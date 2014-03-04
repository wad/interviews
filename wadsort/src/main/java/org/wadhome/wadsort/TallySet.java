package org.wadhome.wadsort;

import org.wadhome.wadsort.binarytree.BinaryTreePayload;
import org.wadhome.wadsort.binarytree.BinaryTreeValue;

/*
 * Keeps track of the tallies for a given range of values. It has been designed for a good balance between
 * memory usage and performance. As the tallies increase in size, it will automatically adjust to hold larger
 * numbers, depending on the parameters provided.
 * If a value is incremented beyond the specified number of bytes, there is no error, it just isn't counted anymore.
 */
public class TallySet implements BinaryTreePayload {

    public static enum TALLY_ATTEMPT_RESULT {
        VALUE_TOO_SMALL,
        VALUE_APPROPRIATE,
        VALUE_TOO_LARGE
    }

    int maxTallies;

    int maxBytesPerTally;

    // Holds the position of this span of tallies in the number space
    long location;

    // Holds the actual tallies. Additional rows are added only as needed, to conserve memory
    byte[][] tallies;

    // param maxTallyBits: used to generate the range that this tally set spans.
    //   The span will be two to this power. Consider a value of 10, to get 1024 tallies.
    // param maxBytesPerTally: additional memory will be allocated to allow for larger tallies,
    //   up to this many bytes per tally. Consider a value of 8, which matches to the max size of long integers.
    // param location: identifies where in the number space this set of tallies begins.
    public TallySet(int maxTallyBits, int maxBytesPerTally, long location) {
        if (maxTallyBits < 1 || maxBytesPerTally < 1 || location < 0) {
            throw new RuntimeException("Cannot accept supplied parameters");
        }
        maxTallies = (int) (1L << maxTallyBits);
        this.maxBytesPerTally = maxBytesPerTally;
        this.location = location;
        tallies = new byte[maxBytesPerTally][];
    }

    @Override
    public void attemptToInsertValue(BinaryTreeValue binaryTreeValue) {
        tally(((ValueToTally) binaryTreeValue).getValue());
    }

    @Override
    public COMPARISON_RESULT compareToValue(BinaryTreeValue binaryTreeValue) {
        return compareToValue(((ValueToTally) binaryTreeValue).getValue());
    }

    COMPARISON_RESULT compareToValue(int value) {
        COMPARISON_RESULT result;
        if (value >= location && value < location + maxTallies) {
            result = COMPARISON_RESULT.EQUAL;
        } else {
            result = value < location ? COMPARISON_RESULT.SMALLER : COMPARISON_RESULT.GREATER;
        }
        return result;
    }

    // returns the result of a comparison of the value with the range in this tally set
    public TALLY_ATTEMPT_RESULT tally(int value) {
        TALLY_ATTEMPT_RESULT result;
        switch (compareToValue(value)) {
            case SMALLER:
                result = TALLY_ATTEMPT_RESULT.VALUE_TOO_SMALL;
                break;
            case GREATER:
                result = TALLY_ATTEMPT_RESULT.VALUE_TOO_LARGE;
                break;
            case EQUAL:
            default:
                result = TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE;
                // convert the value to the value relative to the starting point of this chunk
                int localValue = (int) ((long) value - location);

                boolean incrementAccomplished = false;
                for (int row = 0; row < maxBytesPerTally && !incrementAccomplished; row++) {
                    // make sure that the memory for this row has been allocated
                    if (tallies[row] == null) {
                        tallies[row] = new byte[maxTallies];
                    }

                    // if the value is already maxed out for this row, set it to zero, and increment the value in the next row
                    if (isUnsignedByteMaxed(tallies[row][localValue])) {
                        tallies[row][localValue] = 0;
                    } else {
                        tallies[row][localValue]++;
                        incrementAccomplished = true;
                    }
                }
                break;
        }
        return result;
    }

    // returns where in the number space this set of tallies starts
    public long getLocation() {
        return location;
    }

    // returns an array holding the tallies. Be sure to add the location to the index into this array to
    // get the absolute position.
    public long[] readTallies() {
        // assemble the various bytes in the rows into longs to return
        final int bitsPerByte = 8;
        long[] results = new long[maxTallies];
        final int usedRows = countUsedRows();
        for (int index = 0; index < maxTallies; index++) {
            for (int row = 0; row < usedRows; row++) {
                results[index] += readUnsignedByteValue(tallies[row][index]) << (row * bitsPerByte);
            }
        }
        return results;
    }

    public int countTallies() {
        int count = 0;
        final int usedRows = countUsedRows();
        for (int index = 0; index < maxTallies; index++) {
            boolean foundAnyValue = false;
            for (int row = 0; row < usedRows && !foundAnyValue; row++) {
                foundAnyValue = readUnsignedByteValue(tallies[row][index]) > 0;
            }
            if (foundAnyValue) {
                count++;
            }
        }
        return count;
    }

    int countUsedRows() {
        int usedRows;
        for (usedRows = 0; usedRows < maxBytesPerTally && tallies[usedRows] != null; usedRows++) ;
        return usedRows;
    }

    // Workaround for lack of unsigned byte primitive type
    // Checks to see if the unsigned byte value has the greatest possible value in it
    static boolean isUnsignedByteMaxed(byte byteValue) {
        return byteValue == -1;
    }

    // Workaround for lack of unsigned byte primitive type
    // Gets the actual integer value of what is intended to be an unsigned byte
    static long readUnsignedByteValue(byte byteValue) {
        return byteValue < 0 ? 256 + byteValue : byteValue;
    }

}
