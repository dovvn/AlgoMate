import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] A;
	static int[] sum; //구역별 인구수
	static int totalSum; //전체 인구수
	static int Ans = Integer.MAX_VALUE; 

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;

		A = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
				totalSum += A[i][j];
			}
		} // End input

		// 1. 기준점,경계길이 정하기
		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				for (int d1 = 1; d1 <= N; d1++) {
					for (int d2 = 1; d2 <= N; d2++) {
						if (x + d1 + d2 >= N) continue; // 범위 체크
						if (y - d1 < 0 || y + d2 > N) continue;
						divide(x, y, d1, d2); //2. 경계 나누기
					}
				}
			}
		}
		
		System.out.println(Ans);
	}

	private static void divide(int x, int y, int d1, int d2) {
		boolean[][] borders = new boolean[N+1][N+1]; // 경계선 true표시

		// 경계선
		for(int i=0; i<=d1; i++) {
			borders[x+i][y-i] = true; //조건1
			borders[x+d2+i][y+d2-i] = true; //조건4
		}
		
		for(int i=0; i<=d2; i++) {
			borders[x+i][y+i] = true; //조건2
			borders[x+d1+i][y-d1+i] = true; //조건3
		}
		
		//3. 구역 나누기
		sum = new int[5];
		//1번 선거구
		for(int i=1; i<x+d1; i++) {
			for(int j=1; j<=y; j++) {
				if(borders[i][j]) break; //경계선 만나면 다음행으로
				sum[0] += A[i][j];
			}
		}
		
		//2번 선거구
		for(int i=1; i<=x+d2; i++) {
			for(int j=N; j>y; j--) {
				if(borders[i][j]) break;
				sum[1] += A[i][j];
			}
		}
		
		//3번 선거구
		for(int i=N; i>=x+d1; i--) {
			for(int j=1; j<y-d1+d2; j++) {
				if(borders[i][j]) break;
				sum[2] += A[i][j];
			}
		}
		
		//4번 선거구
		for(int i=N; i>x+d2; i--) {
			for(int j=N; j>=y-d1+d2; j--) {
				if(borders[i][j]) break;
				sum[3] += A[i][j];
			}
		}
		
		//5번 선거구
		int remain = 0;
		for(int i=0; i<4; i++) remain += sum[i];
		sum[4] = totalSum-remain;
		
		Arrays.sort(sum);
		Ans = Math.min(sum[4]-sum[0], Ans);
	}
}
