import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, K, L, X; //보드크기, 사과개수, 방향횟수, 초
	static char C; //방향
	static int[][] map;
	static Map<Integer, Character> dirInfo; //초, 회전 방향
	static Queue<Point> queue; //뱀 머리 저장
	static int[] di = {-1, 0, 1, 0}; //상우하좌
	static int[] dj = {0, 1, 0, -1};
	static int hx = 1, hy = 1; //머리 위치
	static int dir = 1; //이동 방향
	static int Ans; //게임시간
	
	static class Point{
		int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		
		StringTokenizer st;
		map = new int[N+1][N+1];
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			map[x][y] = -2;//사과표시
		}//End Apple Input
		
		L = Integer.parseInt(br.readLine());
		dirInfo = new HashMap<>();
		
		for(int i=0; i<L; i++) {
			st = new StringTokenizer(br.readLine());
			
			X = Integer.parseInt(st.nextToken());
			C = st.nextToken().charAt(0);
			dirInfo.put(X, C);
		}//End dir Info
		
		queue = new LinkedList<>();
		queue.add(new Point(1, 1)); //머리정보
		map[1][1] = -1; //뱀 몸 표시
		
		while(true) {
			Ans++; //시간증가
			
			//다음 방향 검사
			int nhx = hx + di[dir];
			int nhy = hy + dj[dir];
			
			if(nhx >= 1 && nhx<=N && nhy>=1 && nhy<=N && map[nhx][nhy] != -1) {
				hx = nhx;
				hy = nhy;
				queue.add(new Point(nhx, nhy)); //머리 넣고
				
				//사과 있는지 체크
				if(map[nhx][nhy] == -2) map[nhx][nhy] = -1; //사과 먹고 몸길이 증가
				else { //꼬리 앞으로 이동
					map[nhx][nhy] = -1;
					Point tail = queue.poll();
					map[tail.x][tail.y] = 0;
				}
			}else { //벽 또는 자기자신과 부딪히면
				System.out.println(Ans);
				break; //종료
			}
			
			//방향 회전 체크
			if(dirInfo.containsKey(Ans)) {
				int rotate = dirInfo.get(Ans);
				if(rotate == 'L') dir = (dir + 3) % 4; //반시계방향
				else dir = (dir + 1) % 4; //시계방향
			}
		}//End game
	}
}
