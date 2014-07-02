package reviews.clean;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class GenerateUniqueMovieTitlesTest {

    private static final String titles_input = "/Users/lokesh/Desktop/parse/MoviesClean.tsv";
    private static final String titles_output = "/Users/lokesh/Desktop/parse/MoviesCleanTitle.txt";

    @Test
    public void testProcessFile() throws IOException {

        GenerateUniqueMovieTitles.processFile(titles_input, titles_output);

        BufferedReader br = new BufferedReader(new FileReader(titles_output));

        assertEquals(br.readLine() != null, true);

        br.close();
    }
}
