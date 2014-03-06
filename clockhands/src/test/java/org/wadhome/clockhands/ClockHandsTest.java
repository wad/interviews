package org.wadhome.clockhands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClockHandsTest {

    @Test
    public void testIt() {
        assertEquals(0, ClockHands.getAngleInDegrees(12, 0));
        assertEquals(180, ClockHands.getAngleInDegrees(6, 0));
        assertEquals(75, ClockHands.getAngleInDegrees(3, 30));
        assertEquals(65, ClockHands.getAngleInDegrees(1, 59));
        assertEquals(5, ClockHands.getAngleInDegrees(11, 59));
    }

}
