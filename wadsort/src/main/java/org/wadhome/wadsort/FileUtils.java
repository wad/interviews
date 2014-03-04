package org.wadhome.wadsort;

import java.io.*;

public class FileUtils {

    // This is defined here, so that the same data generation code has access to it.
    // Could also use System.getProperty("line.separator")
    public static final String NEWLINE = "\r\n";

    // This will destroy any existing file by this name that finds. It will fail
    // if strange stuff happens, such as a directory existing with that same name.
    public static BufferedWriter prepFileForWriting(String outputFilename) throws Exception {
        BufferedWriter writer;
        File outputFile = new File(outputFilename);
        if (outputFile.exists() && !outputFile.delete()) {
            throw new Exception("Could not delete existing file: " + outputFilename);
        }
        try {
            if (!outputFile.createNewFile()) {
                throw new Exception("Did not delete existing file: " + outputFilename);
            }
            if (!outputFile.canWrite()) {
                throw new Exception("Could not write to file: " + outputFilename);
            }
        } catch (IOException e) {
            throw new Exception("Could not create file for writing: " + outputFilename + "\n" + e.getMessage());
        }
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new Exception("Could not create stream for writing: " + outputFilename + "\n" + e.getMessage());
        }
        return writer;
    }

    public static BufferedReader prepFileForReading(String inputFilename) throws Exception {
        File inputFile = new File(inputFilename);
        if (!inputFile.exists()) {
            throw new Exception("File " + inputFilename + " doesn't exist!");
        }
        if (!inputFile.canRead()) {
            throw new Exception("File " + inputFile + " cannot be read!");
        }
        return new BufferedReader(new FileReader(inputFile));
    }

}
