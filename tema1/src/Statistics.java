import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Scanner;

public class Statistics {
	public static void main(String[] args) {
		try {
			File fin = new File("statistics.in");
			File fout = new File("statistics.out");
			fout.createNewFile();
			Scanner sc = new Scanner(fin);
			FileWriter wr = new FileWriter("statistics.out");

			int n = sc.nextInt();
			String[] words = new String[n];
			/* read the endline character before reading the words */
			sc.nextLine();
			for (int i = 0; i < n; i++) {
				words[i] = sc.nextLine();
			}

            wr.write(Integer.toString(getStatistics(words)));
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int getStatistics(String[] words) {
		Map<String, Map<Character, Integer>> freq = new HashMap<>();
		ArrayList<Character> chs = frequencyCount(words, freq);

		/* return value */
		int maxi = -1;
		/* select words */
		for (char c : chs) {
			/* sort by each value */
			sortByChar(words, c, freq);
			int count = 0;
			Map<Character, Integer> freqTot = new HashMap<>();
			for (String word : words) {
				updateFreq(freqTot, freq.get(word));
				if (freqTot.get(c) > 0) {
					count++;
				}
			} //da un break in else la countu ala, incearca si cu litera dominanta
			if (count > maxi) {
				maxi = count;
			}
		}
		return maxi;
	}

	static void updateFreq(Map<Character, Integer> freqUp, Map<Character, Integer> freqW) {
		freqW.forEach((key, value) -> {
			if (!freqUp.containsKey(key)) {
				freqUp.put(key, value);
			} else {
				freqUp.put(key, freqUp.get(key) + value);
			}
		});
	}

	/* sorts the array by the frequency of the given char */
	static void sortByChar(String[] words, char ch, Map<String, Map<Character, Integer>> freq) {
		Arrays.sort(words, (o1, o2) -> {
			if (!freq.get(o1).containsKey(ch)) {
				freq.get(o1).put(ch, -o1.length());
			}
			if (!freq.get(o2).containsKey(ch)) {
				freq.get(o2).put(ch, -o2.length());
			}
			return freq.get(o2).get(ch) - freq.get(o1).get(ch);
		});
	}

	/* returns a list of character
	 *  calculates and keeps the frequency of each letter for each word */
	static ArrayList<Character> frequencyCount(String[] words,
											   Map<String, Map<Character, Integer>> freq) {
		ArrayList<Character> chs = new ArrayList<>();
		for (String w : words) {
			freq.put(w, new HashMap<>());
			for (int i = 0; i < w.length(); i++) {
				char chr = w.charAt(i);
				if (!freq.get(w).containsKey(chr)) {
					int count = (int) w.chars().filter(ch -> ch == chr).count();
					count = count * 2 - w.length();
					freq.get(w).put(chr, count);
					if (!chs.contains(chr)) {
						chs.add(chr);
					}
				}
			}
		}
		return chs;
	}

}
