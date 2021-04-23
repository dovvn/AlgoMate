package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_19237 {
	static int N, M, k, answer;
	static int[][] map, shark, time, smell;
	static int[][][] priority;
	static int[] di = { 0, -1, 1, 0, 0 }, dj = { 0, 0, 0, -1, 1 }; // 0상하좌우

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 맵 크기
		M = Integer.parseInt(st.nextToken()); // 상어 마리 수
		k = Integer.parseInt(st.nextToken()); // 냄새 사라지는 시간
		answer = 0;
		
		map = new int[N][N];
		shark = new int[M + 1][4]; // [상어 번호][], [][0], [][1] - 상어 위치, [][2] - 상어 방향
		priority = new int[M + 1][5][4]; // [상어 번호][][] , [][n][] - 현재 상어 방향, [][][n] - 다음 방향의 우선순위
		time = new int[N][N];
		smell = new int[N][N];

		for (int i = 0; i < N; i++) { // 상어 정보 저장
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] != 0) {
					shark[map[i][j]][0] = i;
					shark[map[i][j]][1] = j;
					shark[map[i][j]][3] = 1;
				}
			}
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= M; i++) {
			shark[i][2] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i <= M; i++) {
			for (int j = 1; j <= 4; j++) {
				st = new StringTokenizer(br.readLine());
				for (int k = 0; k < 4; k++) {
					priority[i][j][k] = Integer.parseInt(st.nextToken());
				}
			}
		}
		
		// ---------------------- 입력 끝 ------------------------------
		
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i=1;i<=M;i++) {
			q.add(i);
			smell[shark[i][0]][shark[i][1]] = i;
			time[shark[i][0]][shark[i][1]] = k;
		}
		
		while(!q.isEmpty()) {
			//남아있는 상어 이동
			int size = q.size();
			for(int i=0;i<size;i++) { // 모든 상어 동시에 이동
				int s = q.poll(); //상어
				// 상어 위치와 바라보는 방향
				int si = shark[s][0];
				int sj = shark[s][1];
				int sd = shark[s][2];
				
				// 냄새없는 칸 못찾으면 자기 냄새있는 칸으로 갈거
				int[] temp = new int[3];
				boolean go = false, my = false;
				for(int d=0;d<4;d++) { //다음 갈 방향 정하기
					int ni = si + di[priority[s][sd][d]];
					int nj = sj + dj[priority[s][sd][d]];
					
					if(ni >= 0 && ni < N && nj >= 0 && nj < N) { // 범위 안
						if(smell[ni][nj] == 0) { //아무 냄새도 없는 칸이면 무조건 가
							shark[s][0] = ni;
							shark[s][1] = nj;
							shark[s][2] = priority[s][sd][d];
							go = true;
							break;
						}else if(!my && smell[ni][nj] == s) {// 내 냄새 있는 곳이면 일단 기억해두기(우선적으로 갈 곳)
							temp[0] = ni;
							temp[1] = nj;
							temp[2] = priority[s][sd][d];
							my = true;
						}
					}
				}
				if(!go) { // 빈칸 못찾으면 내 냄새있는 곳으로 이동
					for(int j=0;j<3;j++) {
						shark[s][j] = temp[j];
					}
				}
				map[si][sj] = 0;
			}//for size
			countDown();
			for(int i=1;i<=M;i++) {
				if(shark[i][3] == 0) continue;
				if(map[shark[i][0]][shark[i][1]] == 0 || map[shark[i][0]][shark[i][1]] == i) {
					update(shark[i][0], shark[i][1], i);
				}else if(map[shark[i][0]][shark[i][1]] > i){
					int remove = map[shark[i][0]][shark[i][1]];
					System.out.println(remove);
					update(shark[i][0], shark[i][1], i);
					shark[remove][3] = 0;	
				}else {
					shark[i][3] = 0;
				}
			}
			for(int i=1;i<=M;i++) {
				if(shark[i][3] != 0) q.add(i);
			}
			answer++;
			if(answer > 1000 || q.size() <= 1) break;
		}//while
		if(answer > 1000) System.out.println(-1);
		else System.out.println(answer);
		

	}
	static void update(int i, int j, int s) {
		map[i][j] = s;
		smell[i][j] = s;
		time[i][j] = k;
	}
	static void countDown() { // 냄새 카운트 감소
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(time[i][j] > 0) time[i][j]--;
				if(time[i][j] == 0) {
					smell[i][j] = 0;
				}
			}
		}
	}
}
