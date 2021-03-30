package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_20058 {
	static int N, Q, ansSum, ansSize;
	static int[][] A;
	static int[] L, di = { -1, 1, 0, 0 }, dj = { 0, 0, -1, 1 }; // 상하좌우

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		int n = (int) Math.pow(2, N);
		A = new int[n][n];
		L = new int[Q];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < Q; i++) {
			L[i] = Integer.parseInt(st.nextToken());
		}
		// ------------------------입력 끝-------------------------------
		for (int q = 0; q < Q; q++) {
			int size = (int) Math.pow(2, L[q]);
			for (int i = 0; i < n; i += size) {
				for (int j = 0; j < n; j += size) {
					rotate(i, j, size);
				}
			}
			// 얼음 깨기
			storm(n);
		}

		ansSum = sum(n);
		ansSize = countArea(n);
		System.out.println(ansSum);
		System.out.println(ansSize);

	}

	static int countArea(int size) {
		// bfs로 덩어리 갯수 최대 구하기
		boolean[][] visit = new boolean[size][size];
		int maxCnt = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!visit[i][j] && A[i][j] != 0) {
					Queue<Point> q = new LinkedList<Point>();
					q.add(new Point(i, j));
					visit[i][j] = true;
					int cnt = 0;

					while (!q.isEmpty()) {
						Point p = q.poll();

						for (int d = 0; d < 4; d++) {
							int ni = p.i + di[d];
							int nj = p.j + dj[d];

							if (ni >= 0 && ni < size && nj >= 0 && nj < size && A[ni][nj] != 0 && !visit[ni][nj]) {
								q.add(new Point(ni, nj));
								visit[ni][nj] = true;
							}
						}
						cnt++;
					}
					maxCnt = Math.max(maxCnt, cnt);
				}
			}
		}
		return maxCnt;
	}

	static int sum(int size) {
		int sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sum += A[i][j];
			}
		}
		return sum;
	}

	static void storm(int size) {
		boolean[][] check = new boolean[size][size]; // 얼을 깰 장소인지 아닌지
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// 얼음있는지 확인
				if (A[i][j] == 0)
					continue;
				// 내 상하좌우에 얼음 몇개있는지 확인
				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int ni = i + di[d];
					int nj = j + dj[d];

					if (ni >= 0 && ni < size && nj >= 0 && nj < size && A[ni][nj] != 0)
						cnt++;
				}
				if (cnt < 3) {
					check[i][j] = true;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (check[i][j])
					A[i][j]--;
			}
		}

	}

	static void rotate(int start_i, int start_j, int size) {
		int[][] temp = new int[size][size];
		// 현재 부분 저장
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				temp[i][j] = A[start_i + i][start_j + j];
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				A[start_i + j][start_j + size - 1 - i] = temp[i][j];
			}
		}
	}

	static void printArray(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}

	static class Point {
		int i, j;

		Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
}
