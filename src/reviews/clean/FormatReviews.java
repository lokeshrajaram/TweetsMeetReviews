package reviews.clean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

public class FormatReviews {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out
                    .println("Both Input File Path & Output File Path Required");
            return;
        }

        processFile(args[0], args[1]);

    }

    private static void processFile(String fileInput, String fileOutput)
            throws IOException {

        int count = 0;

        List<String> tokens = new ArrayList<String>();

        FormatReviews sqlImport = new FormatReviews();

        BufferedReader br = new BufferedReader(new FileReader(fileInput));
        String line;

        CSVWriter writer = new CSVWriter(new FileWriter(fileOutput), '\t',
                CSVWriter.NO_QUOTE_CHARACTER);

        while ((line = br.readLine()) != null) {

            while (line != null && !line.isEmpty()) {

                String[] lineArray = line.split(": ");

                if (line.contains("product/title:")) {
                    tokens.add(lineArray.length == 1 ? "Unknown" : sqlImport
                            .escapeCharacters(lineArray[2].toLowerCase()));
                } else {
                    tokens.add(lineArray.length == 1 ? "Unknown" : lineArray[1]
                            .replaceAll("\\\\", "").trim());
                }

                line = br.readLine();

            }

            if (tokens.get(1).length() > 4) {
                if (tokens.get(9) != null) {
                    tokens.set(9, tokens.get(9).replaceAll(",", ""));
                }
                count++;
                writer.writeNext(tokens.toArray(new String[tokens.size()]));
            }
            tokens = new ArrayList<String>();
        }

        br.close();
        writer.close();

        System.out.println("Total Records Count:: " + count);
    }

    private String escapeCharacters(String input) {

        Map<String, String> regexMap = new HashMap<String, String>();
        regexMap.put("\\[HD\\]", "");
        regexMap.put("\\[hd\\]", "");
        regexMap.put("\\[Featurette\\]", "");
        regexMap.put("\\[English Subtitled\\]", "");
        regexMap.put("\\[Remastered & Restored\\]", "");
        regexMap.put("\\[The Final Cut - Widescreen Edition\\]", "");
        regexMap.put("\\[Unrated Uncut Edition\\]", "");
        regexMap.put("\\(AKA", "");
        regexMap.put("- Special Edition", "");
        regexMap.put("DVD", "");
        regexMap.put("\\(Institutional Use", "");
        regexMap.put("\\(institutional", "");
        regexMap.put("- download version", "");
        regexMap.put("&amp;", "&");
        regexMap.put("&#39;", "'");
        regexMap.put("\\(.*\\)", "");
        regexMap.put("\\\\", "");
        regexMap.put("&quot;", "");

        for (String s : regexMap.keySet()) {
            input = input.replaceAll(s, regexMap.get(s));
        }

        return input.trim();
    }
}