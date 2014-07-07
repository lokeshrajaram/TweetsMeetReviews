package map.reduce.twitter.tweets;

import map.reduce.amazon.reviews.ReviewsApplication;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

/**
 * Tweets Application provides an easy way to launch a tweet based map reduce
 * job.
 * 
 * @author Lokesh Rajaram
 */
public class TweetsApplication {

    private static Logger logger = Logger.getLogger(ReviewsApplication.class);
    private static final String INVOKED_APP_NAME = "Twitter Application invoked";

    public static void main(String[] args) throws Exception {
        logger.info(INVOKED_APP_NAME);
        int code = 0;
        code = ToolRunner.run(new Configuration(), new TweetsDriver(), args);
        System.exit(code);
    }
}
