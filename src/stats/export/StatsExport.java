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

/**
 * StatsExport is a wrapper around HBase rest api to move metrics data from HDFS
 * to HBase.
 * 
 * @author Lokesh Rajaram
 */
public class StatsExport {

    public static final String SEPARATOR_FIELD = new String(new char[] { 1 });

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: HdfsRead <hdfs_input_path>");
            System.exit(1);
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(
                new File(args[0])));

        Configuration hbaseConfig = HBaseConfiguration.create();

        HTable htable = new HTable(hbaseConfig, "tweets_stats");

        HTable htableCounts = new HTable(hbaseConfig, "counts");

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

                    put.add("numbers".getBytes(), "tweet_count".getBytes(),
                            tokens[1].getBytes());

                    put.add("numbers".getBytes(), "match_count".getBytes(),
                            tokens[2].getBytes());

                    htable.put(put);

                    if (records % 1 == 0) {
                        System.out.println("No of records inserted so far... "
                                + records);
                    }

                    String countLine = null;

                    BufferedReader br = new BufferedReader(new FileReader(
                            "/home/ubuntu/stats/counts/count.log"));

                    long newTweetCount = 0;
                    long newMatchCount = 0;

                    while ((countLine = br.readLine()) != null) {

                        String[] counts = countLine.split(",");

                        newTweetCount = Long.parseLong(counts[0])
                                + Long.parseLong(tokens[1]);

                        newMatchCount = Long.parseLong(counts[1])
                                + Long.parseLong(tokens[2]);

                        Put putcounts = new Put("count".getBytes());

                        putcounts.add("info".getBytes(),
                                "tweet_count".getBytes(),
                                String.valueOf(newTweetCount).getBytes());

                        putcounts.add("info".getBytes(),
                                "match_count".getBytes(),
                                String.valueOf(newMatchCount).getBytes());

                        htableCounts.put(putcounts);
                    }

                    br.close();

                    BufferedWriter bwr = new BufferedWriter(new FileWriter(
                            "/home/ubuntu/stats/counts/count.log"));

                    bwr.write(newTweetCount + "," + newMatchCount);

                    bwr.close();

                    System.out.println("Tweet Count:: " + newTweetCount);

                    System.out.println("Match Count:: " + newMatchCount);

                }

            }

            long endTime = System.nanoTime();

            System.out.println("Total Records:: " + records);

            System.out.println("Time Taken:: " + (endTime - startTime) + " ns");

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