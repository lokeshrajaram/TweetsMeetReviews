package reviews.clean;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class FormatReviewsTest {

    private static final String input = "/Users/lokesh/Desktop/parse/Movies.txt";
    private static final String output = "/Users/lokesh/Desktop/parse/MoviesClean.tsv";

    @Test
    public void testProcessFile() throws IOException {

        FormatReviews.processFile(input, output);

        BufferedReader br = new BufferedReader(new FileReader(output));

        assertEquals(br.readLine() != null, true);

        br.close();
    }
}
