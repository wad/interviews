package org.wadhome.wadsort;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This implementation of the interface is for testing purposes only.
public class NumberSpongeTrivialImpl implements NumberSponge {

    private List<Integer> values;
    private boolean isSorted;

    public NumberSpongeTrivialImpl() {
        values = new ArrayList<Integer>();
        isSorted = true;
    }

    @Override
    public void absorb(int value) {
        values.add(value);
        isSorted = false;
    }

    @Override
    public long getNumUniqueAbsorbedValues() {
        return values.size();
    }

    @Override
    public void writeSortedData(BufferedWriter writer) throws Exception {
        sort();
        for (Integer value : values) {
            writer.write(String.valueOf(value) + FileUtils.NEWLINE);
        }
    }

    @Override
    public void writeHistogramData(BufferedWriter writer) throws Exception {
        sort();
        Integer previousValue = null;
        long sameValueCounter = 1L;
        for (Integer value : values) {
            if (previousValue == null) {
                previousValue = value;
            } else {
                if (value.equals(previousValue)) {
                    sameValueCounter++;
                } else {
                    writeHistogramPair(writer, previousValue, sameValueCounter);
                    previousValue = value;
                    sameValueCounter = 1L;
                }
            }
        }
        if (previousValue != null) {
            writeHistogramPair(writer, previousValue, sameValueCounter);
        }
    }

    void sort() {
        if (!isSorted) {
            Collections.sort(values);
        }
    }

    void writeHistogramPair(BufferedWriter writer, int value, long count) throws IOException {
        writer.write(String.valueOf(value) + " - " + count + FileUtils.NEWLINE);
    }

}
