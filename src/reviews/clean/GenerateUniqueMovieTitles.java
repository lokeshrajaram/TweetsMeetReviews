package reviews.clean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * GenerateUniqueMovieTitles crunches the flattened review results produced by @see
 * {@link FormatReviews} and colects the unique movie titles and sorts them.
 * 
 * @author Lokesh Rajaram
 */
public class GenerateUniqueMovieTitles {

    private static Logger logger = Logger
            .getLogger(GenerateUniqueMovieTitles.class);

    private static final String SEPARATOR = "\t";

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            logger.error("Both Input File Path & Output File Path Required");
            return;
        }

        processFile(args[0], args[1]);

    }

    public static void processFile(String fileName, String output)
            throws IOException {

        SortedSet<String> titles = new TreeSet<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;

        FileWriter writer = new FileWriter(output);

        while ((line = br.readLine()) != null) {

            String[] lineArray = line.split(SEPARATOR);

            if (lineArray.length == 10) {
                titles.add(lineArray[1]);
            }
        }

        int count = 0;
        for (String title : titles) {

            if (!RemoveTitles.blacklistTitles.contains(title)
                    && title.length() > 10) {
                count++;
                writer.write(title + "\n");
            }
        }

        logger.info("Total Unique Titles:: " + count);

        br.close();
        writer.close();

    }
}
