package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_20057 {
	static int[][] ci = { { -2, -1, -1, -1, 0, 1, 1, 1, 2 }, 
							{ 0, 1, 0, -1, 2, 1, 0, -1, 0 },
							{ 2, 1, 1, 1, 0, -1, -1, -1, -2 }, 
							{ 0, -1, 0, 1, -2, -1, 0, 1, 0 } }; // 방향에 따른 모래 이동 영역
	static int[][] cj = { { 0, -1, 0, 1, -2, -1, 0, 1, 0 }, 
							{ -2, -1, -1, -1, 0, 1, 1, 1, 2 },
							{ 0, 1, 0, -1, 2, 1, 0, -1, 0 }, 
							{ 2, 1, 1, 1, 0, -1, -1, -1, -2 } }; // 방향에 따른 모래 이동 영역
	static int[] rate = { 2, 10, 7, 1, 5, 10, 7, 1, 2 }; // 흩날리는 모래 비율
	static int N, answer;
	static int[][] A;
	static int[] di = { 0, 1, 0, -1 }, dj = { -1, 0, 1, 0 }; // 좌하우상 - 태풍 이동할 때 사용

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		A = new int[N][N];
		answer = 0;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// -----------입력 끝------------------------------
		
		Point now = new Point(N / 2, N / 2); // 중앙에서 시작
		int d = 0; // 태풍 이동 방향
		int t = 0; // 한 방향으로 이동하는 거리가 증가해야 하는지 확인할 변수
		int go = 1; // 한 방향으로 이동할 이동 거리
		answer = 0; // 격자 밖으로 나가는 모래 양

		while (now.i != 0 || now.j != 0) {
			for (int g = 0; g < go; g++) { // 현재 방향(d)으로 몇 칸(g) 이동 
				// 다음 이동할 곳
				int ni = now.i + di[d];
				int nj = now.j + dj[d];
				
				if (ni < 0 || ni >= N || nj < 0 || nj >= N) break;
				
				int remainder = A[ni][nj]; // 흩날리고 남은 모래양 계산

				// 모래 흩날리기
				for (int k = 0; k < 9; k++) {
					// 모래 날아간 영역
					int ki = ni + ci[d][k]; 
					int kj = nj + cj[d][k];
					int sand = A[ni][nj] * rate[k] / 100; // 날아간 모래 양
					
					if (ki < 0 || ki >= N || kj < 0 || kj >= N) { // 격자 밖으로 나갔으면 answer에 더해주기
						answer += sand;
					} else { //격자 안이면 원래 있던 모래양에 더해주기
						A[ki][kj] += sand;
					}
					remainder -= sand; //남아있는 모래양 계산
				}
				// 흩날리고 남은 모래 a위치로 이동
				int nni = ni + di[d];
				int nnj = nj + dj[d];
				if (nni >= 0 && nni < N && nnj >= 0 && nnj < N) { // a위치가 격자 안이면
					A[nni][nnj] += remainder; // 이동
				} else { // a위치가 격자 밖이면
					answer += remainder; // answer에 더해주기
				}
				A[ni][nj] = 0; //모래 다 날림
				//태풍 이동
				now.i = ni; 
				now.j = nj;
			} // 한 방향으로 태풍 모두 이동
			d = (d + 1) % 4; // 방향 전환
			
			t = (t + 1) % 2; // 방향이 두번 전환되면 이동 거리 증가
			if (t == 0) go++;
		}
		System.out.println(answer);
	}

	static class Point {
		int i, j;

		Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

}
