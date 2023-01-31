import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Curse {
	static class Task {
		/* general data for problem */
		int N, M, A;
		int[][] ant;

		void readInput()  {
			try {
				File fin = new File("curse.in");
				Scanner sc = new Scanner(fin);
				N = sc.nextInt();
				M = sc.nextInt();
				A = sc.nextInt();
				ant = new int[A][N];
				for (int i = 0; i < A; i++) {
					for (int j = 0; j < N; j++) {
						ant[i][j] = sc.nextInt();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		void writeOutput(ArrayList<Integer> cars) {
			try {
				File fout = new File("curse.out");
				fout.createNewFile();
				FileWriter wr = new FileWriter("curse.out");
				for (int i : cars) {
					wr.write(i + " ");
				}
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ArrayList<Integer> getRezult() {
			ArrayList<Integer> cars = new ArrayList<>();

			ArrayList<Integer>[] parent = new ArrayList[M + 1];
			for (int i = 0; i <= M; i++) {
				parent[i] = new ArrayList<>();
			}

			/* build parent array lists for all cars */
			for (int i = 0, j = 0; i + 1 < A;) {
				if (ant[i][j] == ant[i + 1][j]) {
					j++;
					continue;
				} else if (ant[i][j] != ant[i + 1][j]
						&& !parent[ant[i + 1][j]].contains(ant[i][j])) {
					parent[ant[i + 1][j]].add(ant[i][j]);
				}
				j = 0;
				i++;
			}

			/* find first element */
			for (int i = 1; i <= M; i++) {
				if (parent[i].isEmpty()) {
					cars.add(i);
					break;
				}
			}

			/* build rezult array */
			while (cars.size() < M) {
				int last = cars.get(cars.size() - 1);
				for (int i = 1; i <= M; i++) {
					if (parent[i].size() == 1 && parent[i].get(0) == last) {
						cars.add(i);
						/* remove parent of i from all other arrays */
						for (ArrayList<Integer> p : parent) {
							p.remove(Integer.valueOf(last));
						}
					}
				}
			}

			return cars;
		}
		void solve() {
			readInput();
			writeOutput(getRezult());
		}
	}
	public static void main(String[] args) {
		new Task().solve();
	}
}
