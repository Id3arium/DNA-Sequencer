/**
 * @author Andres A. Campos (5338747)
 */
package cisc187.dna;

import java.util.*;

/**
 * Value class to store a BaseSequence. A BaseSequence is a sequence of characters that represent
 * either a DNA or an RNA fragment. The class stores the sequence of characters that make up the
 * fragment and the fragments type. a DNA fragment can only hold: A,C,G or T and an RNA fragment can
 * only hold: A,C,G or U. If a fragment contains no characters its type is EMPTY.
 */
public class BaseSequence {

	private static final String CR = System.lineSeparator();
	/**
	 * The valid DNA characters.
	 */
	private static final String DNA_CHARS = "AGCT";
	/**
	 * The valid RNA characters.
	 */
	private static final String RNA_CHARS = "AGCU";
	/**
	 * The characters that make up the BaseSequence.
	 */
	private List<Character> sequence;
	/**
	 * The BaseSequences type (either DNA, RNA or EMPTY).
	 */
	private SequenceType type;

	/**
	 * Create a BaseSequence with a specified sequence and type. If the sequence contains an invalid
	 * character for the given type an empty BaseSequence will be created.
	 *
	 * @param sequence the sequence of characters that the BaseSequence will contain.
	 * @param type the sequenceType of the BaseSequence.
	 */
	public BaseSequence(final String sequence, final SequenceType type) {
		char[] chars = sequence.toCharArray();
		this.sequence = new LinkedList<Character>();
		for (char c : chars) {
			if (!type.isValid(c)) {
				System.out.print("One or more invalid characters in sequence." + CR);
				this.type = SequenceType.EMPTY;
				return;
			}
		}
		this.type = type;
		for (char c : chars) {
			this.sequence.add(c);
		}
	}

	/**
	 * Create a BaseSequence with a specified sequence and type.
	 *
	 * @param sequence the sequence of characters that the BaseSequence will contain.
	 * @param type the sequenceType BaseSequence will have.
	 */
	public BaseSequence(final List<Character> sequence, final SequenceType type) {
		this.sequence = sequence;
		this.type = type;
	}

	/**
	 * Create an empty BaseSequence.
	 */
	public BaseSequence() {
		this.sequence = new LinkedList<Character>();
		this.type = SequenceType.EMPTY;
	}

	/**
	 * turns the object to a string.
	 *
	 * @return the string representation of the BaseSequence object.
	 */
	@Override
	public String toString() {
		String chars = "";
		for (char c : this.sequence) {
			chars += "" + Character.toUpperCase(c);
		}
		if (type == SequenceType.EMPTY) {
			return "[" + chars + "]";
		}
		return "[" + type + ": " + chars + "]";
	}

	/**
	 * checks if an object is equal to this BaseSequnce object.
	 *
	 * @param o the object to be compared with this object
	 * @return true if the objects are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BaseSequence)) {
			return false;
		}
		BaseSequence b = (BaseSequence) o;
		return this.sequence.equals(b.getSequence()) && this.type == b.type;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + Objects.hashCode(this.sequence);
		hash = 71 * hash + Objects.hashCode(this.type);
		return hash;
	}

	/**
	 * Clear all of the characters in the BaseSequence and change its SequenceType field to EMPTY.
	 */
	public void clear() {
		this.sequence.clear();
		this.type = SequenceType.EMPTY;
	}

	/**
	 * Replace the sequence with a clipped sequence where the characters from positions start to end
	 * (inclusive) are removed. It is an error if start has a value less than zero, or if start or
	 * end are beyond the end of the sequence. A clip command with such an error makes no alteration
	 * to the sequence. If the value for end is less than the value for start then the result is a
	 * sequence containing no characters.
	 *
	 * @param start the starting position in the sequence.
	 * @param end the ending position in the sequence.
	 * @return the clipped fragment.
	 */
	public BaseSequence clip(final int start, final int end) {
		int st = start;
		int en = end;
		List<Character> clipped = new LinkedList<Character>();
		if (st < 0) {
			System.out.print("Unable to clip fragment starting at " + st + "."
				+ CR + "Start must be between 0 and " + (this.sequence.size() - 1) + "." + CR);
			return new BaseSequence();
		} else if (st >= this.sequence.size()) {
			System.out.print("Unable to clip fragment starting at " + st + "."
				+ CR + "Start must be between 0 and " + (this.sequence.size() - 1) + "." + CR);
			return new BaseSequence();
		} else if (en >= this.sequence.size()) {
			System.out.print("Unable to clip fragment ending at " + en + "."
				+ CR + "End must be less than or equal to " + (this.sequence.size() - 1) + "." + CR);
			return new BaseSequence();
		} else if (st > en) { //clip all of the elements in the list
			st = 0;
			en = this.sequence.size() - 1;
		} 
		for (int i = 0; i <= (en - st); i++) {
			clipped.add(this.sequence.remove(st));
		}
		return new BaseSequence(clipped, this.type);
	}

	/**
	 * Replace the sequence with a clipped sequence where the characters from positions start to end
	 * (inclusive) are removed. It is an error if start has a value less than zero, or if start or
	 * end are beyond the end of the sequence. A clip command with such an error makes no alteration
	 * to the sequence. If the value for end is less than the value for start then the result is a
	 * sequence containing no characters.
	 *
	 * @param start the starting position in the sequence.
	 * @param end the ending position in the sequence.
	 * @return the clipped fragment.
	 */
	public BaseSequence clip(final int start) {
		return clip(start, this.sequence.size() - 1);
	}

	/**
	 * Get the sequenceType of this BaseSequence.
	 *
	 * @return the sequenceType of this BaseSequence.
	 */
	public SequenceType getType() {
		return this.type;
	}

	/**
	 * Get the sequence of this BaseSequence.
	 *
	 * @return the sequence of this BaseSequence.
	 */
	public List<Character> getSequence() {
		return this.sequence;
	}

	/**
	 * Make a copy of this BaseSequence. The copy will be an BaseSequence object that is equal to
	 * this BaseSequence.
	 *
	 * @return the copy of this BaseSequence.
	 */
	public BaseSequence copy() {
		String copyseq = "";
		for (Character c : this.sequence) {
			copyseq += "" + c;
		}
		SequenceType copytype = this.type;
		return new BaseSequence(copyseq, copytype);
	}

	/**
	 * Transcribe this DNA BaseSequence into a valid RNA BaseSequence. change the sequenceType of
	 * this BaseSequence into RNA only if its sequenceType is DNA otherwise do nothing. After
	 * changing the sequenceType, invert the order of the sequence and for each occurrence of the
	 * letters A,C,G, and T change them to U,G,C and A respectively.
	 */
	public void transcribe() {
		List<Character> temp = new LinkedList<Character>();
		if (this.type != SequenceType.DNA) {
			System.out.print("Can only transcribe DNA sequences." + CR);
			return;
		}
		this.type = SequenceType.RNA;
		for (int i = this.sequence.size() - 1; i >= 0; i--) {
			temp.add(this.sequence.get(i));
		}
		for (int i = 0; i < temp.size(); i++) {
			switch (Character.toUpperCase(temp.get(i))) {
				case 'A':
					this.sequence.set(i, 'U');
					break;
				case 'C':
					this.sequence.set(i, 'G');
					break;
				case 'G':
					this.sequence.set(i, 'C');
					break;
				case 'T':
					this.sequence.set(i, 'A');
			}
		}
	}
}
