import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prinel {
	public static void main(String[] args) {
		try {
			File fin = new File("prinel.in");
			File fout = new File("prinel.out");
			fout.createNewFile();
			Scanner sc = new Scanner(fin);

			int n = sc.nextInt();
			int k = sc.nextInt();
			/* array of points */
			int[] points = new int[n];
			/* array of necessary steps to win points */
			int[] steps = new int[n];
			/* hashmap of divisors to avoid recalculating them */
			Map<Integer, ArrayList<Integer>> divisors = new HashMap<>();
			for (int i = 0; i < n; i++) {
				steps[i] = sc.nextInt();
				steps[i] = necessarySteps(steps[i], divisors);
			}
			for (int i = 0; i < n; i++) {
				points[i] = sc.nextInt();
			}

			FileWriter wr = new FileWriter("prinel.out");
			wr.write(Integer.toString(maxPoints(k, steps, points, n)));
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static int maxPoints(int k, int[] steps, int[] points, int n) {
		int[] old = new int[k + 1];

		Arrays.fill(old, 0);
		for (int i = 0; i < n; i ++) {
			int[] act = new int[k + 1];
			for (int j = 0; j <= k; j++) {
				if (j < steps[i]) {
					act[j] = old[j];
				} else {
					act[j] = Math.max(points[i] + old[j - steps[i]], old[j]);
				}
			}
			old = act;
		}
		return old[k];
	}

	/* Returns the list of divisors for given number */
	static ArrayList<Integer> getDivisors(int n) {
		ArrayList<Integer> divisors = new ArrayList<>();
		divisors.add(1);
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				divisors.add(i);
			}
		}
		divisors.add(n);
		return divisors;
	}

	static int necessarySteps(int target, Map<Integer, ArrayList<Integer>> div) {
		int starter = 1;
		int count = 0;
		while (starter < target) {
			if (!div.containsKey(starter)) {
				div.put(starter, getDivisors(starter));
			}
			ArrayList<Integer> divisors = div.get(starter);
			for (int i = divisors.size() - 1; i >= 0; i--) {
				if (starter + divisors.get(i) <= target) {
					count++;
					starter += divisors.get(i);
					break;
				}
			}
		}
		return count;
	}
}