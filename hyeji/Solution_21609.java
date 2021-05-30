package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_21609 {
	static int N, M, answer;
	static int[] di = {-1,1,0,0}, dj= {0,0,-1,1}; //상하좌우
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int[][] block = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				block[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// ============ 입력 끝 ====================
		
		// 크기 가장 큰 블럭 찾기
		while(true) {
			boolean[][] group = new boolean[N][N];
			int maxTotalBlockCnt = 0;
			int maxRainbowBlockCnt = 0;
			int[] maxIdx = new int[2];
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(block[i][j] <= 0 || group[i][j]) continue;
					
					Queue<int []> q = new LinkedList<int[]>();
					q.add(new int[] {i,j});
					boolean[][] visit = new boolean[N][N];
					visit[i][j] = true;
					group[i][j] = true;
					int totalBlockCnt = 1;
					int rainbowBlockCnt = 0;
					
					while(!q.isEmpty()) {
						int[] now = q.poll();
						
						for(int d=0;d<4;d++) {
							int ni = now[0]+di[d];
							int nj = now[1]+dj[d];
							
							if(ni >= 0 && ni < N && nj >= 0 && nj < N && block[ni][nj] != -1 && !visit[ni][nj] && !group[ni][nj]) {
								if(block[ni][nj] == 0) {
									rainbowBlockCnt++;
									totalBlockCnt++;
									q.add(new int[] {ni,nj});
									visit[ni][nj] = true;
								}else if(block[ni][nj] == block[i][j]){
									totalBlockCnt++;
									q.add(new int[] {ni,nj});
									visit[ni][nj] = true;
									group[ni][nj] = true;
								}
							}
						}
					}
					
					if(maxTotalBlockCnt <= totalBlockCnt) {
						if(maxTotalBlockCnt < totalBlockCnt || maxRainbowBlockCnt <= rainbowBlockCnt) {
							maxTotalBlockCnt = totalBlockCnt;
							maxRainbowBlockCnt = rainbowBlockCnt;
							
							maxIdx[0] = i;
							maxIdx[1] = j;
						}
					}
				}
			}
			
			if(maxTotalBlockCnt >= 2) {
				answer += maxTotalBlockCnt*maxTotalBlockCnt;
				remove(maxIdx, block);
				down(block);
				block = rotate(block);
				down(block);			
			}else {
				break;
			}
		}
		System.out.println(answer);
	}
	
	public static void remove(int[] point, int[][] block) {
		int num = block[point[0]][point[1]];
		Queue<int []> q = new LinkedList<int[]>();
		q.add(point);
		boolean[][] visit = new boolean[N][N];
		visit[point[0]][point[1]] = true;
		block[point[0]][point[1]] = -2;
		while(!q.isEmpty()) {
			int[] now = q.poll();
			
			for(int d=0;d<4;d++) {
				int ni = now[0]+di[d];
				int nj = now[1]+dj[d];
				
				if(ni >= 0 && ni < N && nj >= 0 && nj < N && block[ni][nj] != -1 && !visit[ni][nj]) {
					if(block[ni][nj] == 0 || block[ni][nj] == num) {
						block[ni][nj] = -2;
						q.add(new int[] {ni,nj});
						visit[ni][nj] = true;
					}
				}
			}
		}
	}
	
	public static void down(int[][] block) {
		for(int j=0;j<N;j++) {
			int target = N-1;
			for(int i=N-1;i>=0;i--) {
				while(block[i][j] < 0 && i > 0) {
					if(block[i][j] == -1) target = i-1;
					i--;
				}
				
				if(target >= 0 && target != i && block[i][j] >= 0) {
					block[target][j] = block[i][j];
					block[i][j] = -2;
					target--;
				}else if(target == i) {
					target--;
				}
			}
		}
	}
	
	public static int[][] rotate(int[][] block){
		int[][] newBlock = new int[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				newBlock[i][j] = block[j][N-1-i];
			}
		}
		return newBlock;
	}
	
	public static void printBlock(int[][] block) {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(block[i][j] != -2) {
					if(block[i][j] >= 0) System.out.print(" "+block[i][j]+" ");
					else System.out.print(block[i][j]+" ");
				}
				else System.out.print("[] ");
			}
			System.out.println();
		}
	}
	
}
