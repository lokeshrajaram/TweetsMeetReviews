package map.reduce.amazon.reviews;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class ReviewsApplication {
    public static void main(String[] args) throws Exception {
        System.out.println("Reviews Application invoked");
        int code = 0;
        code = ToolRunner.run(new Configuration(), new ReviewsDriver(), args);
        System.exit(code);
    }
}
