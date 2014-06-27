package map.reduce.twitter.tweets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

public class TweetsDriver extends Configured implements Tool {

    private static final String hdfsCacheFile = "/user/ubuntu/reviews/MovieTitles.txt";

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Path path = new Path(hdfsCacheFile);

        DistributedCache.addCacheFile(path.toUri(), conf);

        Job job = new Job(conf, "Tweets Application");

        job.setJarByClass(TweetsApplication.class);

        job.setInputFormatClass(TextInputFormat.class);

        job.setMapperClass(TweetsMapper.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean jobSucceeded = job.waitForCompletion(true);
        if (jobSucceeded) {
            return 0;
        } else {
            return -1;
        }
    }
}