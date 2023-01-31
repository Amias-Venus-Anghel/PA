import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Fortificatii {
	static class Task {
		/* date ce tin de problema */
		File fin = new File("fortificatii.in");
		File fout = new File("fortificatii.out");

		int n, m, k;

		static final int INF = (int) 1e9;
		ArrayList<Pair>[] adj;

		ArrayList<Integer> barbs = new ArrayList<>();

		public class DijkstraResult {
			List<Integer> d;
			List<Integer> p;

			DijkstraResult(List<Integer> _d, List<Integer> _p) {
				d = _d;
				p = _p;
			}
		}

		public class Pair implements Comparable<Pair> {
			public int destination;
			public int cost;

			Pair(int _destination, int _cost) {
				destination = _destination;
				cost = _cost;
			}

			public int compareTo(Pair rhs) {
				return Integer.compare(cost, rhs.cost);
			}
		}

		void readInput()  {
			try {
				Scanner sc = new Scanner(fin);
				n = sc.nextInt();
				m = sc.nextInt();
				k = sc.nextInt();
				adj = new ArrayList[n + 1];
				int b = sc.nextInt();
				for (int i = 0; i < b; i++) {
					barbs.add(sc.nextInt());
				}
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, t;
					x = sc.nextInt();
					y = sc.nextInt();
					t = sc.nextInt();
					adj[x].add(new Pair(y, t));
					adj[y].add(new Pair(x, t));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//lab 9
		private DijkstraResult dijkstra(int source, int nodes, ArrayList<Pair>[] adj) {
			List<Integer> d = new ArrayList<>();
			List<Integer> p = new ArrayList<>();

			// Initializam distantele la infinit
			for (int node = 0; node <= nodes; node++) {
				d.add(INF);
				p.add(0);
			}

			// Folosim un priority queue de Pair, desi elementele noastre nu sunt tocmai
			// muchii.
			// Vom folosi field-ul de cost ca si distanta pana la nodul respectiv.
			// Observati ca am modificat clasa Pair ca sa implementeze interfata Comparable.
			PriorityQueue<Pair> pq = new PriorityQueue<>();

			// Inseram nodul de plecare in pq si ii setam distanta la 0.
			d.set(source, 0);
			pq.add(new Pair(source, 0));

			// Cat timp inca avem noduri de procesat
			while (!pq.isEmpty()) {
				// Scoatem head-ul cozii
				int cost = pq.peek().cost;
				int node = pq.poll().destination;

				// In cazul in care un nod e introdus in coada cu mai multe distante (pentru ca
				// distanta pana la el se imbunatateste in timp), vrem sa procesam doar
				// versiunea sa cu distanta minima. Astfel, dam discard la intrarile din coada
				// care nu au distanta optima.
				if (cost > d.get(node)) {
					continue;
				}

				// Ii parcurgem toti vecinii.
				for (var e : adj[node]) {
					int neigh = e.destination;
					int w = e.cost;

					// Se imbunatateste distanta?
					if (d.get(node) + w < d.get(neigh)) {
						// Actualizam distanta si parintele.
						d.set(neigh, d.get(node) + w);
						p.set(neigh, node);
						pq.add(new Pair(neigh, d.get(neigh)));
					}
				}
			}

			// Toate nodurile ce au distanta INF nu pot fi atinse din sursa, asa ca setam
			// distantele
			// pe -1.
			for (int i = 1; i <= n; i++) {
				if (d.get(i) == INF) {
					d.set(i, -1);
				}
			}

			return new DijkstraResult(d, p);
		}

		int getResult() {
			DijkstraResult djrez;
			int k_left = k;
			while (k_left > 0) {
				djrez = dijkstra(1, n, adj);
				int st_barb = INF;
				int nd_dist = INF, st_dist = INF;
				// get barbarian base closes to capital
				for (int b : barbs) {
					if (djrez.d.get(b) < 0) {
						continue;
					}
					if (djrez.d.get(b) < st_dist) {
						nd_dist = st_dist;
						st_barb = b;
						st_dist = djrez.d.get(st_barb);
					} else if (djrez.d.get(b) <= nd_dist && djrez.d.get(b) >= st_dist
							&& b != st_barb) {
						nd_dist = djrez.d.get(b);
					}
				}

				int parent = djrez.p.get(st_barb);
				if (parent == 0) {
					break;
				}

				for (Pair p : adj[parent]) {
					if (p.destination == st_barb) {
						if (nd_dist == st_dist) {
							p.cost++;
							k_left--;
						} else if (k_left >= nd_dist - st_dist) {
							p.cost += nd_dist - st_dist;
							k_left -= nd_dist - st_dist;
						} else {
							p.cost += k_left;
							k_left = 0;
						}
						break;
					}
				}
			}

			djrez = dijkstra(1, n, adj);

			int dist = INF;
			// get barbarian base closes to capital
			for (int b : barbs) {
				if (djrez.d.get(b) < dist) {
					dist = djrez.d.get(b);
				}
			}

			return  dist;
		}

		void solve() {
			readInput();

			try {
				fout.createNewFile();
				FileWriter wr = new FileWriter("fortificatii.out");
				wr.write(Integer.toString(getResult()));
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
