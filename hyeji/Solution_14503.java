package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_14503 {
	static int N, M, answer;
	static int[][] room;
	static int[] robot, di = { -1, 0, 1, 0 }, dj = { 0, 1, 0, -1 }; // 북동남서, 상우하좌

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		answer = 0;
		st = new StringTokenizer(br.readLine());
		robot = new int[3];

		for (int i = 0; i < 3; i++) {
			robot[i] = Integer.parseInt(st.nextToken());
		}
		room = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				room[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// -------------입력 끝----------------
		go1();
		System.out.println(answer);

	}

	static void go1() {
		if (room[robot[0]][robot[1]] == 0) { // 현재 위치가 청소 안되어있으면 청소하자
			room[robot[0]][robot[1]] = 2;
			answer++;
			go2();
		}
	}
	static void go2() {
		// 청소 후 내 왼쪽 확인
		int d = (robot[2] + 3) % 4;
		int ni = robot[0] + di[d];
		int nj = robot[1] + dj[d];

		if (ni >= 0 && ni < N && nj >= 0 && nj < M && room[ni][nj] == 0) { // 내 왼쪽이 청소가 안되어있으면
			robot[0] = ni;
			robot[1] = nj;
			robot[2] = d;
			go1(); // 청소기 회전 + 이동 후 1번 반복
		} else if (room[ni][nj] != 0) {// 내 왼쪽이 청소가 되어있거나 벽이면
			//나머지 청소할 구역 있는지 확인
			for(int i=0;i<3;i++) {
				d = (d+3)%4; 
				ni = robot[0]+di[d];
				nj = robot[1]+dj[d]; //계속 회전하면서 찾기
				
				if(room[ni][nj] == 0) { //청소할 수 있는 곳 찾음 -> 1번으로
					robot[0] = ni;
					robot[1] = nj;
					robot[2] = d;
					go1();
					return ;
				}
			}
			
			// 4방향 모두 청소할 곳 없음, 후진
			int b = (robot[2] + 2) % 4; //후진 방향
			if(room[robot[0]+di[b]][robot[1]+dj[b]] != 1) { //후진 가능하면
				robot[0] += di[b];
				robot[1] += dj[b];
				go2(); //후진하고 2번으로
			}else { //로봇 멈춤
				return ;
			}
		}
	}

}