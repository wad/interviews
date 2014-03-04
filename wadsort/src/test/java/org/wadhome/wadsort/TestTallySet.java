package org.wadhome.wadsort;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestTallySet {

    @Test
    public void testConstructionWithBadParameters() {
        try {
            new TallySet(0, 5, 5);
            fail("Should have thrown");
        } catch (RuntimeException e) {
            assertTrue("expected", true);
        }
        try {
            new TallySet(5, 0, 5);
            fail("Should have thrown");
        } catch (RuntimeException e) {
            assertTrue("expected", true);
        }
        try {
            new TallySet(5, 5, -1);
            fail("Should have thrown");
        } catch (RuntimeException e) {
            assertTrue("expected", true);
        }
    }

    @Test
    public void testConstructorParameters() {
        final int maxTallyBits = 10;
        final int maxTallies = 1024; // 2 ^ 10
        final int maxBytesPerTally = 8;
        final long location = 42;
        TallySet tallySet = new TallySet(maxTallyBits, maxBytesPerTally, location);
        assertEquals("location should match", location, tallySet.getLocation());
        assertEquals("span doesn't match", maxTallies, tallySet.maxTallies);
        assertEquals("bytes per tally doesn't match", maxBytesPerTally, tallySet.maxBytesPerTally);
    }

    @Test
    public void testTallying() {
        // something pretty small. This results in a tally set only encompassing 8 tallies.
        final int maxTallyBits = 3;

        // something small, so that the test won't take forever.
        final int maxBytesPerTally = 3;

        // to try something at a different location than 0
        final int location = 100;

        TallySet tallySet = new TallySet(maxTallyBits, maxBytesPerTally, location);

        // try some values out of range of this tally set
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_TOO_SMALL, tallySet.tally(location - 1));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_TOO_LARGE, tallySet.tally(location + (8)));

        // try with no tallies
        long[] tallies = tallySet.readTallies();
        assertEquals("tally was incorrect", 0, tallies[0]);
        assertEquals("tally was incorrect", 0, tallies[1]);
        assertEquals("tally was incorrect", 0, tallies[2]);
        assertEquals("tally was incorrect", 0, tallies[3]);
        assertEquals("tally was incorrect", 0, tallies[4]);
        assertEquals("tally was incorrect", 0, tallies[5]);
        assertEquals("tally was incorrect", 0, tallies[6]);
        assertEquals("tally was incorrect", 0, tallies[7]);
        assertEquals("tally count was incorrect", 0, tallySet.countTallies());

        // Try a single tally
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(102));
        tallies = tallySet.readTallies();
        assertEquals("tally was incorrect", 0, tallies[0]);
        assertEquals("tally was incorrect", 0, tallies[1]);
        assertEquals("tally was incorrect", 1, tallies[2]);
        assertEquals("tally was incorrect", 0, tallies[3]);
        assertEquals("tally was incorrect", 0, tallies[4]);
        assertEquals("tally was incorrect", 0, tallies[5]);
        assertEquals("tally was incorrect", 0, tallies[6]);
        assertEquals("tally was incorrect", 0, tallies[7]);
        assertEquals("tally count was incorrect", 1, tallySet.countTallies());

        // try several tallies
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(106));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(100));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(102));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(103));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(104));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(105));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(106));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        tallies = tallySet.readTallies();
        assertEquals("tally was incorrect", 1, tallies[0]);
        assertEquals("tally was incorrect", 0, tallies[1]);
        assertEquals("tally was incorrect", 2, tallies[2]);
        assertEquals("tally was incorrect", 1, tallies[3]);
        assertEquals("tally was incorrect", 1, tallies[4]);
        assertEquals("tally was incorrect", 1, tallies[5]);
        assertEquals("tally was incorrect", 2, tallies[6]);
        assertEquals("tally was incorrect", 5, tallies[7]);
        assertEquals("tally count was incorrect", 7, tallySet.countTallies());

        // now bump some of the values up into the third byte
        for (int i = 0; i < (65536 + 10); i++) {
            assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(102));
            assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(106));
            assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(107));
        }
        tallies = tallySet.readTallies();
        assertEquals("tally was incorrect", 1, tallies[0]);
        assertEquals("tally was incorrect", 0, tallies[1]);
        assertEquals("tally was incorrect", 65548, tallies[2]);
        assertEquals("tally was incorrect", 1, tallies[3]);
        assertEquals("tally was incorrect", 1, tallies[4]);
        assertEquals("tally was incorrect", 1, tallies[5]);
        assertEquals("tally was incorrect", 65548, tallies[6]);
        assertEquals("tally was incorrect", 65551, tallies[7]);
        assertEquals("tally count was incorrect", 7, tallySet.countTallies());
    }

    @Test
    public void testSingleLargeNumberOfTallies() {
        // something pretty small. This results in a tally set only encompassing 8 tallies.
        final int maxTallyBits = 3;

        // something small, so that the test won't take forever.
        final int maxBytesPerTally = 3;

        TallySet tallySet = new TallySet(maxTallyBits, maxBytesPerTally, 0);
        for (int i = 0; i < 520; i++) {
            assertEquals(TallySet.TALLY_ATTEMPT_RESULT.VALUE_APPROPRIATE, tallySet.tally(1));
        }

        long[] tallies = tallySet.readTallies();
        assertEquals("tally was incorrect", 0, tallies[0]);
        assertEquals("tally was incorrect", 520, tallies[1]);
        assertEquals("tally was incorrect", 0, tallies[2]);
        assertEquals("tally was incorrect", 0, tallies[3]);
        assertEquals("tally was incorrect", 0, tallies[4]);
        assertEquals("tally was incorrect", 0, tallies[5]);
        assertEquals("tally was incorrect", 0, tallies[6]);
        assertEquals("tally was incorrect", 0, tallies[7]);
        assertEquals("tally count was incorrect", 1, tallySet.countTallies());
    }

    @Test
    public void testIsMaxed() {
        assertFalse(TallySet.isUnsignedByteMaxed((byte) 0));
        assertFalse(TallySet.isUnsignedByteMaxed((byte) 1));
        assertFalse(TallySet.isUnsignedByteMaxed((byte) 127));
        assertTrue(TallySet.isUnsignedByteMaxed((byte) -1));
        assertFalse(TallySet.isUnsignedByteMaxed((byte) -2));
        assertFalse(TallySet.isUnsignedByteMaxed((byte) -127));
        assertFalse(TallySet.isUnsignedByteMaxed((byte) -128));
    }

    @Test
    public void testReadBits() {
        byte byteValue = 0;
        for (int i = 0; i < 256; i++) {
            assertEquals(i, TallySet.readUnsignedByteValue(byteValue));
            byteValue++;
        }
    }

}
