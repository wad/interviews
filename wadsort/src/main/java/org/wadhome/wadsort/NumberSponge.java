package org.wadhome.wadsort;

import java.io.BufferedWriter;

// This interface describes a class that absorbs numbers from the input stream, and keeps an internal histogram
public interface NumberSponge {

    void absorb(int value);

    long getNumUniqueAbsorbedValues();

    void writeSortedData(BufferedWriter writer) throws Exception;

    void writeHistogramData(BufferedWriter writer) throws Exception;

}
