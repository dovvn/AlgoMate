package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_14500 {
	static int N, M, answer;
	static int[][] paper;
	static int[] di = { 0, 1, 0, -1}, dj = { 1, 0, -1, 0}; // 우하좌상
	static boolean[][] visit;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 세로 크기
		M = Integer.parseInt(st.nextToken()); // 가로 크기
		answer = Integer.MIN_VALUE;
		paper = new int[N][M];
		visit = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				paper[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ---------------입력 끝
		
		// 방향키(ㅗ) 모양빼고 다 탐색함
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				visit[i][j] = true;
				go(i,j,0,0);
				visit[i][j] = false;
			}
		}
		// 방향키 모양 탐색해야함
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				int sum = paper[i][j];
				int count = 0;
				int min = Integer.MAX_VALUE;
				for(int d=0;d<4;d++) {
					int ni = i+di[d];
					int nj = j+dj[d];
					
					if(ni < 0 || ni >= N || nj < 0 || nj >= M) continue;
					min = Math.min(min, paper[ni][nj]);
					sum += paper[ni][nj];
					count++;
				}
				if(count < 3) continue; // ㅗ 모양 안만들어짐
				else if(count > 3) {// ㅗ 모양 많이 만들어짐
					sum -= min;
				}
				answer = Math.max(answer, sum);
			}
		}
		System.out.println(answer);

	}
	
	static void go(int i, int j, int cnt, int sum) {
		if(cnt+1 == 4) {
			answer = Math.max(answer, sum+paper[i][j]);
			return ;
		}
		for(int d=0;d<3;d++) {
			int ni = i+di[d];
			int nj = j+dj[d];
			if(ni < 0 || ni >= N || nj < 0 || nj >= M || visit[ni][nj]) continue;
			visit[ni][nj] = true;
			go(ni,nj,cnt+1,sum+paper[i][j]);
			visit[ni][nj] = false;
		}
		
	}

}