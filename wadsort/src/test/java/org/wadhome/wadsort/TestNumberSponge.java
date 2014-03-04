package org.wadhome.wadsort;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class TestNumberSponge {

    @Test
    public void testComputeLocation() {
        int tallySetSize = 1 << NumberSpongeImpl.MAX_TALLY_BITS;
        assertEquals(0, NumberSpongeImpl.computeLocationFromValue(0));
        assertEquals(0, NumberSpongeImpl.computeLocationFromValue(1));
        assertEquals(0, NumberSpongeImpl.computeLocationFromValue(2));
        assertEquals(0, NumberSpongeImpl.computeLocationFromValue(tallySetSize - 2));
        assertEquals(0, NumberSpongeImpl.computeLocationFromValue(tallySetSize - 1));
        assertEquals(tallySetSize, NumberSpongeImpl.computeLocationFromValue(tallySetSize));
        assertEquals(tallySetSize, NumberSpongeImpl.computeLocationFromValue(tallySetSize + 1));
        assertEquals(tallySetSize, NumberSpongeImpl.computeLocationFromValue(tallySetSize + 1));
        assertEquals(tallySetSize, NumberSpongeImpl.computeLocationFromValue((2 * tallySetSize) - 2));
        assertEquals(tallySetSize, NumberSpongeImpl.computeLocationFromValue((2 * tallySetSize) - 1));
        assertEquals(tallySetSize * 2, NumberSpongeImpl.computeLocationFromValue(2 * tallySetSize));
        assertEquals(tallySetSize * 2, NumberSpongeImpl.computeLocationFromValue((2 * tallySetSize) + 1));
        assertEquals(tallySetSize * 2, NumberSpongeImpl.computeLocationFromValue((2 * tallySetSize) + 2));
    }

    @Test
    public void testTallying() throws Exception {
        NumberSpongeImpl sponge = new NumberSpongeImpl();
        sponge.absorb(7);
        sponge.absorb(6);
        sponge.absorb(5);
        sponge.absorb(5);
        sponge.absorb(5);
        sponge.absorb(2);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7777);
        sponge.absorb(7778);

        assertEquals("incorrect number of values", 6, sponge.getNumUniqueAbsorbedValues());

        // check the sorted list
        Writer stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        sponge.writeSortedData(writer);
        writer.close();
        String sorted = stringWriter.toString();
        String expectedSortedOutput =
                "2" + FileUtils.NEWLINE +
                        "5" + FileUtils.NEWLINE +
                        "5" + FileUtils.NEWLINE +
                        "5" + FileUtils.NEWLINE +
                        "6" + FileUtils.NEWLINE +
                        "7" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7777" + FileUtils.NEWLINE +
                        "7778" + FileUtils.NEWLINE;
        assertEquals("unexpected output from sorted list", expectedSortedOutput, sorted);

        stringWriter = new StringWriter();
        writer = new BufferedWriter(stringWriter);
        sponge.writeHistogramData(writer);
        writer.close();
        String histogram = stringWriter.toString();
        String expectedHistogramOutput =
                "2 - 1" + FileUtils.NEWLINE +
                        "5 - 3" + FileUtils.NEWLINE +
                        "6 - 1" + FileUtils.NEWLINE +
                        "7 - 1" + FileUtils.NEWLINE +
                        "7777 - 7" + FileUtils.NEWLINE +
                        "7778 - 1" + FileUtils.NEWLINE;
        assertEquals("unexpected output from histogram", expectedHistogramOutput, histogram);
    }

}
