package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_19238 {
	static int N, M, K;
	static int[][] map;
	static int[] di = { -1, 1, 0, 0 }, dj = { 0, 0, -1, 1 }; // 상하좌우
	static int[] taxi, person;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 맵 크기
		M = Integer.parseInt(st.nextToken()); // 인원 수
		K = Integer.parseInt(st.nextToken()); // 연료량
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		taxi = new int[2];
		person = new int[N * N];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 2; i++) {
			taxi[i] = Integer.parseInt(st.nextToken()) - 1;
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start_i = Integer.parseInt(st.nextToken()) - 1;
			int start_j = Integer.parseInt(st.nextToken()) - 1;
			int end_i = Integer.parseInt(st.nextToken()) - 1;
			int end_j = Integer.parseInt(st.nextToken()) - 1;

			map[start_i][start_j] = -1; // 사람있음
			person[start_i * N + start_j] = end_i * N + end_j; // person[시작위치] = 끝위치
		}
		// -------------------------입력 끝------------------------
		for(int i=0;i<M;i++) {
			if(!go()) {
				System.out.println(-1);
				return;
			}
		}
		System.out.println(K);
	}

	static boolean go() { 
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] visit = new boolean[N][N];
		q.add(taxi);
		visit[taxi[0]][taxi[1]] = true;
		List<Integer> p = new ArrayList<Integer>(); // 같은 거리에 있는 사람들 저장
		int dist = 0; //움직이는 거리
		
		// 현재 택시 위치에서 가장 가까운 사람 찾기
		if(map[taxi[0]][taxi[1]] < 0) { // 현재 위치에 사람있음
			p.add(taxi[0]*N+taxi[1]);
		}else {
			while (!q.isEmpty()) {
				int size = q.size();
				for (int s = 0; s < size; s++) {
					int[] now = q.poll();
					
					for (int d = 0; d < 4; d++) {
						int ni = now[0] + di[d];
						int nj = now[1] + dj[d];
						if (ni >= 0 && ni < N && nj >= 0 && nj < N && map[ni][nj] != 1 && !visit[ni][nj]) { // 범위 안에 있고 벽 아님
							if (map[ni][nj] < 0) { // 사람있음
								p.add(ni * N + nj);
							}else{
								q.add(new int[] { ni, nj });
							}
							visit[ni][nj] = true;
						}
					}
				}
				dist++;
				if (p.size() > 0) break;
			}
		}
		if(K < dist || p.size() <= 0) return false; // 이동 거리가 현재 연료보다 크거나 사람 못찾으면 실패
		
		// 가장 가까운 사람 찾음
		Collections.sort(p); // 정렬해서 다음에 태울 사람 찾기
		int pick = p.get(0); // 행, 열이 제일 빠른 사람
		taxi[0] = pick / N; // 택시 이동
		taxi[1] = pick % N;
		K -= dist; // 연료 소모
		
		map[taxi[0]][taxi[1]] = 0; // 사람 태웠으니까 없애기
		
		// 도착지까지 이동
		visit = new boolean[N][N];
		q.clear();
		q.add(taxi);
		visit[taxi[0]][taxi[1]] = true;
		boolean end = false;
		dist = 0;
		
		if(taxi[0]*N+taxi[1] == person[pick]) { // 현재 위치가 도착지임
			end = true;
		}else {
			while (!q.isEmpty()) {
				int size = q.size();
				for (int s = 0; s < size; s++) {
					int[] now = q.poll();
					
					for (int d = 0; d < 4; d++) {
						int ni = now[0] + di[d];
						int nj = now[1] + dj[d];

						if (ni >= 0 && ni < N && nj >= 0 && nj < N && map[ni][nj] != 1 && !visit[ni][nj]) { // 범위 안에 있고 벽 아님
							if (ni * N + nj == person[pick]) { // 도착지에 도착
								end = true;
								break;
							}else {
								q.add(new int[] { ni, nj });
								visit[ni][nj] = true;
							}
						}
					}
					if (end) break;
				}
				dist++;
				if (end) break;
			}
		}
		if(K < dist || !end) return false; // 이동 거리가 현재 연료량보다 크거나 도착 못하면 실패
		
		taxi[0] = person[pick] / N; // 택시 이동
		taxi[1] = person[pick] % N;
		K -= dist; //연료 소모
		
		K += dist*2; // 도착지에 도착했으니까 2배 더하기
		return true;
	}
}
