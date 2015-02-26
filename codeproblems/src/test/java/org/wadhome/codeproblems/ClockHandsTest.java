package org.wadhome.codeproblems;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClockHandsTest {

    @Test
    public void testIt() {
        Assert.assertEquals(0, ClockHands.getAngleInDegrees(12, 0));
        Assert.assertEquals(180, ClockHands.getAngleInDegrees(6, 0));
        Assert.assertEquals(75, ClockHands.getAngleInDegrees(3, 30));
        Assert.assertEquals(65, ClockHands.getAngleInDegrees(1, 59));
        Assert.assertEquals(5, ClockHands.getAngleInDegrees(11, 59));
    }
}
