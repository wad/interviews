package org.wadhome.wadsort;

import org.wadhome.wadsort.binarytree.BinaryTreePayload;
import org.wadhome.wadsort.binarytree.BinaryTreeValue;

// This object contains all the information needed to generate a TallySet, which is what the
// binary tree will do.
public class ValueToTally implements BinaryTreeValue {

    private int value;
    private int maxTallyBits;
    private int maxBytesPerTally;
    private long location;

    public ValueToTally(int value, int maxTallyBits, int maxBytesPerTally, long location) {
        this.value = value;
        this.maxTallyBits = maxTallyBits;
        this.maxBytesPerTally = maxBytesPerTally;
        this.location = location;
    }

    public int getValue() {
        return value;
    }

    @Override
    public BinaryTreePayload createPayload() {
        return new TallySet(maxTallyBits, maxBytesPerTally, location);
    }

}
