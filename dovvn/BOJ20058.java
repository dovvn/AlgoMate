package 삼성기출;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ20058 {

	static int N, Q;
	static int[][] map, tmp;
	static int[][] dInfo = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static Queue<Point> queue;
	static boolean[][] visited;
	static boolean[][] checked;
	static int size;
	static int ans_sum;
	static int ans_max;

	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		size = (int) Math.pow(2, N); // 한변의 길이
		map = new int[size][size];

		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < size; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		} // End input

		st = new StringTokenizer(br.readLine());

		for (int q = 0; q < Q; q++) {
			int l = Integer.parseInt(st.nextToken()); // 분할 크기
			tmp = new int[size][size];

			// 1. 90도 회전
			for (int i = 0; i < size; i += (int) Math.pow(2, l)) {
				for (int j = 0; j < size; j += (int) Math.pow(2, l)) {
					rotate(i, j, (int) Math.pow(2, l));
				}
			}

			checked = new boolean[size][size];

			// 2. 얼음 줄이기
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					int cnt = 0;
					if (map[i][j] == 0)
						continue;
					for (int d = 0; d < 4; d++) {
						int ni = i + dInfo[d][0];
						int nj = j + dInfo[d][1];

						if (ni >= 0 && ni < size && nj >= 0 && nj < size && map[ni][nj] > 0)
							cnt++;
					}
					if (cnt < 3)
						checked[i][j] = true;
				}
			}

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (checked[i][j])
						map[i][j]--;
				}
			}
		} // End 시전

		// 3. 합 구하기
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] <= 0)
					continue;
				ans_sum += map[i][j];
			}
		}

		// 4. 가장 큰 덩어리 구하기
		queue = new LinkedList<>();
		visited = new boolean[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!visited[i][j] && map[i][j] > 0) {
					queue.add(new Point(i, j));
					visited[i][j] = true;
					bfs();
				}
			}
		}

		bw.write(ans_sum + "\n");
		bw.write(ans_max + "");
		bw.flush();
		bw.close();
		br.close();
	}

	private static void bfs() {
		int cnt = 1;

		while (!queue.isEmpty()) {
			Point p = queue.poll();

			for (int d = 0; d < 4; d++) {
				int ni = p.x + dInfo[d][0];
				int nj = p.y + dInfo[d][1];

				if (ni >= 0 && ni < size && nj >= 0 && nj < size && !visited[ni][nj] && map[ni][nj] > 0) {
					queue.add(new Point(ni, nj));
					visited[ni][nj] = true;
					cnt++;
				}
			}
		}
		ans_max = Math.max(ans_max, cnt);
	}

	private static void rotate(int x, int y, int length) {
		for (int i = 0; i < length; i++) { // 90도 회전
			for (int j = 0; j < length; j++) {
				tmp[j][length - i - 1] = map[x + i][y + j];
			}
		}

		for (int i = 0; i < length; i++) { // 원래 배열에 복사
			for (int j = 0; j < length; j++) {
				map[x + i][y + j] = tmp[i][j];
			}
		}
	}
}
