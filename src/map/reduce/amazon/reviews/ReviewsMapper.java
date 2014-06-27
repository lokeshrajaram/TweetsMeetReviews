package map.reduce.amazon.reviews;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ReviewsMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] tokens = value.toString().split("\t");

        if (tokens.length == 10) {

            final Text newKey = new Text(tokens[1]);

            final Text reviewText = new Text(tokens[9]);

            context.write(newKey, reviewText);
        }

    }
}
