package org.wadhome.wadsort.binarytree;

public class SampleTestPayload implements BinaryTreePayload {

    private int value;

    public SampleTestPayload(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void attemptToInsertValue(BinaryTreeValue binaryTreeValue) {
        // do nothing
    }

    @Override
    public COMPARISON_RESULT compareToValue(BinaryTreeValue binaryTreeValue) {
        COMPARISON_RESULT result;
        int targetValue = ((SampleTestValue) binaryTreeValue).getValue();
        if (targetValue > value) {
            result = COMPARISON_RESULT.GREATER;
        } else {
            result = targetValue < value ? COMPARISON_RESULT.SMALLER : COMPARISON_RESULT.EQUAL;
        }
        return result;
    }
}
