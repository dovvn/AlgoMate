import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ17406 {
	
	static int N,M,K,depth;
	static int[][] map;
	static int[][] kInfo;
	static int r,c,s,lx,ly,rx,ry;
	static int ans = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N+1][M+1];
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}//End input
		
		kInfo = new int[K][3];
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			kInfo[i][0] = Integer.parseInt(st.nextToken());
			kInfo[i][1] = Integer.parseInt(st.nextToken());
			kInfo[i][2] = Integer.parseInt(st.nextToken());
		}
		
		int[] nums = new int[K];
		boolean[] visited = new boolean[K];
		
		//1. 순열
		per(0, visited, nums);
		System.out.println(ans);
		
	}

	private static void per(int idx, boolean[] visited, int[] nums) {
		if(idx>=K) {
			//2. 회전
			rotate(nums);
			return;
		}
		
		for(int i=0; i<K; i++) {
			if(!visited[i]) {
				nums[idx] = i;
				visited[i] = true;
				per(idx+1, visited, nums);
				visited[i] = false;
			}
		}
	}

	private static void rotate(int[] nums) {
		int[][] tmp = copyMap();
		
		for(int i=0; i<K; i++) {
			r = kInfo[nums[i]][0];
			c = kInfo[nums[i]][1];
			s = kInfo[nums[i]][2];
			
			for(int j=0; j<s; j++) {
				lx = r-s+j; //왼쪽 상단 좌표
				ly = c-s+j;
				rx = r+s-j;
				ry = c+s-j; //오른쪽 아래 좌표
				
				//시계방향 회전(우상좌하)
				int temp = tmp[lx][ry];
				for(int k=ry-1; k>=ly; k--) {
					tmp[lx][k+1] = tmp[lx][k];
				}
				
				for(int k=lx+1; k<=rx; k++) {
					tmp[k-1][ly] = tmp[k][ly];
				}
				
				for(int k=ly+1; k<=ry; k++) {
					tmp[rx][k-1] = tmp[rx][k];
				}
				
				for(int k=rx-1; k>=lx; k--) {
					tmp[k+1][ry] = tmp[k][ry];
				}
				
				tmp[lx+1][ry] = temp;
			}
		}
		
		//최소값 구하기
		for(int x=1; x<=N; x++) {
			int sum = 0;
			for(int y=1; y<=M; y++) {
				sum += tmp[x][y];
			}
			ans = Math.min(sum, ans);
		}
	}


	private static int[][] copyMap() {
		int[][] tmp = new int[N+1][M+1];
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=M; j++) {
				tmp[i][j] = map[i][j];
			}
		}
		return tmp;
	}
}
