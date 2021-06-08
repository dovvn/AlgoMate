package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_17406 {
	static int N, M, K, answer;
	static int[] array;
	static boolean[] visit;
	static int[][] order, map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		array = new int[K]; // 돌릴 순서 정할 배열
		visit = new boolean[K]; // 순열 방문 체크
		order = new int[K][3]; // 돌리는 명령
		map = new int[N][M]; // 초기 맵
		answer = Integer.MAX_VALUE; // 맵 최솟값

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				order[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ================입력 끝=======================
		perm(0);
		System.out.println(answer);
	}

	public static int[][] copyMap(int[][] map) { // 배열 copy
		int[][] newMap = new int[map.length][map[0].length];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		return newMap;
	}

	public static void perm(int cnt) { // 순열
		if (cnt == K) {
			int[][] newMap = copyMap(map);

			for (int i = 0; i < K; i++) {
				rotate(order[array[i]][0], order[array[i]][1], order[array[i]][2], newMap); // 순서대로 돌리기
			}
			// 행 더한 값 중 최솟값 찾기
			int value = Integer.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				int sum = 0;
				for (int j = 0; j < M; j++) {
					sum += newMap[i][j];
				}
				value = Math.min(value, sum);
			}
			answer = Math.min(answer, value); // 맵 최솟값 찾기
			return;
		}

		for (int i = 0; i < K; i++) {
			if (!visit[i]) {
				visit[i] = true;
				array[cnt] = i;
				perm(cnt + 1);
				visit[i] = false;
			}
		}
	}

	public static void rotate(int r, int c, int s, int[][] map) { // 시계방향 한칸씩 회전
		int[][] tmp = new int[2 * s + 1][2 * s + 1]; // 회전할 부분 저장할 배열
		
		// 주어진 부분 저장
		for (int i = r - s - 1; i < r + s; i++) {
			for (int j = c - s - 1; j < c + s; j++) {
				tmp[i - (r - s - 1)][j - (c - s - 1)] = map[i][j];
			}
		}
		
		int[] di = { 0, 1, 0, -1 }, dj = { 1, 0, -1, 0 }; // 우하좌상

		for (int i = 0; i < (2 * s + 1) / 2; i++) { // 2차원배열 바깥부터 안쪽으로 회전
			int d = 0; // 현재 방향
			int size = (2 * (s - i)); // 현재 방향으로 이동할 횟수
			int cnt = 0; // 이동한 횟수
			int[] p = { i, i }; // 현재 위치
			int num1 = tmp[p[0]][p[1]]; // 이전 값
			while (true) {
				int num2 = tmp[p[0] + di[d]][p[1] + dj[d]]; // 다음 값
				tmp[p[0] + di[d]][p[1] + dj[d]] = num1; // 다음 값에 이전 값 넣기
				num1 = num2; // 이전 값 갱신
				
				//위치 이동
				p[0] += di[d];
				p[1] += dj[d];
				cnt++; // 이동 횟수 증가

				if (size == cnt) { // 모두 이동했으면 방향 전환
					d = (d + 1) % 4;
					if (d == 0) break; // 한바퀴 다 돌음
					cnt = 0; // 이동 횟수 초기화
				}
			}
		}

		for (int i = r - s - 1; i < r + s; i++) { // map에 회전한 tmp 넣어주기
			for (int j = c - s - 1; j < c + s; j++) {
				map[i][j] = tmp[i - (r - s - 1)][j - (c - s - 1)];
			}
		}
	}

	public static void printArray(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

}
