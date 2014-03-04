package org.wadhome.wadsort;

import org.junit.Test;
import org.wadhome.wadsort.generator.SampleDataGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestWadSort {

    /*
     * This will create a default input file and fill it with some sample data (generated randomly)
     * then process it in two ways (one of them trivial), then compare the results.
     */
    @Test
    public void testExecutionRandomData() {
        try {
            final String trivialModeSortedOutput = "trivialmode_sorted_output.txt";
            final String trivialModeHistogramOutput = "trivialmode_histogram_output.txt";

            // create an input file with some default data in it
            createInputFile(WadSort.DEFAULT_INPUT_FILENAME);

            // process that file with the default parameters, creating the default output files
            new WadSort();

            // process the same file, but using the alternate trivial implementation, generating different files
            new WadSort(WadSort.DEFAULT_INPUT_FILENAME,
                    trivialModeSortedOutput,
                    trivialModeHistogramOutput,
                    WadSort.PARAMETER_VALUE_MODE_TRIVIAL);

            // compare the resulting files and make sure they are identical
            assertTrue("Sorted data output files should match",
                    verifyFilesIdentical(trivialModeSortedOutput,
                            WadSort.DEFAULT_SORTED_OUTPUT_FILENAME));
            assertTrue("Histogram data output files should match",
                    verifyFilesIdentical(trivialModeHistogramOutput,
                            WadSort.DEFAULT_HISTOGRAM_OUTPUT_FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    // todo: Write tests around different data characteristics of interest

    // todo: If performance becomes an issue for this method, it could be sped up considerably by
    // todo: either using OS calls (diff) or by using byte buffers.
    private boolean verifyFilesIdentical(String filenameA, String filenameB) throws Exception {
        final BufferedReader readerA = FileUtils.prepFileForReading(filenameA);
        final BufferedReader readerB = FileUtils.prepFileForReading(filenameB);

        boolean stillSame = true;
        String lineReadA = readerA.readLine();
        while (lineReadA != null && stillSame) {
            String lineReadB = readerB.readLine();
            stillSame = lineReadA.equals(lineReadB);
            lineReadA = readerA.readLine();
        }
        return stillSame;
    }

    private void createInputFile(String filename) throws Exception {
        BufferedWriter writer = FileUtils.prepFileForWriting(filename);
        final int numEntries = 100000;
        final int minValue = 1000;
        final int maxValue = 4000;
        SampleDataGenerator.generateEntries(writer, numEntries, minValue, maxValue);
    }

}
