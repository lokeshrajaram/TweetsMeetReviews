package reviews.clean;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FormatReviewsTest.class, GenerateUniqueMovieTitlesTest.class,
        RemoveTitlesTest.class })
public class AllTests {

}
