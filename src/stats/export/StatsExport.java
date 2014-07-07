package stats.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import reviews.clean.FormatReviews;

/**
 * StatsExport is a wrapper around HBase rest api to move metrics data from HDFS
 * to HBase.
 * 
 * @author Lokesh Rajaram
 */
public class StatsExport {

    private static Logger logger = Logger.getLogger(FormatReviews.class);

    private static final String SEPARATOR_FIELD = new String(new char[] { 1 });

    private static final String TABLE_TWEETS_STATS = "tweets_stats";
    private static final String COLUMN_FAMILY = "numbers";
    private static final String COLUMN_1 = "tweet_count";
    private static final String COLUMN_2 = "match_count";
    private static final String COUNTS = "counts";

    private static final String COLUMN_FAMILY_COUNTS = "info";
    private static final String COLUMN_ONE = "tweet_count";
    private static final String COLUMN_TWO = "match_count";
    private static final String KEY = "count";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            logger.info("Usage: HdfsRead <hdfs_input_path>, cunt log path");
            System.exit(1);
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(
                new File(args[0])));

        Configuration hbaseConfig = HBaseConfiguration.create();

        HTable htable = new HTable(hbaseConfig, TABLE_TWEETS_STATS);

        HTable htableCounts = new HTable(hbaseConfig, COUNTS);

        try {
            String line = null;
            int records = 0;
            long startTime = System.nanoTime();
            while ((line = bufferedReader.readLine()) != null) {

                records++;

                String[] tokens = line.split(SEPARATOR_FIELD);

                if (tokens.length == 3) {

                    byte[] key = tokens[0].getBytes();

                    Put put = new Put(key);

                    put.add(COLUMN_FAMILY.getBytes(), COLUMN_1.getBytes(),
                            tokens[1].getBytes());

                    put.add(COLUMN_FAMILY.getBytes(), COLUMN_2.getBytes(),
                            tokens[2].getBytes());

                    htable.put(put);

                    if (records % 1 == 0) {
                        logger.info("No of records inserted so far... "
                                + records);
                    }

                    String countLine = null;

                    BufferedReader br = new BufferedReader(new FileReader(
                            args[1]));

                    long newTweetCount = 0;
                    long newMatchCount = 0;

                    while ((countLine = br.readLine()) != null) {

                        String[] counts = countLine.split(",");

                        newTweetCount = Long.parseLong(counts[0])
                                + Long.parseLong(tokens[1]);

                        newMatchCount = Long.parseLong(counts[1])
                                + Long.parseLong(tokens[2]);

                        Put putcounts = new Put(KEY.getBytes());

                        putcounts.add(COLUMN_FAMILY_COUNTS.getBytes(),
                                COLUMN_ONE.getBytes(),
                                String.valueOf(newTweetCount).getBytes());

                        putcounts.add(COLUMN_FAMILY_COUNTS.getBytes(),
                                COLUMN_TWO.getBytes(),
                                String.valueOf(newMatchCount).getBytes());

                        htableCounts.put(putcounts);
                    }

                    br.close();

                    BufferedWriter bwr = new BufferedWriter(new FileWriter(
                            args[1]));

                    bwr.write(newTweetCount + "," + newMatchCount);

                    bwr.close();

                    logger.info("Tweet Count:: " + newTweetCount);

                    logger.info("Match Count:: " + newMatchCount);

                }

            }

            long endTime = System.nanoTime();

            logger.info("Total Records:: " + records);

            logger.info("Time Taken:: " + (endTime - startTime) + " ns");

            bufferedReader.close();

            htable.flushCommits();

            htable.close();

            htableCounts.flushCommits();

            htableCounts.close();

        } finally {
            bufferedReader.close();

            htable.flushCommits();

            htable.close();

            htableCounts.flushCommits();

            htableCounts.close();
        }

    }
}