package org.wadhome.wadsort;

import org.wadhome.wadsort.binarytree.BinaryTree;
import org.wadhome.wadsort.binarytree.BinaryTreePayload;

import java.io.BufferedWriter;

/*
 * This is the primary implementation of the interface
 *
 * This implementation is designed to handle many values, all in the range of the positive Java integers.
 * Performance should scale well.
 *
 * High-level design:
 * The idea is basically that there is a sparse array of the size of the number value space (positive Java integers).
 * As a value is read in, that index in the array is incremented, thus generating a histogram.
 * The histogram easily generates a sorted list of the values.
 *
 * Considerations:
 * Because we don't want to allocate memory to hold the entire array, we do it in sets of 1024 values, and use
 * a binary tree to organize these sets to cover the entire number space.
 */
public class NumberSpongeImpl implements NumberSponge {

    /*
     * The range of values spanned in a single TallySet is determined by this value. Depending on the
     * characteristics of the expected input to the program, this is a very good place to do some performance tuning.
     * A value of 10 results in a span of 1024 values in one node in the binary tree. If you anticipate a wide range
     * of values, but they don't count very high, you could set this lower, and put more burden on the binary tree
     * than on the TallySets.
     */
    final static int MAX_TALLY_BITS = 10;

    /*
     * This sets the limit on how many tallies will be counted for a given value. Currently, the implementation
     * will not return tallies higher than longs, so 7 is the maximum. There is little use in setting this value
     * below than the maximum, as it will grow as needed. 8 is not used, because of the sign bit.
     */
    final static int MAX_BYTES_PER_TALLY = 7;

    BinaryTree<ValueToTally> tree;

    BinaryTreePayload[] tallySetCache;

    public NumberSpongeImpl() {
        tree = new BinaryTree<ValueToTally>();
        tallySetCache = null;
    }

    @Override
    public void absorb(int value) {
        long location = computeLocationFromValue(value);
        ValueToTally valueToTally = new ValueToTally(value, MAX_TALLY_BITS, MAX_BYTES_PER_TALLY, location);
        tree.insert(valueToTally);

        // clear any cached value
        tallySetCache = null;
    }

    @Override
    public long getNumUniqueAbsorbedValues() {
        long tallyOfTallies = 0;
        harvestTallySets();
        for (BinaryTreePayload payload : tallySetCache) {
            tallyOfTallies += (long) (((TallySet) payload).countTallies());
        }
        return tallyOfTallies;
    }

    @Override
    public void writeSortedData(BufferedWriter writer) throws Exception {
        walkTreeAndWriteData(writer, false);
    }

    @Override
    public void writeHistogramData(BufferedWriter writer) throws Exception {
        walkTreeAndWriteData(writer, true);
    }

    void walkTreeAndWriteData(BufferedWriter writer, boolean isHistogram) throws Exception {
        harvestTallySets();
        for (BinaryTreePayload payload : tallySetCache) {
            TallySet tallySet = (TallySet) payload;
            long location = tallySet.getLocation();
            long[] tallies = tallySet.readTallies();
            for (int i = 0; i < tallies.length; i++) {
                if (tallies[i] > 0) {
                    if (isHistogram) {
                        writer.write(String.valueOf(location + i) + " - " + tallies[i] + FileUtils.NEWLINE);
                    } else {
                        for (int j = 0; j < tallies[i]; j++) {
                            writer.write(String.valueOf(location + i) + FileUtils.NEWLINE);
                        }
                    }
                }
            }
        }
    }

    void harvestTallySets() {
        if (tallySetCache == null) {
            tallySetCache = tree.dumpInAscendingOrder();
        }
    }

    // The location is the first value in a tally set. It is calculated from the value
    // by setting the rightmost n bits to zero, where n is the number of bits in the tally size
    // returns the location, which will be the starting number of the set in which this value belongs
    static long computeLocationFromValue(int value) {
        return (((long) value) >> MAX_TALLY_BITS) << MAX_TALLY_BITS;
    }

}
