import java.util.*;
import java.io.*;

public class Game {
	public static void main(String args[]){
		String randomword;
		ArrayList<String> remainingwords = new ArrayList<String>();
		ArrayList<String> allwords = new ArrayList<String>();
		Random x = new Random();
		boolean cheatstatus = true;
		int RAND_MIN = 3, //controlling this for now >:D
			RAND_MAX = 8;
		int tries = 0;
		int randLength = x.nextInt((RAND_MAX - RAND_MIN) + 1) + RAND_MIN;
		System.out.println("This is the length random chosen:" + randLength);
		try {
			Scanner words = new Scanner(new File("word_list.txt"));
			while (words.hasNextLine()) {
				String word = words.nextLine();
				if (word.length() == randLength) {
					allwords.add(word);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists");
		}
	//	System.out.println(allwords);
	//	System.out.println("Of course, the user won't be able to see this stuff :D");
		String gametext = "";
		for (int i = 0; i<randLength; i++) {
			gametext += "_";
		}
		System.out.println("Guess a letter or the word " + gametext);
		Scanner in = new Scanner(System.in);
		String guess = "";
		ArrayList<String> test = new ArrayList<String>();
		System.out.println("You have " + 10 + " tries.");
		while (tries != 10 && guess.contentEquals(allwords.get(0)) == false) {
			guess = in.next();
			tries++;
			System.out.println("You've got " + (10-tries) +  " tries left.");
			if (cheatstatus) {
				if (allwords.indexOf(guess) > -1) {
					//check before removing
					test.clear();
					test.addAll(allwords);
					test.remove(allwords.indexOf(guess));
					if (test.size() != 0) {
						allwords.remove(allwords.indexOf(guess));
					//	System.out.println("Computer cheated by removing the word " + guess);
					//	System.out.println(allwords);
					} else {
					//	System.out.println("Can't cheat no more. Let's settle on a random word from the group");
						randomword = allwords.get(x.nextInt(allwords.size()));
						remainingwords.add(randomword);
						allwords.retainAll(remainingwords);
					//	System.out.println(allwords);
					}
				}
				// removing words that contain a character
				boolean allremoved = false; // need to assume that all words containing letter haven't been removed
				while (allremoved == false) {
					test.clear();
					test.addAll(allwords);
					for (int i=0; i<test.size(); i++) {
						String wordtoremove;
						if (test.get(i).contains(guess)) {
							wordtoremove = test.get(i);
							//check before removing
							test.remove(test.indexOf(wordtoremove));
							i = -1; // if we remove something, start index resets to 0
						}
					}
					if (test.size() != 0) {
						for (int i=0; i<allwords.size(); i++) {
							String wordtoremove;
							if (allwords.get(i).contains(guess)) {
								wordtoremove = allwords.get(i);
								//check before removing
								allwords.remove(allwords.indexOf(wordtoremove));
					//			System.out.println("Computer cheated by removing words with " + guess);
					//			System.out.println(allwords);
								i = -1; // if we remove something, start index resets to 0
							}
						}
					} else {
					//	System.out.println("Can't cheat no more. Let's settle on a random word from the group");
						randomword = allwords.get(x.nextInt(allwords.size()));
						remainingwords.add(randomword);
						allwords.retainAll(remainingwords);
					//	System.out.println(allwords);
						cheatstatus = false;
					}
					allremoved = true;
				}
			}
			if (cheatstatus == false) {
				String correctword = allwords.get(0);
				for (int i=0; i<correctword.length(); i++) {
					if (correctword.substring(i, i+1).contentEquals(guess)) {
						gametext = gametext.substring(0, i) + guess + gametext.substring(i+1);
					}
				}
			}
			if (gametext.contains("_") == false) {
				System.out.println("Looks like we have a winner!");
				guess = allwords.get(0);
			} else {
				System.out.println("Guess a letter or the word " + gametext);
			}
		}
		
		if (guess.contentEquals(allwords.get(0))){
			System.out.println("You won! Somehow!");
		} else if (tries == 10) {
			System.out.println("You've lost dude!");
		}
	}
}
