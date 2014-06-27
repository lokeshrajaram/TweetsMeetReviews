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

public class TweetsExport {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: HdfsRead <hdfs_input_path>");
            System.exit(1);
        }
        String fromHdfsFile = args[0];

        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(fromHdfsFile), conf);

        Path path = new Path(fromHdfsFile);
        if (!fileSystem.exists(path)) {
            System.out.println("File " + fromHdfsFile + " does not exist");
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(fileSystem.open(path)));

        Configuration hbaseConfig = HBaseConfiguration.create();

        HTable htable = new HTable(hbaseConfig, "tweets");

        try {
            String line = null;
            int records = 0;
            long startTime = System.nanoTime();
            while ((line = bufferedReader.readLine()) != null) {

                records++;

                String[] tokens = line.split("\t");

                if (tokens.length == 4) {

                    byte[] newKey = tokens[0].getBytes();

                    Put put = new Put(newKey);

                    put.add("info".getBytes(), "created_at".getBytes(),
                            tokens[1].getBytes());

                    put.add("info".getBytes(), "text".getBytes(),
                            tokens[2].getBytes());

                    put.add("info".getBytes(), "review_title".getBytes(),
                            tokens[3].getBytes());

                    htable.put(put);

                    if (records % 10000 == 0) {
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