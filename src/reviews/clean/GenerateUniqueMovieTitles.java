package reviews.clean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class GenerateUniqueMovieTitles {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out
                    .println("Both Input File Path & Output File Path Required");
            return;
        }

        processFile(args[0], args[1]);

    }

    private static void processFile(String fileName, String output)
            throws IOException {

        SortedSet<String> titles = new TreeSet<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;

        FileWriter writer = new FileWriter(output);

        while ((line = br.readLine()) != null) {

            String[] lineArray = line.split("\t");

            if (lineArray.length == 10) {
                titles.add(lineArray[1]);
            }
        }

        int count = 0;
        for (String title : titles) {

            if (!RemoveTitles.blacklistTitles.contains(title)) {
                count++;
                writer.write(title + "\n");
            }
        }

        System.out.println("Total Unique Titles:: " + count);

        br.close();
        writer.close();

    }
}
