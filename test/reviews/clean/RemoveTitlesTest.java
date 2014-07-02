package reviews.clean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveTitlesTest {

    @Test
    public void test() {

        String[] testPositive = { "ghost", "live", "run" };

        for (String s : testPositive) {
            assertEquals(RemoveTitles.blacklistTitles.contains(s), true);
        }

        String[] testNegative = { "lokesh", "insight", "up in the air" };

        for (String s : testNegative) {
            assertEquals(!RemoveTitles.blacklistTitles.contains(s), true);
        }

    }

}
