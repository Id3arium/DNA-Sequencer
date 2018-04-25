/**
 * @author Andres A. Campos (5338747)
 */
package cisc187.dna;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Sequencer {

	private static final String CR = System.lineSeparator();

	private static FragmentList frags;
	private static int maxSize;
	private static final int MAX_SIZE = 100;

	/**
	 * Not an instantiable class
	 */
	private Sequencer() {
	}

	public static void main(String[] args) {
		if (args.length < 2 || args.length < 1 || Integer.parseInt(args[0]) > MAX_SIZE) {
			usage();
		} else {
			maxSize = Integer.parseInt(args[0]);
			frags = new FragmentList(maxSize);
			processFile(args[1]);
		}
	}

	private static void processFile(String filename) {
		try {
			Scanner sc = new Scanner(new File(filename));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					processCommand(new Scanner(nextLine));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void processCommand(final Scanner line) {
		if (line.hasNext()) {
			switch (line.next().toLowerCase()) {
				case "insert":
					parseInsert(line);
					break;
				case "remove":
					parseRemove(line);
					break;
				case "print":
					parsePrint(line);
					break;
				case "clip":
					parseClip(line);
					break;
				case "copy":
					parseCopy(line);
					break;
				case "swap":
					parseSwap(line);
					break;
				case "transcribe":
					parseTranscribe(line);
					break;
				default:
					System.out.println("Invalid command.");
					break;
			}
		} else {
			//line is not empty but doesnt have a command. Do nothing.
		}
	}

	private static void parseInsert(final Scanner line) {
		int pos = Integer.parseInt(line.next());
		SequenceType type;
		String sequence;
		switch (line.next().toUpperCase()) {
			case ("DNA"):
				type = SequenceType.DNA;
				break;
			case ("RNA"):
				type = SequenceType.RNA;
				break;
			default:
				type = SequenceType.EMPTY;
		}
		sequence = line.next();
		frags.insert(pos, type, sequence);
	}

	private static void parseRemove(final Scanner line) {
		int pos = Integer.parseInt(line.next());
		frags.remove(pos);
	}

	private static void parsePrint(final Scanner line) {
		if (line.hasNext()) {
			frags.print(Integer.parseInt(line.next()));
		} else {
			frags.print();
		}
	}

	private static void parseClip(final Scanner line) {
		int pos = Integer.parseInt(line.next());
		int start = Integer.parseInt(line.next());
		if (line.hasNext()) {
			frags.clip(pos, start, Integer.parseInt(line.next()));
		} else {
			frags.clip(pos, start);
		}
	}

	private static void parseCopy(final Scanner line) {
		int pos1 = Integer.parseInt(line.next());
		int pos2 = Integer.parseInt(line.next());
		frags.copy(pos1, pos2);
	}

	private static void parseSwap(final Scanner line) {
		int pos1 = Integer.parseInt(line.next());
		int start1 = Integer.parseInt(line.next());
		int pos2 = Integer.parseInt(line.next());
		int start2 = Integer.parseInt(line.next());
		frags.swap(pos1, start1, pos2, start2);
	}

	private static void parseTranscribe(final Scanner line) {
		int pos = Integer.parseInt(line.next());
		frags.transcribe(pos);
	}

	/**
	 * Show information on proper usage of the main method of this class.
	 */
	private static void usage() {
		System.out.println("Error running sequencer. Invalid command line arguments!");
		System.out.println("usage:");
		System.out.println("\tjava Sequencer <size> <filename>");
		System.out.println("\twhere");
		System.out.println("\tsize = maximum number of fragments this sequenceer can hold.");
		System.out.println("\t       size must be > 0 and <= " + MAX_SIZE);
		System.out.println("\tfilename = name of the commands file containing valid sequencer commands to process.");
	}

}
