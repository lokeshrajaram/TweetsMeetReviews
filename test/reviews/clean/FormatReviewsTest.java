package reviews.clean;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class FormatReviewsTest {

    @Test
    public void testProcessFile() throws IOException {

        FormatReviews.processFile(PathContants.input, PathContants.output);

        BufferedReader br = new BufferedReader(new FileReader(
                PathContants.output));

        assertEquals(br.readLine() != null, true);

        br.close();
    }
}
