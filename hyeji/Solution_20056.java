package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_20056 {
	static int N, M, K, answer;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 }, dj = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 격자 크기
		M = Integer.parseInt(st.nextToken()); // 초기 파이어볼 개수
		K = Integer.parseInt(st.nextToken()); // 이동 횟수
		answer = 0;
		Queue<FireBall> q = new LinkedList<FireBall>();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			q.add(new FireBall(r-1, c-1, m, s, d)); // 움직일 파이어볼
		}
		// --------------------- 입력 끝 -------------------------
		List<FireBall>[][] map = new ArrayList[N][N]; // 칸에 파이어볼 뭐있는지 확인
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayList<FireBall>();
			}
		}
		for (int k = 0; k < K; k++) {
			while (!q.isEmpty()) { // 파이어볼 움직이기
				FireBall f = q.poll();
				f.i = (f.i + f.s * di[f.d]) % N;
				f.j = (f.j + f.s * dj[f.d]) % N;
				if (f.i < 0) {
					f.i = N + f.i;
				}
				if (f.j < 0) {
					f.j = N + f.j;
				}
				map[f.i][f.j].add(f);
			}
			// 칸 마다 파이어볼 있느지 없는지 확인
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
//					System.out.println(i+" "+j+" "+map[i][j].size());
					if (map[i][j].size() == 1) {
						q.add(map[i][j].get(0));
					} else if (map[i][j].size() > 1) {
						int m_sum = 0;
						int s_sum = 0;
						int d = -1;
						boolean check = true; // 다 홀수이거나 짝수인지 확인
						for (FireBall b : map[i][j]) {
							m_sum += b.m;
							s_sum += b.s;
							if (d == -1) { // 처음 방향이면
								d = b.d % 2;
							} else if (d != b.d % 2) { // 이전 방향하고 홀짝이 다르면
								check = false;
							}
						}
						int nm = m_sum / 5;
						int ns = s_sum/map[i][j].size();
						if (nm > 0) {
							if(check) d = 0;
							else d = 1;
							for (int m = 0; m < 4; m++) {
								q.add(new FireBall(i,j,nm,ns,d));
								d += 2;
							}
						}
					}
					map[i][j].clear();
				}
			}
		}
		while(!q.isEmpty()) {
			answer += q.poll().m;
		}
		System.out.println(answer);
	}

	static class FireBall {
		int i, j;
		int m, d, s;

		FireBall(int i, int j, int m, int s, int d) {
			this.i = i;
			this.j = j;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}

}
