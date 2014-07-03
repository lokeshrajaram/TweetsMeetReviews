package map.reduce.amazon.reviews;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 * ReviewsDriver prepares and configures required parameters to launch the
 * actual map reduce job.
 * 
 * @author Lokesh Rajaram
 */
public class ReviewsDriver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = new Job(conf, "Reviews Application");

        job.setJarByClass(ReviewsApplication.class);

        job.setInputFormatClass(TextInputFormat.class);

        job.setMapperClass(ReviewsMapper.class);
        job.setReducerClass(ReviewsReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputValueClass(Text.class);
        job.setOutputValueClass(Text.class);

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