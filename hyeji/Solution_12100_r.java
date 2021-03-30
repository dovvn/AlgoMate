package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_12100_r {
	static int N, answer;
	static int[][] board;
	static int[] di= {-1,1,0,0}, dj= {0,0,-1,1}, order; //상하좌우
	static List<Point> blocks;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		order = new int[5];
		blocks = new ArrayList<Point>();
		answer = 0;
		for(int i=0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int value = Integer.parseInt(st.nextToken());
				board[i][j] = value;
				if(value != 0) blocks.add(new Point(i,j,value)); 
			}
		}
		perm(0);
		System.out.println(answer);
	}
	static List<Point> makeBlocks(int[][] board){
		List<Point> blocks = new ArrayList<Point>();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] != 0) blocks.add(new Point(i,j,board[i][j])); 
			}
		}
		return blocks;
	}
	static int[][] go(int[][] board, int d, List<Point> block) {
		PriorityQueue<Point> blockOrder = null;
		switch(d) {
		case 0:	//상
			blockOrder = new PriorityQueue<Point>(new Comparator<Point>(){
				@Override
				public int compare(Point o1, Point o2) {
					return (o1.i*N+o1.j)-(o2.i*N+o2.j);
				}
			});
			break;
		case 1: //하
			blockOrder = new PriorityQueue<Point>(new Comparator<Point>(){
				@Override
				public int compare(Point o1, Point o2) {
					return (o2.i*N+o2.j)-(o1.i*N+o1.j);
				}
			});
			break;
		case 2: //좌
			blockOrder = new PriorityQueue<Point>(new Comparator<Point>(){
				@Override
				public int compare(Point o1, Point o2) {
					return (o1.j*N+o1.i)-(o2.j*N+o2.i);
				}
			});
			break;
		case 3: //우
			blockOrder = new PriorityQueue<Point>(new Comparator<Point>(){
				@Override
				public int compare(Point o1, Point o2) {
					return (o2.j*N+o2.i)-(o1.j*N+o1.i);
				}
			});
			break;
		}
		for(Point b : block) {
			blockOrder.add(b);
		}
		boolean check[][] = new boolean[N][N];
		while(!blockOrder.isEmpty()) {
			Point b = blockOrder.poll();
			while(true) {
				int ni = b.i+di[d];
				int nj = b.j+dj[d];
				
				if(ni <0 || ni >= N || nj < 0 || nj >= N) break;
				else if(board[ni][nj] !=0) {
					if(!check[ni][nj] && board[ni][nj] == b.value) {
						board[ni][nj] = b.value*2;
						check[ni][nj] = true;
						board[b.i][b.j] = 0;
					}
					break;
				}else {
					board[b.i][b.j] = 0;
					b.i = ni;
					b.j = nj;
					board[b.i][b.j] = b.value;
				}
			}
		}
		return board;
	}
	static void perm(int cnt) {
		if(cnt == 5) { //이동 순서 정하기
			int[][] new_board = new int[N][N];
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					new_board[i][j] = board[i][j];
				}
			}
			for(int i=0;i<5;i++) {
				List<Point> block = makeBlocks(new_board);
				new_board = go(new_board,order[i], block);
			}
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					answer = Math.max(answer, new_board[i][j]);
				}
			}
			return ;
		}
		
		for(int i=0;i<4;i++) {
			order[cnt] = i;
			perm(cnt+1);
		}
	}
	static class Point{
		int i, j;
		int value;
		Point(int i, int j, int value){
			this.i = i;
			this.j = j;
			this.value = value;
		}
	}
}

