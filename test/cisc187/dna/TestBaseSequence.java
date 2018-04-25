package cisc187.dna;

import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link BaseSequence} Unit tests
 */
public class TestBaseSequence {
  private BaseSequence dna;
  private final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
  private static final String CR = System.lineSeparator();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(stdout));
  }
  @After
  public void cleanUp() {
    System.setOut(null);
    dna = null;
  }


  /////////////////////////////////////////////////////////////////////////
  //  Constructor tests
  /////////////////////////////////////////////////////////////////////////

  @Test
  public void initDefault() {
    dna = new BaseSequence();
    assertEquals("Failed to get the correct type for a new empty sequence.", SequenceType.EMPTY, dna.getType());
  }

  @Test
  public void initDNAType() {
    dna = new BaseSequence("ACGTACGT", SequenceType.DNA);
    assertEquals("Failed to get the correct type for a new DNA sequence.", SequenceType.DNA, dna.getType());
  }

  @Test
  public void initDNASeq() {
    dna = new BaseSequence("ACGTACGT", SequenceType.DNA);
    assertEquals("Failed to set the correct sequence for a new DNA sequence.", "[DNA: ACGTACGT]", dna.toString());
  }
  @Test
  public void initInvalidDNA() {
    dna = new BaseSequence("U", SequenceType.DNA);
    assertEquals("Failed to detect invalid DNA sequence.", "One or more invalid characters in sequence." + CR, stdout.toString());
  }


  @Test
  public void initRNAType() {
    dna = new BaseSequence("ACGUACGU", SequenceType.RNA);
    assertEquals("Failed to get the correct type for a new RNA sequence.", SequenceType.RNA, dna.getType());
  }

  @Test
  public void initRNASeq() {
    dna = new BaseSequence("ACGUACGU", SequenceType.RNA);
    assertEquals("Failed to set the correct sequence for a new RNA sequence.", "[RNA: ACGUACGU]", dna.toString());
  }
  @Test
  public void initInvalidRNA() {
    dna = new BaseSequence("T", SequenceType.RNA);
    assertEquals("Failed to detect invalid DNA sequence.", "One or more invalid characters in sequence."+ CR, stdout.toString());
  }

  @Test
  public void initEmptyRNASeq() {
    List<Character> c = new ArrayList<>();
    dna = new BaseSequence(c, SequenceType.RNA);
    assertEquals("Failed to init a new empty RNA sequence.", "[RNA: ]", dna.toString());
  }



  /////////////////////////////////////////////////////////////////////////
  //  API tests: clear
  /////////////////////////////////////////////////////////////////////////

  @Test
  public void clearDNA() {
    dna = new BaseSequence("ACGTACGT", SequenceType.DNA);
    dna.clear();
    assertEquals("Failed to clear a DNA sequence type.", SequenceType.EMPTY, dna.getType());
    assertEquals("Failed to clear a DNA sequence.", "[]", dna.toString());
  }

  /////////////////////////////////////////////////////////////////////////
  //  API tests: copy
  /////////////////////////////////////////////////////////////////////////

  @Test
  public void copyDNA() {
    dna = new BaseSequence("ACGTACGT", SequenceType.DNA);
    BaseSequence dna2 = dna.copy();
    assertEquals("Failed to copy a DNA sequence.", "[DNA: ACGTACGT]", dna2.toString());
  }
  @Test
  public void copyDNAisDifferentObject() {
    dna = new BaseSequence("ACGTACGT", SequenceType.DNA);
    BaseSequence dna2 = dna.copy();
    assertFalse("Copy of a DNA sequence is actually the same object.", dna2 == dna);
  }

  /////////////////////////////////////////////////////////////////////////
  //  API tests: transcribe
  /////////////////////////////////////////////////////////////////////////

  @Test
  public void transcribeDNA() {
    dna = new BaseSequence("ACGTACG", SequenceType.DNA);
    dna.transcribe();
    assertEquals("Failed to transcribe DNA sequence.", "[RNA: CGUACGU]", dna.toString());
  }

  @Test
  public void transcribeEmptySequence() {
    dna = new BaseSequence("", SequenceType.DNA);
    dna.transcribe();
    assertEquals("Failed to transcribe DNA sequence.", "[RNA: ]", dna.toString());
  }

  @Test
  public void transcribeRNA() {
    dna = new BaseSequence("AAAAAAA", SequenceType.RNA);
    dna.transcribe();
    assertEquals("Failed to show RNA transcription error.", "Can only transcribe DNA sequences."+ CR, stdout.toString());
  }


  /////////////////////////////////////////////////////////////////////////
  //  API tests: clip
  /////////////////////////////////////////////////////////////////////////

  @Test
  public void clip4() {
    dna = new BaseSequence("AACTTGA", SequenceType.DNA);
    dna.clip(2,5);
    assertEquals("Failed to clip DNA sequence from 3 to 6.", "[DNA: AAA]", dna.toString());
  }

  @Test
  public void clipAll() {
    dna = new BaseSequence("ACGTACG", SequenceType.DNA);
    dna.clip(0,-1);
    assertEquals("Failed to clip entire DNA sequence (end < start).", "[DNA: ]", dna.toString());
  }

  @Test
  public void clipStartLessThanZero() {
    dna = new BaseSequence("ACGTACG", SequenceType.DNA);
    dna.clip(-1,4);
    assertEquals("Failed to detect invalid sequence clip start", "Unable to clip fragment starting at -1."+ CR+"Start must be between 0 and 6."+ CR, stdout.toString());
  }

  @Test
  public void clipStartMoreThanLength() {
    dna = new BaseSequence("ACGTACG", SequenceType.DNA);
    dna.clip(7,0);
    assertEquals("Failed to detect invalid sequence clip start", "Unable to clip fragment starting at 7."+ CR+"Start must be between 0 and 6."+ CR, stdout.toString());
  }

  @Test
  public void clipEndMoreThanLength() {
    dna = new BaseSequence("ACGTACG", SequenceType.DNA);
    dna.clip(2,7);
    assertEquals("Failed to detect invalid sequence clip end", "Unable to clip fragment ending at 7."+ CR+"End must be less than or equal to 6."+ CR, stdout.toString());
  }



}
