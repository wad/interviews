package org.wadhome.wadsort.binarytree;

public class SampleTestValue implements BinaryTreeValue {

    protected int value;

    public SampleTestValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public BinaryTreePayload createPayload() {
        return new SampleTestPayload(value);
    }
}
