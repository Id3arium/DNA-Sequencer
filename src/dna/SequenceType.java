package cisc187.dna;

import java.util.HashMap;
import java.util.Map;

public enum SequenceType {
  /** 
   * A DNA sequence.
   * The four characters A, C, G, and T are the only possible values in a DNA sequence.
   */
  DNA("ACGT"),
  /** 
   * An RNA sequence.
   * The four characters A, C, G, and U are the only possible values in a RNA sequence.
   */
  RNA("ACGU"),
  /** 
   * A fragment in which no sequence is defined.
   * No characters are valid entries in an empty sequence.
   */
  EMPTY("");

  /**
   * A map in which to store mappings between SequenceTypes and their common names.
   * Currently, they are pretty redundant, but this gives us the flexibility to 
   * add entries such as 'Messenger RNA' later with no impacts to users.
   */
  private static final Map<String, SequenceType> map = new HashMap<>();

  /**
   * Create the enum.
   * @param chars the valid characters for this enum
   */
   private String validChars;
   
  SequenceType(String chars) {
	  this.validChars = chars;
  }

  public boolean isValid(char c){
	  return validChars.indexOf(Character.toUpperCase(c)) >= 0; 
  }
}


