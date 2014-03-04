package org.wadhome.wadsort.binarytree;

/*
 * The values that get passed around, and are used to order the binary tree, are distinct from the payloads
 * in this implementation of a binary tree. 
 * The items actually stored in the tree must extend this interface, so that they know what they
 * should do when the user attempts to insert a duplicate value, and how to compare values
 */
public interface BinaryTreePayload {

    static enum COMPARISON_RESULT {
        SMALLER,
        EQUAL,
        GREATER
    }

    void attemptToInsertValue(BinaryTreeValue binaryTreeValue);

    COMPARISON_RESULT compareToValue(BinaryTreeValue binaryTreeValue);

}
