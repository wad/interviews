package org.wadhome.wadsort.generator;

import org.wadhome.wadsort.FileUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class SampleDataGenerator {

    static final int ARGUMENT_INDEX_OUTPUT_FILENAME = 0;
    static final int ARGUMENT_INDEX_NUM_ENTRIES = 1;
    static final int ARGUMENT_INDEX_SMALLEST_ENTRY_VALUE = 2;
    static final int ARGUMENT_INDEX_LARGEST_ENTRY_VALUE = 3;
    static final int NUM_ARGUMENTS_EXPECTED = 4;

    public static void main(String... arguments) {
        if (arguments.length == NUM_ARGUMENTS_EXPECTED) {
            String outputFilename = arguments[ARGUMENT_INDEX_OUTPUT_FILENAME];

            // todo: Could be more robust here, and check these parameters. Deemed not worth it for this test tool.
            int numEntries = Integer.parseInt(arguments[ARGUMENT_INDEX_NUM_ENTRIES]);
            int smallestEntryValue = Integer.parseInt(arguments[ARGUMENT_INDEX_SMALLEST_ENTRY_VALUE]);
            int largestEntryValue = Integer.parseInt(arguments[ARGUMENT_INDEX_LARGEST_ENTRY_VALUE]);

            try {
                generateEntries(FileUtils.prepFileForWriting(outputFilename), numEntries, smallestEntryValue, largestEntryValue);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Please provide the following arguments:");
            System.out.println("\t[output filename]");
            System.out.println("\t[number of entries]");
            System.out.println("\t[smallest possible entry]");
            System.out.println("\t[greatest possible entry]");
            System.out.println("Example: sample.txt 12345 55 7777777777");
        }
    }

    public static void generateEntries(
            BufferedWriter stream,
            int numEntries,
            int smallestEntryValue,
            int largestEntryValue) throws Exception {
        int entryNumber = 0;
        try {
            Random randomNumberGenerator = new Random(System.nanoTime());
            final int spread = largestEntryValue - smallestEntryValue + 1;
            for (entryNumber = 0; entryNumber < numEntries; entryNumber++) {
                int entry = ((int) (randomNumberGenerator.nextDouble() * spread)) + smallestEntryValue;
                stream.write(String.valueOf(entry));
                stream.write(FileUtils.NEWLINE);
            }
        } catch (IOException e) {
            throw new Exception("Failed to write an entry, entry number " + entryNumber, e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                System.out.println("Something bad happened, and could not close stream.");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
