package map.reduce.amazon.reviews;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * ReviewsReducer compiles the results and presents it in the form of title and
 * list of reviews.
 * 
 * @author Lokesh Rajaram
 */
public class ReviewsReducer extends Reducer<Text, Text, Text, Text> {

    private static final String SEPARATE_BY = "\t";

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        StringBuffer buff = new StringBuffer();

        Iterator<Text> iterator = values.iterator();

        while (iterator.hasNext()) {
            buff.append(iterator.next().toString());

            if (iterator.hasNext()) {
                buff.append(SEPARATE_BY);
            }

        }

        context.write(key, new Text(buff.toString()));

    }
}
