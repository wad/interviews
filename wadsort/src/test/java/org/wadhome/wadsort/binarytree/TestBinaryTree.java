package org.wadhome.wadsort.binarytree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBinaryTree {

    @Test
    public void testCountNodes() {
        BinaryTree<SampleTestValue> tree = new BinaryTree<SampleTestValue>();
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(5));
        tree.insert(new SampleTestValue(6));
        tree.insert(new SampleTestValue(6));
        tree.insert(new SampleTestValue(7));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(9));
        assertEquals("count of nodes was wrong", 5, tree.countNodes());
    }

    @Test
    public void testAscending() {
        BinaryTree<SampleTestValue> tree = new BinaryTree<SampleTestValue>();
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(5));
        tree.insert(new SampleTestValue(6));
        tree.insert(new SampleTestValue(6));
        tree.insert(new SampleTestValue(7));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(8));
        tree.insert(new SampleTestValue(9));
        Object[] ascending = tree.dumpInAscendingOrder();
        assertEquals("array of wrong size", 5, ascending.length);
        assertTrue("wrong value detected", ((SampleTestPayload) ascending[0]).getValue() == 5);
        assertTrue("wrong value detected", ((SampleTestPayload) ascending[1]).getValue() == 6);
        assertTrue("wrong value detected", ((SampleTestPayload) ascending[2]).getValue() == 7);
        assertTrue("wrong value detected", ((SampleTestPayload) ascending[3]).getValue() == 8);
        assertTrue("wrong value detected", ((SampleTestPayload) ascending[4]).getValue() == 9);
    }

}
