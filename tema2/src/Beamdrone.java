import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Beamdrone {
	static class Task {
		/* date ce tin de problema */
		File fin = new File("beamdrone.in");
		File fout = new File("beamdrone.out");

		int n, m;
		int xi, yi, xf, yf;
		int[][] map;
		boolean[][] viz;
		int inf = 100000;

		Queue<Integer> qx = new LinkedList<>();
		Queue<Integer> qy = new LinkedList<>();
		Queue<Boolean> qvert = new LinkedList<>();

		void readInput()  {
			try {
				Scanner sc = new Scanner(fin);
				n = sc.nextInt();
				m = sc.nextInt();
				xi = sc.nextInt();
				yi = sc.nextInt();
				xf = sc.nextInt();
				yf = sc.nextInt();
				// read endline character
				sc.nextLine();

				map = new int[n][m];
				viz = new boolean[n][m];
				for (int i = 0; i < n; i++) {
					String line = sc.nextLine();
					for (int j = 0; j < m; j++) {
						if (line.charAt(j) == 'W') {
							map[i][j] = -1;
							continue;
						}
						map[i][j] = inf;
						viz[i][j] = false;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean validNei(int neix, int neiy) {
			return !(neix < 0 || neiy < 0 || neix >= n || neiy >= m
					|| map[neix][neiy] == -1 || (neix == xi && neiy == yi));
		}

		void processDif(int x, int y, int neix, int neiy,
						boolean dirCheck, boolean dirAdd) {
			if (validNei(neix, neiy) && dirCheck) {
				if (!viz[neix][neiy] || map[neix][neiy] >= map[x][y] + 1) {
					map[neix][neiy] = map[x][y] + 1;
					viz[neix][neiy] = true;
					qx.add(neix);
					qy.add(neiy);
					qvert.add(dirAdd);
				}
			}
		}
		void processSame(int x, int y, int neix, int neiy,
						boolean dirCheck, boolean dirAdd) {
			if (validNei(neix, neiy) && dirCheck) {
				if (!viz[neix][neiy] || map[neix][neiy] > map[x][y]) {
					map[neix][neiy] = map[x][y];
					viz[neix][neiy] = true;
					qx.add(neix);
					qy.add(neiy);
					qvert.add(dirAdd);
				}
			}
		}

		void calculatePaths() {

			viz[xi][yi] = true;
			if (validNei(xi + 1, yi)) {
				map[xi + 1][yi] = 0;
				viz[xi + 1][yi] = true;
				qx.add(xi + 1);
				qy.add(yi);
				qvert.add(true);
			}
			if (validNei(xi - 1, yi)) {
				map[xi - 1][yi] = 0;
				viz[xi - 1][yi] = true;
				qx.add(xi - 1);
				qy.add(yi);
				qvert.add(true);
			}
			if (validNei(xi, yi + 1)) {
				map[xi][yi + 1] = 0;
				viz[xi][yi + 1] = true;
				qx.add(xi);
				qy.add(yi + 1);
				qvert.add(false);
			}
			if (validNei(xi, yi - 1)) {
				map[xi][yi - 1] = 0;
				viz[xi][yi - 1] = true;
				qx.add(xi);
				qy.add(yi - 1);
				qvert.add(false);
			}

			while (!qx.isEmpty()) {
				int x = qx.poll();
				int y = qy.poll();
				boolean vert = qvert.poll();

				// directie verticala, vecin orizontala
				processDif(x, y, x, y + 1, vert, false);
				processDif(x, y, x, y - 1, vert, false);

				// directie orizontala, vecin verticala
				processDif(x, y, x + 1, y, !vert, true);
				processDif(x, y, x - 1, y, !vert, true);

				// vecin pe aceasi directie
				processSame(x, y, x, y + 1, !vert, false);
				processSame(x, y, x, y - 1, !vert, false);
				processSame(x, y, x + 1, y, vert, true);
				processSame(x, y, x - 1, y, vert, true);
			}
		}

		void solve() {
			readInput();
			calculatePaths();
			try {
				fout.createNewFile();
				FileWriter wr = new FileWriter("beamdrone.out");
				wr.write(Integer.toString(map[xf][yf]));
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
