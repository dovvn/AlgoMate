import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int[][] map;
	static int N;
	static int[] di = { 0, 1, 0, -1 }; // 좌하우상
	static int[] dj = { -1, 0, 1, 0 };
	static int dir; // 현재 방향
	static int nowX, nowY;
	static int nx, ny;
	static int Ans; //밖으로 나간 모래양

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		map = new int[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		} // End input

		nowX = (N + 1) / 2; // 시작좌표
		nowY = (N + 1) / 2;

		int cnt = 1; // 앞으로 전진 횟수
		boolean flag = true;
		
		while (flag) {
			for (int i = 0; i < 2; i++) { //방향 두번 바뀌는 동안
				go(cnt); // 앞으로 전진
				if (nowX == 1 && nowY == N) {// 마지막 라인
					go(cnt);
					flag = false;
					break;
				}
			}
			cnt++;
		}//End
		
		System.out.println(Ans);
	}

	private static void go(int cnt) { 
		for (int j = 0; j < cnt; j++) {
			nx = nowX + di[dir];
			ny = nowY + dj[dir];
			tornado(); //모래 이동
			nowX = nx;
			nowY = ny;
		}
		dir = (dir + 1) % 4; //다음 방향
	}

	private static void tornado() {
		int now = map[nx][ny];
		map[nx][ny] = 0;
		
		//이동할 모래비율
		int s1 = (int)(now * 0.01);
		int s2 = (int)(now * 0.02);
		int s5 = (int)(now * 0.05);
		int s7 = (int)(now * 0.07);
		int s10 = (int)(now * 0.1);
		
		//이동안하고 남은모래
		int a = now - 2*(s1+s2+s7+s10)-s5;
		
		int sx, sy; //모래 좌표
		
		//모래 이동 0:왼, 1:아래, 2:우, 3:위
		if(dir==0 || dir==2) { //왼쪽 or 오른쪽
			//1% 위아래
			for(int i=0; i<2; i++) {
				sx = nowX + di[1+2*i];
				sy = nowY + dj[1+2*i];
				check(sx, sy, s1); //밖으로 나가는지 체크
			}
			
			//2% 두칸 위아래
			for(int i=0; i<2; i++) {
				sx = nx + di[1+2*i] * 2;
				sy = ny + dj[1+2*i] * 2;
				check(sx, sy, s2);
			}
			
			//7% 위아래
			for(int i=0; i<2; i++) {
				sx = nx + di[1+2*i];
				sy = ny + dj[1+2*i];
				check(sx, sy, s7);
			}
			
			//10% 한칸 전진하고 위아래
			for(int i = 0; i < 2; i++) {
				sx = nx + di[dir] + di[1+2*i];
	            sy = ny + dj[dir] + dj[1+2*i];
	            check(sx, sy, s10);
	        }
			
			//5% nx,ny에서 두칸 전진
			sx = nx + di[dir]*2;
			sy = ny + dj[dir]*2;
			check(sx, sy, s5);
			
			//a
			sx = nx + di[dir];
			sy = ny + dj[dir];
			check(sx, sy, a);
			
		}else { //위아래
			//1% 왼오
			for(int i = 0; i < 2; i++) {
				sx = nowX + di[2*i];
	            sy = nowY + dj[2*i];
	            check(sx, sy, s1);
	        }
			
			//2% 두칸 왼오
			for(int i = 0; i < 2; i++) {
				sx = nx + di[2*i]*2;
	            sy = ny + dj[2*i]*2;
	            check(sx, sy, s2);
	        }
			
			//7%
			for(int i = 0; i < 2; i++) {
				sx = nx + di[2*i];
	            sy = ny + dj[2*i];
	            check(sx, sy, s7);
	        }
			
			//10% 한칸앞 왼오
			for(int i = 0; i < 2; i++) {
				sx = nx + di[dir] + di[2*i];
	            sy = ny + dj[dir] + dj[2*i];
	            check(sx, sy, s10);
	        }
			
			//5% nx,ny에서 두칸앞
			sx = nx + di[dir]*2;
	        sy = ny + dj[dir]*2;
	        check(sx, sy, s5);

	        //a(알파)
	        sx = nx + di[dir];
	        sy = ny + dj[dir];
	        check(sx, sy, a);
		}
	}

	private static void check(int sx, int sy, int s) {
		if(sx>=1 && sy>=1 && sx<=N && sy<=N) map[sx][sy] += s;
		else Ans += s;
	}
}
