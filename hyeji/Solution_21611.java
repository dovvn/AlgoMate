package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_21611 {
	static int N, M;
	static int[] boom_cnt;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		boom_cnt = new int[4];
		int[] di = { 0, -1, 1, 0, 0 }, dj = { 0, 0, 0, -1, 1 }; // 상하좌우
		int[][] map = new int[N][N];
		int[][] breaking = new int[M][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++) {
				breaking[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ==========================입력 끝============================
		
		for (int i = 0; i < M; i++) {
			// 1. 구슬 파괴
			int ni = N / 2;
			int nj = N / 2;
			for (int m = 0; m < breaking[i][1]; m++) {
				ni = ni + di[breaking[i][0]];
				nj = nj + dj[breaking[i][0]];
				map[ni][nj] = 0;
			}
			int[] mapArray = mapToArray(map);
			mapArray = move(mapArray);
			// 2. 구슬 폭발
			boolean check = true;
			while (check) {
				check = boom(mapArray);
				mapArray = move(mapArray);
			}
			// 3. 구슬 변화
			mapArray = change(mapArray);
			map = arrayToMap(mapArray);
		}
		
		System.out.println(boom_cnt[1] + 2 * boom_cnt[2] + 3 * boom_cnt[3]);
	}

	public static int[] mapToArray(int[][] map) {
		int map_i = N / 2, map_j = N / 2;
		int[] di = { 0, 1, 0, -1 }, dj = { -1, 0, 1, 0 };
		int map_size = 1, map_d = 0, map_toggle = 0, index = 1;
		int[] array = new int[N * N];
		
		while (map_i >= 0 && map_j >= 0) {
			for (int i = 0; i < map_size; i++) {
				map_i += di[map_d];
				map_j += dj[map_d];
				if (map_i < 0 || map_j < 0) break;

				array[index] = map[map_i][map_j];
				index++;
			}
			map_toggle++;
			if (map_toggle % 2 == 0) map_size++;
			map_d = (map_d + 1) % 4;
		}
		return array;
	}

	public static int[][] arrayToMap(int[] array) {
		int[][] map = new int[N][N];
		int map_i = N / 2, map_j = N / 2;
		int[] di = { 0, 1, 0, -1 }, dj = { -1, 0, 1, 0 };
		int map_size = 1, map_d = 0, map_toggle = 0, index = 1;
		
		while (map_i >= 0 && map_j >= 0) {
			for (int i = 0; i < map_size; i++) {
				map_i += di[map_d];
				map_j += dj[map_d];
				if (map_i < 0 || map_j < 0) break;

				map[map_i][map_j] = array[index];
				index++;
			}
			map_toggle++;
			if (map_toggle % 2 == 0) map_size++;
			map_d = (map_d + 1) % 4;
		}
		return map;
	}

	public static int[] change(int[] array) {
		int[] newArray = new int[N * N];
		int num = array[1], num_size = 0, index = 1;
		for (int i = 1; i < N * N; i++) {
			if (N * N - index < 2) break;
			if (array[i] == num) {
				num_size++;
			} else {
				newArray[index++] = num_size;
				newArray[index++] = num;
				num_size = 1;
				num = array[i];
			}
		}
		return newArray;
	}

	public static boolean boom(int[] array) {
		boolean result = false;
		int startIdx = 1, endIdx = 1, num_size = 0, num = array[startIdx];

		for (int i = 0; i < N * N; i++) {
			if (array[i] == num) {
				num_size++;
				endIdx = i;
			} else {
				if (num_size >= 4) {
					result = true;
					boom_cnt[num] += num_size;
					for (int j = startIdx; j <= endIdx; j++) {
						array[j] = 0;
					}
				}
				num_size = 1;
				num = array[i];
				startIdx = i;
				endIdx = i;
			}
		}
		if (num_size >= 4) {
			for (int j = startIdx; j <= endIdx; j++) {
				array[j] = 0;
			}
		}
		return result;
	}

	public static int[] move(int[] array) {
		int[] result = new int[N * N];
		int index = 1;
		for (int i = 1; i < N * N; i++) {
			if (array[i] != 0) result[index++] = array[i];
		}
		return result;
	}
}
