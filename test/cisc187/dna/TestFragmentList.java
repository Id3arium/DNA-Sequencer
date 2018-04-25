package cisc187.dna;

import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link FragmentList} Unit tests
 */
public class TestFragmentList {

    private FragmentList frag;
    private final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
    private static final String CR = System.lineSeparator();
    private static final int SIZE = 5;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(stdout));
        frag = new FragmentList(SIZE);
        fillSequence();
    }

    @After
    public void cleanUp() {
        System.setOut(null);
        frag = null;
    }

    private void fillSequence() {
        frag.insert(0, SequenceType.DNA, "AAAA");
        frag.insert(1, SequenceType.RNA, "CCCC");
        frag.insert(2, SequenceType.DNA, "GGGG");
        frag.insert(3, SequenceType.RNA, "UUUU");
        frag.insert(4, SequenceType.DNA, "TTTT");
        frag.print();
        assertEquals("Failed to establish preconditions.", "[DNA: AAAA][RNA: CCCC][DNA: GGGG][RNA: UUUU][DNA: TTTT]" + CR, stdout.toString());
        stdout.reset();
    }

  /////////////////////////////////////////////////////////////////////////
    //  Constructor tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void initDefaultSize() {
        frag = new FragmentList();
        assertEquals("Failed to get the correct size for a new empty fragment list.", 16, frag.getSize());
    }

    @Test
    public void initNonDefaultSize() {
        frag = new FragmentList(3);
        assertEquals("Failed to get the correct size for a new empty fragment list.", 3, frag.getSize());
    }

  /////////////////////////////////////////////////////////////////////////
    //  print and toString tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void testToStringEmpty() {
        frag = new FragmentList(SIZE);
        assertEquals("Failed to toString empty fragment list.", "[][][][][]", frag.toString());
    }

    @Test
    public void testPrintEmpty() {
        frag = new FragmentList(SIZE);
        frag.print();
        assertEquals("Failed to print empty fragment list.", "[][][][][]" + CR, stdout.toString());
    }

    @Test
    public void testPrintEmptyItem() {
        frag = new FragmentList(SIZE);
        frag.print(0);
        assertEquals("Failed to print empty fragment list first element.", "[]" + CR, stdout.toString());
    }

    @Test
    public void testPrintEmptyItemLast() {
        frag = new FragmentList(SIZE);
        frag.print(SIZE - 1);
        assertEquals("Failed to print empty fragment list last element.", "[]" + CR, stdout.toString());
    }

    @Test
    public void testPrintEmptyItemBeyondLast() {
        frag = new FragmentList(SIZE);
        frag.print(SIZE);
        assertEquals("Failed to display suitable print error", "Unable to print fragment at position 5." + CR + "Position must be between 0 and 4." + CR, stdout.toString());
    }

    @Test
    public void testPrintEmptyItemBeforeFirst() {
        frag = new FragmentList(SIZE);
        frag.print(-1);
        assertEquals("Failed to display suitable print error", "Unable to print fragment at position -1." + CR + "Position must be between 0 and 4." + CR, stdout.toString());
    }

  /////////////////////////////////////////////////////////////////////////
    //  insert API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void testOverwriteAt0() {
        frag.insert(0, SequenceType.DNA, "ACGT");
        frag.print(0);
        assertEquals("Failed to insert fragment list at position 0.", "[DNA: ACGT]" + CR, stdout.toString());
    }

    @Test
    public void testInsertSeqAt2() {
        frag.insert(2, SequenceType.RNA, "ACGU");
        frag.print();
        assertEquals("Failed to insert fragment list at position 2.", "[DNA: AAAA][RNA: CCCC][RNA: ACGU][RNA: UUUU][DNA: TTTT]" + CR, stdout.toString());
    }

    @Test
    public void testInsertSeqAt4() {
        frag.insert(4, SequenceType.RNA, "ACGU");
        frag.print();
        assertEquals("Failed to insert fragment list at position 2.", "[DNA: AAAA][RNA: CCCC][DNA: GGGG][RNA: UUUU][RNA: ACGU]" + CR, stdout.toString());
    }

    @Test
    public void testInsertDnaAt5() {
        frag.insert(5, SequenceType.DNA, "ACGT");
        assertEquals("Failed to detect invalid insert.", "Unable to insert fragment at position 5." + CR + "Position must be between 0 and 4." + CR, stdout.toString());
    }

    @Test
    public void testInsertEmptyItem() {
        frag.insert(0, SequenceType.EMPTY, "");
        frag.print(0);
        assertEquals("Failed to insert empty RNA fragment.", "[]" + CR, stdout.toString());
    }

    @Test
    public void testInsertInvalidDNA() {
        frag.insert(0, SequenceType.DNA, "U");
        assertEquals("Failed to detect invalid insert.", "One or more invalid characters in sequence." + CR, stdout.toString());
    }

    @Test
    public void testInsertInvalidRNA() {
        frag.insert(0, SequenceType.RNA, "T");
        assertEquals("Failed to detect invalid insert.", "One or more invalid characters in sequence." + CR, stdout.toString());
    }

  /////////////////////////////////////////////////////////////////////////
    //  remove API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void testRemoveDnaAt5() {
        frag.remove(5);
        assertEquals("Failed to detect invalid remove.", "Unable to remove fragment at position 5." + CR + "Position must be between 0 and 4." + CR, stdout.toString());
    }

    @Test
    public void testRemoveSeqAt2() {
        frag.remove(2);
        frag.print();
        assertEquals("Failed to remove fragment list at position 2.", "[DNA: AAAA][RNA: CCCC][][RNA: UUUU][DNA: TTTT]" + CR, stdout.toString());
    }

    @Test
    public void testRemoveSeqAt4() {
        frag.remove(4);
        frag.print();
        assertEquals("Failed to remove fragment list at position 4.", "[DNA: AAAA][RNA: CCCC][DNA: GGGG][RNA: UUUU][]" + CR, stdout.toString());
    }

    @Test
    public void testRemoveSeqAt0() {
        frag.remove(0);
        frag.print();
        assertEquals("Failed to remove fragment list at position 0.", "[][RNA: CCCC][DNA: GGGG][RNA: UUUU][DNA: TTTT]" + CR, stdout.toString());
    }

  /////////////////////////////////////////////////////////////////////////
    //  transcribe API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void transcribeSeqAAAA() {
        frag.transcribe(0);
        frag.print(0);
        assertEquals("Failed to transcribe fragment list at position 0.", "[RNA: UUUU]" + CR, stdout.toString());
    }

    @Test
    public void transcribeSeqTTTT() {
        frag.transcribe(4);
        frag.print(4);
        assertEquals("Failed to transcribe fragment list at position 4.", "[RNA: AAAA]" + CR, stdout.toString());
    }

    @Test
    public void transcribeSeqAt4() {
        frag.insert(4, SequenceType.DNA, "ggggtttt");
        frag.transcribe(4);
        frag.print(4);
        assertEquals("Failed to transcribe fragment list at position 4.", "[RNA: AAAACCCC]" + CR, stdout.toString());
    }

    @Test
    public void transcribeSeqRNAError() {
        frag.transcribe(1);
        assertEquals("Failed to detect invalid RNA transcribe event.", "Can only transcribe DNA sequences." + CR, stdout.toString());

    }

  /////////////////////////////////////////////////////////////////////////
    //  clip API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void clipACGTStart() {
        frag.insert(0, SequenceType.DNA, "ACGT");
        BaseSequence clipped = frag.clip(0, 0, 1);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to clip first two characters from fragment.", new BaseSequence("GT", SequenceType.DNA), seq);
        assertEquals("Failed to return clipped data.", new BaseSequence("AC", SequenceType.DNA), clipped);
    }

    @Test
    public void clipAll() {
        frag.insert(0, SequenceType.DNA, "ACGTACG");
        BaseSequence clipped = frag.clip(0, 0, -1);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to clip all characters from fragment.", new BaseSequence("", SequenceType.DNA), seq);
        assertEquals("Failed to return clipped data.", new BaseSequence("ACGTACG", SequenceType.DNA), clipped);
    }

    @Test
    public void clip4Items() {
        frag.insert(0, SequenceType.DNA, "AACTTGA");
        BaseSequence clipped = frag.clip(0, 2, 5);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to clip 4 characters from fragment.", new BaseSequence("AAA", SequenceType.DNA), seq);
        assertEquals("Failed to return clipped data.", new BaseSequence("CTTG", SequenceType.DNA), clipped);
    }

    @Test
    public void clipAllNoEnd() {
        frag.insert(0, SequenceType.DNA, "ACGTACG");
        BaseSequence clipped = frag.clip(0, 0);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to clip all characters from fragment.", new BaseSequence("", SequenceType.DNA), seq);
        assertEquals("Failed to return clipped data.", new BaseSequence("ACGTACG", SequenceType.DNA), clipped);
    }

    @Test
    public void clipLastThree() {
        frag.insert(0, SequenceType.DNA, "AAACCCC");
        BaseSequence clipped = frag.clip(0, 3);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to clip 3 characters from fragment.", new BaseSequence("AAA", SequenceType.DNA), seq);
        assertEquals("Failed to return clipped data.", new BaseSequence("CCCC", SequenceType.DNA), clipped);
    }

    @Test
    public void clipBeyondLast() {
        frag.insert(0, SequenceType.DNA, "AAA");
        BaseSequence clipped = frag.clip(0, 3);
        BaseSequence seq = frag.sequenceAt(0);
        assertEquals("Failed to detect clip beyond end of fragment.", "Unable to clip fragment starting at 3." + CR + "Start must be between 0 and 2."+CR, stdout.toString());
    }

  /////////////////////////////////////////////////////////////////////////
    //  copy API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void copyFrom0To1() {
        frag.copy(0, 1);
        frag.print(1);
        assertEquals("Failed to copy fragment position 0 to pos 1.", "[DNA: AAAA]" + CR, stdout.toString());
    }

  /////////////////////////////////////////////////////////////////////////
    //  swap API tests
    /////////////////////////////////////////////////////////////////////////
    @Test
    public void swapAt4() {
        frag.insert(0, SequenceType.DNA, "AAAACCCC");
        frag.insert(1, SequenceType.DNA, "GGGGTTTT");
        frag.swap(0, 4, 1, 4);
        BaseSequence zero = frag.sequenceAt(0);
        BaseSequence one = frag.sequenceAt(1);
        assertEquals("Failed to swap C's and T's .", new BaseSequence("AAAATTTT", SequenceType.DNA), zero);
        assertEquals("Failed to swap C's and T's .", new BaseSequence("GGGGCCCC", SequenceType.DNA), one);
    }

}
