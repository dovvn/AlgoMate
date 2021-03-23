package baekjoon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_14502 {
	static int N, M, answer; // 연구실 크기, 안전지역 수  최댓값
	static int[][] lab; // 연구실
	static List<Integer> wall; // 벽 세울 수 있는 위치
	static int[] pick; // 벽 세울 곳 지정
	static int[] di = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dj = { 0, 0, -1, 1 }; // 상하좌우

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 연구실 세로
		M = Integer.parseInt(st.nextToken()); // 연구실 가로
		lab = new int[N][M]; // 연구실 초기화
		wall = new ArrayList<Integer>(); // 벽 세울 수 있는 곳
		pick = new int[3]; // 벽 세울 3곳 선택
		answer = Integer.MIN_VALUE; // 최대값을 구해야하니까 최소값로 지정
		// 연구실 상태 저장
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				lab[i][j] = Integer.parseInt(st.nextToken());
				if (lab[i][j] == 0) { // 벽 세울 수 있음
					wall.add(i * M + j);
				}
			}
		}
		go(0, 0); // 벽 세울 곳 정하면서 시뮬 시작
		System.out.println(answer);

	}
	// 안전 지역 수 세기
	public static int areaCnt(int[][] lab) {
		Queue<Point> q = new LinkedList<Point>(); // 바이러스 위치
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (lab[i][j] == 2) { //바이러스이면 현재 위치 저장
					q.add(new Point(i, j));
				}
			}
		}
		while (!q.isEmpty()) { //바이러스가 더이상 퍼지지 않을 때까지
			Point now = q.poll();
			for (int d = 0; d < 4; d++) {
				// 바이러스가 다음으로 퍼질 위치
				int ni = now.i + di[d];
				int nj = now.j + dj[d];

				if (ni >= 0 && ni < N && nj >= 0 && nj < M && lab[ni][nj] == 0) { //퍼질 수 있는 요건이 맞으면
					lab[ni][nj] = 2; //퍼지기
					q.add(new Point(ni, nj)); //또 퍼질 수 있으니까 큐에 저장
				}
			}
		}
		int cnt = 0; // 안전지역 수
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (lab[i][j] == 0) // 안전지역 이면 카운트 증가
					cnt++;
			}
		}
		return cnt;
	}
	// 3개의 벽을 세울 수 있는 경우의 수 다 탐색
	public static void go(int cur, int cnt) {
		if (cnt == 3) {// 벽 세울 곳 다 정함
			int[][] new_lab = new int[N][M]; //바이러스 시뮬 돌릴 연구실 새로 만들기 - 원본 안건드리려고
			for (int ki = 0; ki < N; ki++) {
				for (int kj = 0; kj < M; kj++) {
					new_lab[ki][kj] = lab[ki][kj];
				}
			}
			// 지정된 곳에 벽 세우기
			for (int i = 0; i < 3; i++) {
				int wall_i = pick[i] / M; // 벽 세울 i
				int wall_j = pick[i] % M; // 벽 세울 j

				new_lab[wall_i][wall_j] = 1;
			}
			int aCnt = areaCnt(new_lab); // 안전지역 수 구하기
			answer = Math.max(answer, aCnt); // 안전지역 수 최대값 구하기 
			return;
		}
		// 중복x 조합으로 벽 세울 곳 선택 
		for (int i = cur; i < wall.size(); i++) {
			pick[cnt] = wall.get(i);
			go(i + 1, cnt + 1);
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