package tweets.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

/**
 * TweetsExport is a wrapper around HBase rest api to move processed tweets data
 * from HDFS to HBase.
 * 
 * @author Lokesh Rajaram
 */
public class TweetsExport {

    private static Logger logger = Logger.getLogger(TweetsExport.class);

    private static final String SEPARATOR = "\t";
    private static final String TABLE_TWEETS = "tweets";
    private static final String COLUMN_FAMILY = "info";
    private static final String COLUMN_1 = "created_at";
    private static final String COLUMN_2 = "text";
    private static final String COLUMN_3 = "review_title";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            logger.error("Usage: HdfsRead <hdfs_input_path>");
            System.exit(1);
        }
        String fromHdfsFile = args[0];

        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(fromHdfsFile), conf);

        Path path = new Path(fromHdfsFile);
        if (!fileSystem.exists(path)) {
            logger.info("File " + fromHdfsFile + " does not exist");
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(fileSystem.open(path)));

        Configuration hbaseConfig = HBaseConfiguration.create();

        HTable htable = new HTable(hbaseConfig, TABLE_TWEETS);

        try {
            String line = null;
            int records = 0;
            long startTime = System.nanoTime();
            while ((line = bufferedReader.readLine()) != null) {

                records++;

                String[] tokens = line.split(SEPARATOR);

                if (tokens.length == 4) {

                    byte[] newKey = tokens[0].getBytes();

                    Put put = new Put(newKey);

                    put.add(COLUMN_FAMILY.getBytes(), COLUMN_1.getBytes(),
                            tokens[1].getBytes());

                    put.add(COLUMN_FAMILY.getBytes(), COLUMN_2.getBytes(),
                            tokens[2].getBytes());

                    put.add(COLUMN_FAMILY.getBytes(), COLUMN_3.getBytes(),
                            tokens[3].getBytes());

                    htable.put(put);

                    if (records % 10000 == 0) {
                        logger.info("No of records inserted so far... "
                                + records);
                    }

                }

            }
            long endTime = System.nanoTime();

            logger.info("Total Records:: " + records);

            logger.info("Time Taken:: " + (endTime - startTime) + " ns");

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