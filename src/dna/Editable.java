package cisc187.dna;


/**
 * Defines actions that can be taken on editable BaseSequences.
 *
 * @author Dave Parillo
 */
public interface Editable {

  /** 
   * Inserts the sequence to position pos in the fragment list. 
   * The type must {@link SequenceType} DNA or RNA. 
   * The {@code sequence} is checked to verify it contains only appropriate letters for its type. 
   * If not, the insert operation is in error and no change is made to the sequence list. 
   * If there is already a sequence at {@code pos} and if {@code sequence} is syntactically correct, 
   * then the new sequence replaces the old one at that position. 
   * <p>
   * It is acceptable that sequence be null (contain no characters) in which case a 
   * null sequence will be stored at {@code pos}. 
   * </p>
   * Note that a null {@code sequence} in a sequence list slot is different from an empty slot.
   * @param pos the target position within the fragment list
   * @param type the sequence type of DNA sequence being added (DNA, RNA)
   * @param sequence the actual sequence to be added
   */
  void insert(final int pos, final SequenceType type, final String sequence);

  /**
   * Remove the sequence at position {@code pos} in the fragment list. 
   * @param pos the target position within the fragment list
   */
  void remove(final int pos);

  /**
   * Transcribe the sequence at position {@code pos} in the fragment list 
   * from a DNA sequence into an RNA sequence. 
   * @param pos the target position within the fragment list
   */
  void transcribe(final int pos);

  /** 
   * Replace the sequence at fragment position {@code pos} with 
   * a clipped version of the sequence. 
   * The clipped version is that part of the sequence beginning at character position 
   * {@code start} and ending with character position {@code end}. 
   * <p>
   * It is an error if {@code start} has a value less than one, or 
   * if {@code start} or  {@code end} are beyond the end of the sequence. 
   * A clip command with such an error makes no alteration to the sequence.
   * <p>
   * If there is no sequence at this slot, output a suitable message. 
   * </p>
   * <p>
   * If the value for {@code end} is less than the value for {@code start} 
   * then the result should be a sequence containing no characters.   
   * </p>
   * @param pos the target position within the fragment list
   * @param start the start location within the sequence to be clipped
   * @param end the final location within the sequence to be clipped
   * @return the part of the sequence that was clipped
   */
  BaseSequence clip(final int pos, final int start, final int end);

  /** 
   * Replace the sequence at fragment position {@code pos} with a clipped version of the sequence. 
   * The clipped version is that part of the sequence beginning at character position 
   * {@code start} and cnotinuing to the end of the sequence.
   * <p>
   * It is an error if {@code start} has a value less than one, or 
   * if {@code start} is beyond the end of the sequence. 
   * A clip command with such an error makes no alteration to the sequence.
   * <p>
   * If there is no sequence at this slot, output a suitable message. 
   * </p>
   * @param pos the target position within the fragment list
   * @param start the start location within the sequence to be clipped
   * @return the part of the sequence that was clipped
   */
  BaseSequence clip(final int pos, final int start);

  /**
   * Copy the sequence in position {@code pos1} to {@code pos2}. 
   * If there is no sequence at {@code pos1}, output a suitable message 
   * and do not modify the sequence at {@code pos2}.
   * @param pos1 the source position within the fragment list
   * @param pos2 the destination position within the fragment list
   */
  void copy(final int pos1, final int pos2);

  /**
   * Swap the tails of the sequences at fragment positions {@code pos1} and {@code pos2}. 
   *
   * The tail for {@code pos1} begins at character {@code start1} and 
   * the tail for {@code pos2} begins at character {@code start2}. 
   * <p>
   * It is an error if the value of the start position is greater than the length 
   * of the sequence or less than 1. 
   * If the length of a sequence is {@code n}, the start position may be {@code n}, 
   * meaning that the tail of the other sequence is added 
   * (i.e., a tail of null length is being swapped). 
   * </p>
   * <p>
   * The swap operation should be reported as an error 
   * if the two sequences are not of the same type, or 
   * if one of the slots does not contain a sequence. 
   * In either case, no change should be made to the sequences.
   * </p>
   *
   * @param pos1 the source position within the fragment list
   * @param start1 the starting character position within the pos1 fragment
   * @param pos2 the destination position within the fragment list
   * @param start2 the starting character position within the pos2 fragment
   */
  void swap(final int pos1, final int start1, final int pos2, final int start2);
}
