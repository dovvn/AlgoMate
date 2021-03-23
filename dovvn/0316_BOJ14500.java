import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//테트로미노
public class Main {
	
	static int N,M; //세로,가로
	static int[][] map;
	static int[] di = {-1, 1, 0, 0}; //상하좌우
	static int[] dj = {0, 0, -1, 1};
	static int[][] makeDir = {{2,3,1},{2,3,0},{0,1,2},{0,1,3}}; //ㅗ모양
	static int Ans; //합 최대값
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) map[i][j] = Integer.parseInt(st.nextToken());
		}//End input
		
		
		boolean[][] visited = new boolean[N][M];
		
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				visited[i][j] = true;
				//dfs로 1~4번 테트로미노 하나 만들기
				dfs(i, j, 1, map[i][j], visited); //
				
				//5번 테트로미노 만들기
				makeShape(i, j, map[i][j], visited);
				visited[i][j] = false;
			}
		}
		
		System.out.println(Ans);
	}

	private static void makeShape(int x, int y, int value, boolean[][] visited) {
		for(int i=0; i<4; i++) { //4가지 모양
			boolean isMake = true;
			int sum =  value;
			
			for(int j=0; j<3; j++) {
				int dir = makeDir[i][j];
				int nx = x + di[dir];
				int ny = y + dj[dir];
				
				if(nx>=0 && nx<N && ny>=0 && ny<M) sum += map[nx][ny];
				else{
					isMake = false;
					break;
				}
			}
			if(isMake) {
				Ans = Math.max(Ans, sum);
			}
		}
	}

	private static void dfs(int x, int y, int cnt, int sum, boolean[][] visited) {
		if(cnt >= 4) {
			Ans = Math.max(Ans, sum);
			return;
		}
		
		for(int d=0; d<4; d++) {
			int nx = x + di[d];
			int ny = y + dj[d];
			
			if(nx>=0 && nx<N && ny>=0 && ny<M && !visited[nx][ny]) {
				visited[nx][ny] = true;
				dfs(nx, ny, cnt+1, sum + map[nx][ny], visited);
				visited[nx][ny] = false;
			}
		}
	}
}
