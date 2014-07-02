package stats.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

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

                }

            }
            long endTime = System.nanoTime();

            System.out.println("Total Records:: " + records);

            System.out.println("Time Taken:: " + (endTime - startTime) + " ns");

            bufferedReader.close();

            htable.flushCommits();

            htable.close();

        } finally {
            bufferedReader.close();

            htable.flushCommits();

            htable.close();
        }

    }
}