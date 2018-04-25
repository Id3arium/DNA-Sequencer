/**
 * @author Andres A. Campos (5338747)
 */
package cisc187.dna;

import cisc187.util.Printable;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Contains an array of BaseSequence DNA/RNA fragments that can be used by the Sequencer class.
 * Acts as a helper class for the Sequencer class by making the required operations needed to 
 * execute the commands read in from the commands file(by the Sequencer class).  
 * The size of the array cant be changed after construction and all of the elements in the array 
 * are BaseSequence objects with no characters and of sequenceType EMPTY by default. 
 */
public class FragmentList implements Editable, Printable {
	
	private static final String CR = System.lineSeparator();
	/**
	 * The size of the fragments array if no size is specified at construction.
	 */
	static int DEFAULT_SIZE = 16;
	private BaseSequence[] fragments;
	private int size;

	/**
	 * Create a FragmentList of default size.
	 */
	public FragmentList() {
		size = DEFAULT_SIZE;
		fragments = new BaseSequence[size];
		for (int i = 0; i < size; i++) {
			fragments[i] = new BaseSequence();
		}
	}
	
	/**
	 * Create a FragmentList with a specified size.
	 * @param size the size of the fragmentList
	 */
	public FragmentList(final int size) {
		this.size = size;
		fragments = new BaseSequence[size];
		for (int i = 0; i < size; i++) {
			fragments[i] = new BaseSequence();
		}
	}

	/**
	 * get the size of this fragmentList.
	 * @return the size of this fragmentList.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * turns the object to a string.
	 * @return the string representation of the FragmentList object.
	 */
	@Override
	public String toString() {
		String list = "";
		for (BaseSequence b : fragments) {
			list += b.toString();
		}
		return list;
	}

	@Override
	public void insert(final int pos, final SequenceType type, final String sequence) {
		if(!isPositionValid(pos,"insert")){
			return;
		}
		char[] chars = sequence.toCharArray();
		for (char c : chars) {
			if (!type.isValid(c)) {
				System.out.println("One or more invalid characters in sequence.");
				return;
			}
		}
		//memory leak?
		fragments[pos] = new BaseSequence(sequence, type);
	}

	/**
	 * 
	 * @param pos 
	 */
	@Override
	public void remove(final int pos) {
		if(!isPositionValid(pos,"remove")){
			return;
		}
		fragments[pos].clear();
	}

	/**
	 * 
	 * @param pos 
	 */
	@Override
	public void transcribe(final int pos) {
		if(!isPositionValid(pos,"transcribe")){
			return;
		}
		if (fragments[pos].getType() != SequenceType.DNA) {
			System.out.print("Can only transcribe DNA sequences." + CR);
			return;
		}
		fragments[pos].transcribe();
	}

	/**
	 * 
	 * @param pos
	 * @param start
	 * @param end
	 * @return 
	 */
	@Override
	public BaseSequence clip(final int pos, final int start, final int end) {
		if(!isPositionValid(pos,"clip")){
			return new BaseSequence();
		}
		return fragments[pos].clip(start, end);
	}

	/**
	 * 
	 * @param pos
	 * @param start
	 * @return 
	 */
	@Override
	public BaseSequence clip(final int pos, final int start) {
		if(!isPositionValid(pos,"clip")){
			return new BaseSequence();
		}
		return fragments[pos].clip(start);
	}

	/**
	 * 
	 * @param pos1
	 * @param pos2 
	 */
	@Override
	public void copy(final int pos1, final int pos2) {
		fragments[pos2] = fragments[pos1].copy();
	}

	/**
	 * 
	 * @param pos1
	 * @param start1
	 * @param pos2
	 * @param start2 
	 */
	@Override
	public void swap(final int pos1, final int start1, final int pos2, final int start2) {
		if(!isPositionValid(pos1,"swap")){
			return;
		}
		if(!isPositionValid(pos2,"swap")){
			return;
		}
		if (fragments[pos1].getType() != fragments[pos2].getType()) {
			System.out.print("Unable to swap sequences. Sequences must be of the same type." + CR);
			return;
		}
		if (start1 >= fragments[pos1].getSequence().size() || start2 >= fragments[pos2].getSequence().size()) {
			System.out.print("Unable to clip tails for swapping. starting positions must be smaller than"
				+ fragments[pos1].getSequence().size() + " and " + fragments[pos1].getSequence().size() + "." + CR);
			return;
		}
		// clip both tails and store them in a temp List.
		List<Character> tail1 = fragments[pos1].clip(start1).getSequence();
		List<Character> tail2 = fragments[pos2].clip(start2).getSequence();
		// store the clipped remainders in a temp list.
		List<Character> swapped1 = new LinkedList<Character>(fragments[pos1].getSequence());
		List<Character> swapped2 = new LinkedList<Character>(fragments[pos2].getSequence());
		// concatenate the clipped tails in the new swapped order.
		swapped1.addAll(tail2);
		swapped2.addAll(tail1);
		//since they both have the same type it shouldnt matter which one you call gettype() on.
		fragments[pos1] = new BaseSequence(swapped1, fragments[pos1].getType());
		fragments[pos2] = new BaseSequence(swapped2, fragments[pos1].getType());
	}

	/**
	 * 
	 */
	@Override
	public void print() {
		for (BaseSequence b : fragments) {
			System.out.print(b);
		}
		System.out.println();
	}

	/**
	 * 
	 * @param pos 
	 */
	@Override
	public void print(int pos) {
		if(!isPositionValid(pos,"print")){
			return;
		}
		System.out.print(fragments[pos] + CR);
	}

	/**
	 * Get the 
	 * @param pos the position of the BaseSequence in the Fragment List.
	 * @return 
	 */
	BaseSequence sequenceAt(final int pos) {
		return fragments[pos];
	}
	
	/**
	 * 
	 * @param pos
	 * @param command
	 * @return 
	 */
	private boolean isPositionValid(final int pos, final String command) {
		if (pos >= this.size || pos < 0) {
			System.out.print("Unable to " + command + " fragment at position "
				+ pos + "." + CR + "Position must be between 0 and " + (this.size - 1) + "." + CR);
			return false;
		}
		return true;
	}
}
