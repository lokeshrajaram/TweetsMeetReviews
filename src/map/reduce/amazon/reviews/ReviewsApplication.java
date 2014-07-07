package map.reduce.amazon.reviews;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

/**
 * Reviews Application provides an easy way to launch a reviews based map reduce
 * job.
 * 
 * @author Lokesh Rajaram
 */
public class ReviewsApplication {

    private static Logger logger = Logger.getLogger(ReviewsApplication.class);
    private static final String INVOKED_APP_NAME = "Reviews Application invoked";

    public static void main(String[] args) throws Exception {
        logger.info(INVOKED_APP_NAME);
        int code = 0;
        code = ToolRunner.run(new Configuration(), new ReviewsDriver(), args);
        System.exit(code);
    }
}
