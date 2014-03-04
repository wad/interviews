package org.wadhome.wadsort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

public class WadSort {

    static final String DEFAULT_INPUT_FILENAME = "input.txt";
    static final String DEFAULT_SORTED_OUTPUT_FILENAME = "ascending.txt";
    static final String DEFAULT_HISTOGRAM_OUTPUT_FILENAME = "histogram.txt";

    private static final int ARGUMENT_INDEX_INPUT_FILENAME = 0;
    private static final int ARGUMENT_INDEX_SORTED_OUTPUT_FILENAME = 1;
    private static final int ARGUMENT_INDEX_HISTOGRAM_OUTPUT_FILENAME = 2;
    private static final int ARGUMENT_INDEX_MODE = 3;
    private static final int NUM_ARGUMENTS_EXPECTED = 4;

    private enum MODE {TRIVIAL, ACTUAL}

    public static final String PARAMETER_VALUE_MODE_TRIVIAL = "TRIVIAL";
    public static final String PARAMETER_VALUE_MODE_ACTUAL = "ACTUAL";
    private static final MODE DEFAULT_MODE = MODE.ACTUAL;
    private MODE mode;

    private String inputFilename;
    private String sortedOutputFilename;
    private String histogramOutputFilename;

    private BufferedReader inputReader = null;
    private BufferedWriter sortedWriter = null;
    private BufferedWriter histogramWriter = null;

    public static void main(String... arguments) {
        try {
            new WadSort(arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WadSort(String... arguments) throws Exception {
        try {
            log("");
            log("Processing started, determining filenames");
            if (checkParameters(arguments)) {
                log("Filenames identified: " + inputFilename + " " + sortedOutputFilename + " " + histogramOutputFilename);

                log("preparing input and output streams");
                prepareReadersAndWriters();
                log("Streams ready");

                log("Beginning to absorb data from " + inputFilename);
                NumberSponge sponge;
                switch (mode) {
                    case TRIVIAL:
                        log("WARNING! Trivial mode enabled, output invalid");
                        sponge = new NumberSpongeTrivialImpl();
                        break;
                    case ACTUAL:
                        sponge = new NumberSpongeImpl();
                        break;
                    default:
                        throw new Exception("unknown mode");
                }
                try {
                    String lineRead = inputReader.readLine();
                    while (lineRead != null) {
                        sponge.absorb(Integer.parseInt(lineRead.trim()));
                        lineRead = inputReader.readLine();
                    }
                } catch (IOException e) {
                    throw new Exception("Something went wrong while reading data", e);
                }
                log("Absorbed " + sponge.getNumUniqueAbsorbedValues() + " values");

                log("Writing sorted data to " + this.sortedOutputFilename);
                sponge.writeSortedData(sortedWriter);
                log("Sorted data written");

                log("Writing histogram data to " + this.histogramOutputFilename);
                sponge.writeHistogramData(histogramWriter);
                log("Histogram data written");

                log("Processing completed successfully");
            } else {
                logError("Processing stopped, could not determine filenames");
            }
        } finally {
            attemptToCloseStreams();
        }
    }

    boolean checkParameters(String[] arguments) {
        boolean parametersHappy = true;
        if (arguments.length == 0) {
            inputFilename = DEFAULT_INPUT_FILENAME;
            sortedOutputFilename = DEFAULT_SORTED_OUTPUT_FILENAME;
            histogramOutputFilename = DEFAULT_HISTOGRAM_OUTPUT_FILENAME;
            mode = DEFAULT_MODE;
        } else if (arguments.length == NUM_ARGUMENTS_EXPECTED) {
            inputFilename = arguments[ARGUMENT_INDEX_INPUT_FILENAME];
            sortedOutputFilename = arguments[ARGUMENT_INDEX_SORTED_OUTPUT_FILENAME];
            histogramOutputFilename = arguments[ARGUMENT_INDEX_HISTOGRAM_OUTPUT_FILENAME];
            String modeGiven = arguments[ARGUMENT_INDEX_MODE];
            if (PARAMETER_VALUE_MODE_TRIVIAL.equalsIgnoreCase(modeGiven)) {
                mode = MODE.TRIVIAL;
            } else if (PARAMETER_VALUE_MODE_ACTUAL.equalsIgnoreCase(modeGiven)) {
                mode = MODE.ACTUAL;
            } else {
                logNoTimestamp("Unrecognized mode: " + modeGiven);
                parametersHappy = false;
            }
        } else {
            logNoTimestamp("Must either supply no arguments (to use the defaults), or these three arguments: ");
            logNoTimestamp(" inputFilename sortedOutputFilename histogramOutputFilename " +
                    PARAMETER_VALUE_MODE_TRIVIAL + "|" + PARAMETER_VALUE_MODE_ACTUAL);
            parametersHappy = false;
        }
        return parametersHappy;
    }

    void prepareReadersAndWriters() throws Exception {
        inputReader = FileUtils.prepFileForReading(inputFilename);
        sortedWriter = FileUtils.prepFileForWriting(sortedOutputFilename);
        histogramWriter = FileUtils.prepFileForWriting(histogramOutputFilename);
    }

    void attemptToCloseStreams() {
        log("Will now attempt to close any open files.");
        if (inputReader != null) {
            try {
                inputReader.close();
                log("Successfully closed " + inputFilename);
            } catch (IOException e) {
                logError("could not close file for reading: " + inputFilename + "\n" + e.getMessage());
            }
        }
        if (sortedWriter != null) {
            try {
                sortedWriter.close();
                log("Successfully closed " + sortedOutputFilename);
            } catch (IOException e) {
                logError("could not close file for writing: " + sortedOutputFilename + "\n" + e.getMessage());
            }
        }
        if (histogramWriter != null) {
            try {
                histogramWriter.close();
                log("Successfully closed " + histogramOutputFilename);
            } catch (IOException e) {
                logError("could not close file for writing: " + histogramOutputFilename + "\n" + e.getMessage());
            }
        }
    }

    private void logError(String message) {
        log("!!!ERROR!!! " + message);
    }

    private void log(String message) {
        logNoTimestamp("[" + new Date() + "] " + message);
    }

    private void logNoTimestamp(String message) {
        System.out.println(message);
    }

}
