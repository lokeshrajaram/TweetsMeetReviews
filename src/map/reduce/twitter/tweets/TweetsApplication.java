package map.reduce.twitter.tweets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

/**
 * Tweets Application provides an easy way to launch a tweet based map reduce
 * job.
 * 
 * @author Lokesh Rajaram
 */
public class TweetsApplication {
    public static void main(String[] args) throws Exception {
        System.out.println("Twitter Application invoked");
        int code = 0;
        code = ToolRunner.run(new Configuration(), new TweetsDriver(), args);
        System.exit(code);
    }
}
