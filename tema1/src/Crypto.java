import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;


public class Crypto {
	public static void main(String[] args) {
		try {
			File fin = new File("crypto.in");
			File fout = new File("crypto.out");
			fout.createNewFile();

			Scanner sc = new Scanner(fin);
			String K;
			String S;
			sc.nextLine();
			K = sc.nextLine();
			S = sc.nextLine();

			FileWriter wr = new FileWriter("crypto.out");
			wr.write(Integer.toString(getCrypto(K, S)));
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getCrypto(String K, String S) {
		/* gets list of all characters */
		LinkedHashSet<Character> set = new LinkedHashSet<>();
		for (int i = 0; i < S.length(); i++) {
			set.add(S.charAt(i));
		}
		/* hashmap for counting appearances */
		Map<String, Integer> keys = new HashMap<>();
		return buildKey(K, set, S, 0, keys);
	}

	private static int buildKey(String string, LinkedHashSet<Character> set, String sub,
								int index, Map<String, Integer> keys) {
		/* string is ready */
		if (index == string.length()) {
			return numberAppearance(string, sub, 0, 0, keys);
		}
		int count = 0;
		/* for each '?' replace with a character from the character set */
		if (string.charAt(index) == '?') {
			for (Character c : set) {
				/* build new word */
				StringBuilder newString = new StringBuilder(string);
				newString.setCharAt(index, c);
				count += buildKey(newString.toString(), set,  sub, index + 1,keys);
			}

		} else {
			count += buildKey(string, set, sub,index + 1, keys);
		}
		return count;
	}

	/* returns number of appearances of subsequence */
	private static int numberAppearance(String str, String sub, int iStr,
										int iSub, Map<String, Integer> keys) {
		if (iSub == sub.length()) {
			return 1;
		}
		if (iStr == str.length()) {
			return 0;
		}
		/* check it has already been calculated by subsequence searched and current substring
		* "has this subsequence been searched in this substring?" */
		String key = str.substring(iStr) + "|" + sub.substring(iSub);
		if (!keys.containsKey(key)) {
			int count = 0;
			for (int i = iStr; i < str.length(); i++) {
				if (str.charAt(i) == sub.charAt(iSub)) {
					count += numberAppearance(str, sub, i + 1, iSub + 1, keys);
				}
			}
			keys.put(key, count);
		}
		return keys.get(key);
	}
}
