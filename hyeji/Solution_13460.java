package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_13460 {
	static int N, M, answer;
	static char board[][];
	static boolean visit[][][][];
	static Point red, blue, goal;
	static int[] di = {0,1,0,-1}; //우하좌상
	static int[] dj = {1,0,-1,0}; //우하좌상
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 보드의 세로 길이
		M = Integer.parseInt(st.nextToken()); // 보드의 가로 길이
		board = new char[N][M];
		visit = new boolean[N][M][N][M];
		answer = 0;
		// 보드 상태 저장, 빨간 구슬, 파란 구슬, 구멍 위치 저장
		for(int i=0;i<N;i++) {
			String temp = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = temp.charAt(j);
				if(board[i][j] == 'R') red = new Point(i,j);
				else if(board[i][j] == 'B') blue = new Point(i,j);
				else if(board[i][j] == 'O') goal = new Point(i,j);
			}
		}
		bfs();
		System.out.println(answer);
		
	}
	static void bfs() {
		Queue<Point> queue = new LinkedList<Point>();
		
		queue.add(new Point(red.i, red.j));
		queue.add(new Point(blue.i, blue.j));
		visit[red.i][red.j][blue.i][blue.j] = true;
		
		boolean r_check = false; // 빨간 구슬 탈출 여부
		// 방향 움직이기
		for(int i=0;i<10;i++) { //최대 10번
			if(!queue.isEmpty()) {
				int size = queue.size();
				for(int j=0;j<size/2;j++) { //한번 이동할 경우의 모든 포인트 다 빼내기
					Point r = queue.poll();
					Point b = queue.poll();
					for(int d=0;d<4;d++) {
						Point nr = new Point(r.i, r.j);
						Point nb = new Point(b.i, b.j);
						
						int bm = move(nb,d); //파란 구슬 먼저 이동
						if(board[nb.i][nb.j] == 'O') { //파란 구슬이 구멍에 빠지면 경우 제외
							continue;
						}
						int rm = move(nr,d);//빨간 구슬 이동
						if(board[nr.i][nr.j] == 'O') { //빨간 구슬 구멍에 빠지면 성공, 반복문 나가기
							r_check = true;
							break;
						}
						
						if(nb.i == nr.i && nb.j == nr.j) { //두 구슬이 같은 위치에 존재하면 더 많이 움직인 구슬이 한칸 뒤로 이동
							if(bm > rm) {
								nb.i -= di[d];
								nb.j -= dj[d];
							}else {
								nr.i -= di[d];
								nr.j -= dj[d];
							}
						}
						
						if(!visit[nr.i][nr.j][nb.i][nb.j]) { //현재 두 구슬의 위치가 이전에 없던 경우이면 큐에 추가
							queue.add(nr);
							queue.add(nb);
							visit[nr.i][nr.j][nb.i][nb.j] = true;
						}
					} //방향
					if(r_check) break;
				} // 구슬 위치
			} //if
			answer++; //이동 횟수 증가
			if(r_check) break;
		}// 10번
		if(!r_check) answer = -1; //10번의 이동동안 빨간 구슬이 탈출하지 못했으면 -1
	}
	// 벽이나 구멍만나기 전까지 이동
	static int move(Point p, int d) {
		int move_dist = 0;
		while(true) {
			int ni = p.i+di[d];
			int nj = p.j+dj[d];
			
			if(board[ni][nj]=='#') break;
			p.i = ni;
			p.j = nj;
			move_dist++;
			if(board[ni][nj] == 'O') break;
		}
		return move_dist;
	}
	
	static class Point{
		int i, j;
		Point(int i, int j){
			this.i= i;
			this.j = j;
		}
	}

}