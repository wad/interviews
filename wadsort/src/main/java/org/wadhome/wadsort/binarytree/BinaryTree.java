package org.wadhome.wadsort.binarytree;

/**
 * This is a pretty straightforward generic implementation of a binary tree, with one specialization:
 * The value used for comparisons is not the same as the payload.
 * This is useful in cases where expensive payloads should not be needlessly created and discarded.
 */
public class BinaryTree<V extends BinaryTreeValue> {

    private Node head;

    public BinaryTree() {
        head = null;
    }

    public void insert(V binaryTreeValue) {
        if (head == null) {
            head = new Node(binaryTreeValue.createPayload());
        }
        head.insert(binaryTreeValue);
    }

    public BinaryTreePayload[] dumpInAscendingOrder() {
        final int nodeCount = countNodes();
        ArrayHolder arrayHolder = new ArrayHolder();
        arrayHolder.array = new BinaryTreePayload[nodeCount];
        arrayHolder.index = 0;
        if (head != null) {
            head.harvestPayloadsAscending(arrayHolder);
        }
        return arrayHolder.array;
    }

    public int countNodes() {
        int nodeCount = 0;
        if (head != null) {
            nodeCount = head.countNodes();
        }
        return nodeCount;
    }

    private class Node {

        public BinaryTreePayload payload;
        public Node childLeft;
        public Node childRight;

        public Node(BinaryTreePayload payload) {
            this.payload = payload;
        }

        public void insert(V binaryTreeValueBeingInserted) {
            switch (payload.compareToValue(binaryTreeValueBeingInserted)) {
                case SMALLER:
                    if (childLeft == null) {
                        childLeft = new Node(binaryTreeValueBeingInserted.createPayload());
                    }
                    childLeft.insert(binaryTreeValueBeingInserted);
                    break;
                case EQUAL:
                    payload.attemptToInsertValue(binaryTreeValueBeingInserted);
                    break;
                case GREATER:
                    if (childRight == null) {
                        childRight = new Node(binaryTreeValueBeingInserted.createPayload());
                    }
                    childRight.insert(binaryTreeValueBeingInserted);
                    break;
            }
        }

        public int countNodes() {
            return 1 + (childLeft == null ? 0 : childLeft.countNodes()) + (childRight == null ? 0 : childRight.countNodes());
        }

        public void harvestPayloadsAscending(ArrayHolder arrayHolder) {
            if (childLeft != null) {
                childLeft.harvestPayloadsAscending(arrayHolder);
            }
            arrayHolder.array[arrayHolder.index++] = payload;
            if (childRight != null) {
                childRight.harvestPayloadsAscending(arrayHolder);
            }
        }
    }

    // This helper class is used to maintain state across recursive invocations of the payload harvesting method
    private class ArrayHolder {
        public int index;
        public BinaryTreePayload[] array;
    }

}
