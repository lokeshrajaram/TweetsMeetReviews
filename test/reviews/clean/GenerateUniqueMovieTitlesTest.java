package reviews.clean;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class GenerateUniqueMovieTitlesTest {

    @Test
    public void testProcessFile() throws IOException {

        GenerateUniqueMovieTitles.processFile(PathContants.titles_input,
                PathContants.titles_output);

        BufferedReader br = new BufferedReader(new FileReader(
                PathContants.titles_output));

        assertEquals(br.readLine() != null, true);

        br.close();
    }
}
