package map.reduce.amazon.reviews;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReviewsReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        StringBuffer buff = new StringBuffer();

        Iterator<Text> iterator = values.iterator();

        while (iterator.hasNext()) {
            buff.append(iterator.next().toString());

            if (iterator.hasNext()) {
                buff.append("\t");
            }

        }

        context.write(key, new Text(buff.toString()));

    }
}
