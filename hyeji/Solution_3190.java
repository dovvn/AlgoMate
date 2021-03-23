package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 뱀이 기어다니는데 사과를 먹으면 뱀 길이가 늘어남
 * 벽 또는 자기 자신의 몸과 부딪히면 게임이 끝남
 * 보드 상하좌우 끝은 벽
 * N*N 정사각 보드 위에서 진행 (0<=N<=100)
 * 사과 (0<=K<=100)
 * 시작할 때 뱀은 맨위맨자측에 위치, 뱀 길이 1, 처음에 오른쪽으로 향함
 * -맵은 몸길이를 늘려 머리를 다음 칸에 위치시킨다
 * -이동한 칸에 사과가 있다면 사과가 없어지고 꼬리는 움지이지 않는다
 * -이동한 칸에 사과가 없다면 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다
 * 사과의 위치와 뱀의 이동경로가 주어질때 몇초에 끝나는지 계산
 * (1,1)에는 사과 x
 * 뱀 방향 변환 횟수 L (1<=L<=100)
 * 게임 시작 시간으로부터 x초가 끝난 뒤에 ('L','D)왼쪽, 오른쪽으로 90도 회전시킨다는 뜻 (x<=10000)
 * */
public class Solution_3190 {
	static int N, K, L, answer;
	static int[][] board, move;
	static List<Point> snake;
	static int[] di = {0,1,0,-1}; //우하좌상
	static int[] dj = {1,0,-1,0}; //우하좌상
	static Point snakeHead, snakeTail;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); //보드 크기
		K = Integer.parseInt(br.readLine()); //사과 개수
		snake = new ArrayList<Point>(); //뱀
		board = new int[N][N];
		answer = 0;
		for(int i=0;i<K;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int ai = Integer.parseInt(st.nextToken());
			int aj = Integer.parseInt(st.nextToken());
			board[ai-1][aj-1] = 1; //보드에 사과표시
		}
		L = Integer.parseInt(br.readLine()); //이동 횟수
		move = new int[L][2];
		for(int i=0;i<L;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			move[i][0] = Integer.parseInt(st.nextToken());
			char temp = st.nextToken().charAt(0);
			move[i][1] = temp=='D'?1:0; //왼쪽 회전 0, 오른쪽 회전 1
		}
		
		//------------------------시작-----------------------------
		snake.add(new Point(0,0));
		snakeHead = snake.get(0);
		snakeTail = snake.get(0);
		board[0][0] = 2; //뱀있는 칸 표시
		
		boolean end = false;
		int d = 0;
		for(int i=0;i<L;i++) {
			for(int t=answer;t<move[i][0];t++) { //다음 회전까지 현재 이동 방향으로 뱀이동 (시작을 현재 지난 시간으로 설정)
				end = go(d);
				if(end) break;
			}
			//방향 전환
			d = move[i][1]==0?(d+3)%4:(d+1)%4;
			if(end) break;
		}
		while(!end) {
			end = go(d);
		}
		System.out.println(answer+1);
		
	}
	static boolean go(int d) {
		int ni = snakeHead.i+di[d];
		int nj = snakeHead.j+dj[d];
		if(ni>=0 && ni<N && nj>=0 && nj<N && board[ni][nj] != 2) {
			//다음 이동할 칸이 벽이 아니고 내 몸도 아니면
			//헤드 이동
			Point newHead = new Point(ni,nj);
			snake.add(0, newHead);
			snakeHead = newHead;
			if(board[ni][nj]!=1) {//이동한 칸에 사과가 없으면
				//테일 이동
				board[snakeTail.i][snakeTail.j] = 0;
				snake.remove(snake.size()-1);
				snakeTail = snake.get(snake.size()-1);
				
			}
			board[ni][nj] = 2;
		}else {
			return true;
		}
		answer++; //시간 증가
		return false;
	}
	static class Point{
		int i, j;
		Point(int i, int j){
			this.i = i;
			this.j = j;
		}
	}
}