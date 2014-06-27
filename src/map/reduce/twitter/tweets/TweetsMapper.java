package map.reduce.twitter.tweets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TweetsMapper extends
        Mapper<LongWritable, Text, NullWritable, Text> {

    private final List<String> titles = new ArrayList<String>();
    public static final String SEPARATOR_FIELD = new String(new char[] { 1 });

    @Override
    public void setup(Context context) throws IOException {

        Path[] localFiles = DistributedCache.getLocalCacheFiles(context
                .getConfiguration());

        BufferedReader reader = new BufferedReader(new FileReader(
                localFiles[0].toString()));

        String title = null;

        while ((title = reader.readLine()) != null) {
            titles.add(title);
        }

        reader.close();
    }

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] tokens = value.toString().split(SEPARATOR_FIELD);

        if (tokens.length == 3) {

            String text = tokens[2];

            String match = "NO MATCH";

            for (String title : titles) {

                if (text.indexOf(title) != -1) {
                    match = title;
                    break;
                }

            }

            StringBuilder builder = new StringBuilder(tokens[0]).append("\t")
                    .append(tokens[1]).append("\t").append(tokens[2])
                    .append("\t").append(match);

            context.write(null, new Text(builder.toString()));

        }

    }
}
