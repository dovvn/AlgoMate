import java.io.*;
import java.util.*;

public class Main {
	static int N,M,K;
	static List<Ball>[][] map;
	static int[] di = {-1, -1, 0, 1, 1, 1, 0, -1}; //01234567
	static int[] dj = {0, 1, 1, 1, 0, -1, -1, -1};
	static int Ans; //남은 질량합
	
	static class Ball{
		int x, y;
		int m,s,d;
		
		public Ball(int m, int s, int d) {
			this.m = m;
			this.s = s;
			this.d = d;
		}
		
		public Ball(int x, int y, int m, int s, int d) {
			this.x = x;
			this.y = y;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new ArrayList[N+1][N+1];
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				map[i][j] = new ArrayList<>();
			}
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			map[r][c].add(new Ball(m,s,d));
			Ans += m;
		}
		
		int cnt = 0;
		while(cnt < K) {
			move(); //모든 볼 이동
			cnt++;
		}
		System.out.println(Ans);
	}

	private static void move() {
		List<Ball> tmpList = new ArrayList<>();
		
		//모든 볼 이동
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				if(map[i][j].size()>=1) {
					for(int s=0; s<map[i][j].size(); s++) {
						Ball b = map[i][j].get(s);
						
						//속력만큼 이동
						int nx = (i + di[b.d]*b.s) % N; 
						int ny = (j + dj[b.d]*b.s) % N;
						
						//다시하기
						if(nx > N) nx = nx % N;
						else if(nx < 1) nx = N - (Math.abs(nx) % N);
						
						if(ny > N) ny = ny % N;
						else if(ny < 1) ny = N - (Math.abs(ny) % N); 	
						tmpList.add(new Ball(nx, ny, b.m, b.s, b.d));
					}
					map[i][j].clear(); //비워주기
				}
			}
		}
		
		//실제 이동
		for(Ball b : tmpList) {
			map[b.x][b.y].add(b);
		}
		
		//2개 이상인 곳 찾기
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				if(map[i][j].size() >= 2) { //하나로 합치기
					int new_m = 0;
					int new_s = 0;
					
					int even = 0;
					int odd = 0;
					for(Ball b : map[i][j]) {
						new_m += b.m;
						new_s += b.s;
						
						if(b.d % 2 == 0) even++;
						else odd++;
					}
					Ans -= new_m;
					new_m /= 5;
					new_s /= map[i][j].size();
					
					map[i][j].clear();
					
					if(new_m == 0) continue;
					Ans += new_m * 4;
					
					if(even == 0 || odd == 0) { //모두 홀수 or 짝수
						int[] dList = {0,2,4,6};
						for(int d=0; d<4; d++) map[i][j].add(new Ball(new_m, new_s, dList[d]));
					}else {
						int[] dList = {1,3,5,7};
						for(int d=0; d<4; d++) map[i][j].add(new Ball(new_m, new_s, dList[d]));
					}	
				}
			}
		}
	}
 }