package map.reduce.amazon.reviews;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * ReviewsMapper extracts the title and review text and emits them as key value
 * pairs.
 * 
 * @author Lokesh Rajaram
 */
public class ReviewsMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static final String SPLITY_BY = "\t";

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] tokens = value.toString().split(SPLITY_BY);

        if (tokens.length == 10) {

            final Text newKey = new Text(tokens[1]);

            final Text reviewText = new Text(tokens[9]);

            context.write(newKey, reviewText);
        }

    }
}
