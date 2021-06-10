package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_17779 {
	static int N, answer;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		answer = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ================입력 끝=====================

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int d1 = 1; d1 < N; d1++) {
					for (int d2 = 1; d2 < N; d2++) {
						if (check(i, j, d1, d2)) {
							answer = Math.min(answer, minCnt(i, j, d1, d2));
						}
					}
				}
			}
		}
		System.out.println(answer);
	}

	public static int minCnt(int x, int y, int d1, int d2) {
		int[][] gu = new int[N][N];
		int[] cnt = new int[6];
		for (int d = 0; d <= d1; d++) {
			gu[x + d][y - d] = 5;
			gu[x + d2 + d][y + d2 - d] = 5;
		}
		for (int d = 0; d <= d2; d++) {
			gu[x + d][y + d] = 5;
			gu[x + d1 + d][y - d1 + d] = 5;
		}

		for (int i = 0; i < x + d1; i++) {
			for (int j = 0; j <= y; j++) {
				if (gu[i][j] == 5) break;
				gu[i][j] = 1;
			}
		}
		for (int i = 0; i <= x + d2; i++) {
			for (int j = N - 1; j >= y + 1; j--) {
				if (gu[i][j] == 5) break;
				gu[i][j] = 2;
			}
		}
		for (int i = x + d1; i < N; i++) {
			for (int j = 0; j < y - d1 + d2; j++) {
				if (gu[i][j] == 5) break;
				gu[i][j] = 3;
			}
		}
		for (int i = x + d2 + 1; i < N; i++) {
			for (int j = N - 1; j >= y - d1 + d2; j--) {
				if (gu[i][j] == 5) break;
				gu[i][j] = 4;
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (gu[i][j] == 0) cnt[5] += map[i][j];
				else cnt[gu[i][j]] += map[i][j];
			}
		}
		Arrays.sort(cnt);
		return cnt[5] - cnt[1];
	}

	public static boolean check(int x, int y, int d1, int d2) {
		if (x < x + d1 + d2 && x + d1 + d2 < N && y - d1 >= 0 && y - d1 < y && y < y + d2 && y + d2 < N) {
			return true;
		}
		return false;
	}

}
