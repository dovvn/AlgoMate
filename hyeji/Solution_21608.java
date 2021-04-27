package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_21608 {
	static int[][] answer;
	static boolean[][] students;
	static int N, ans;
	static int[] di = { -1, 1, 0, 0 }, dj = { 0, 0, -1, 1 }, order; // 상하좌우

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		order = new int[N * N]; // 자리배정 순서
		students = new boolean[N * N + 1][N * N + 1]; // [i][]번 학생이 좋아하는 학생 [][j] : true면 좋아함
		answer = new int[N][N];
		for (int i = 0; i < N * N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			order[i] = Integer.parseInt(st.nextToken());
			for (int j = 0; j < 4; j++) {
				students[order[i]][Integer.parseInt(st.nextToken())] = true;
			}
		}
		// ------------------- 입력 끝 --------------------------
		for (int idx = 0; idx < N * N; idx++) {
			int std_id = order[idx]; // 학생 번호
			int maxLike = -1;
			int maxEmpty = -1;
			int[] target = new int[2]; // 최적의 자리
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (answer[i][j] != 0) continue; // (i,j)자리 차있으면 넘어가기

					int cnt_like = 0; // (i,j)자리 주변으로 현재 학생이 좋아하는 학생 몇 명 있는지
					int cnt_empty = 0;// (i,j)자리 주변으로 빈 자리 몇 개인지
					for (int d = 0; d < 4; d++) { // 인접한 자리 탐색
						int ni = i + di[d];
						int nj = j + dj[d];

						if (ni >= 0 && ni < N && nj >= 0 && nj < N) {
							if (answer[ni][nj] == 0) { // 빈자리
								cnt_empty++;
							} else if (students[std_id][answer[ni][nj]]) {
								cnt_like++;
							}
						}
					}

					if (maxLike < cnt_like) {
						maxLike = cnt_like;
						maxEmpty = cnt_empty;
						target[0] = i;
						target[1] = j;
					} else if (maxLike == cnt_like && maxEmpty < cnt_empty) {
						maxEmpty = cnt_empty;
						target[0] = i;
						target[1] = j;
					}
				}
			}
			answer[target[0]][target[1]] = std_id; // 자리 설정
		}
		// 만족도 계산
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int ni = i + di[d];
					int nj = j + dj[d];

					if (ni >= 0 && ni < N && nj >= 0 && nj < N && students[answer[i][j]][answer[ni][nj]]) {
						cnt++;
					}
				}

				if (cnt != 0) { // (0 : 0), (1 : 1), (2: 10), (3: 100), (4 : 1000)
					ans += (int) (Math.pow(10, cnt - 1));
				}
			}
		}
		
		System.out.println(ans);
	}
}
