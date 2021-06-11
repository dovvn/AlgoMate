import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int N, M;
	static int[][] map;
	static Point[] numArr; //칸번호(구슬 저장)
	static int sharkX, sharkY; //상어좌표
	static int[][] snailDir = {{0,-1},{1,0},{0,1},{-1,0}};
	static int[][] breakingDir = {{0,0},{-1,0},{1,0},{0,-1},{0,1}};
	static int[] explodingSum = new int[3]; //폭발한 구슬 개수합
	
	static class Point{
		int x, y, val; //좌표는 고정,구슬번호
		public Point(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N+1][N+1];
		numArr = new Point[N*N]; //1번~N*N-1
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}//End input
		
		sharkX = (N+1)/2;
		sharkY = sharkX;
		
		int go = 1; //이동횟수
		int dir = 0; //현재 방향
		int idx=1; //칸번호
		int x = sharkX; //좌표
		int y = sharkY;
		boolean flag = true;
		
		//달팽이
		while(flag) {
			for(int i=0; i<2; i++) { //두번 방향 바꿈
				for(int j=0; j<go; j++) {
					x += snailDir[dir][0];
					y += snailDir[dir][1];
					numArr[idx++] = new Point(x, y, map[x][y]);
					if(x==1 && y==1) {
						flag = false;
						i=2;
						break;
					}
					
				}
				dir = (dir+1)%4;
			}
			go++;
		}	
		
		for(int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			//1. 구슬 파괴
			breaking(d, s);
			//2. 구슬 이동
			moving();
			//3. 구슬 폭발
			exploding();
			//4. 구슬 변화
			changing();
			
		
		}
		
		System.out.println(explodingSum[0] + explodingSum[1]*2 + explodingSum[2]*3);
	}
	
	private static void changing() {
		List<Integer> tmpList = new ArrayList<>();
		
		//구슬 변화
		for(int i=1; i<N*N; i++) {
			if(numArr[i].val==0) break;
			int cnt = 1;
			if(numArr[i].val!=0 && numArr[i].val == numArr[i+1].val) {
				cnt++;
				for(int j=i+1; j<N*N-1; j++) {
					if(numArr[j].val == numArr[j+1].val) cnt++;
					else { //연속된 값 다 찾으면
						tmpList.add(cnt);//개수
						tmpList.add(numArr[i].val);//번호
						i+=cnt-1;//밖에서 한번더 i++
						break;
					}
				}
			}else {
				tmpList.add(cnt);
				tmpList.add(numArr[i].val);
			}
		}
		
		//numArr, map에 새로넣기
		for(int i=0; i<tmpList.size(); i++) {
			if(i+1>=N*N) break;
			numArr[i+1].val = tmpList.get(i);
		}
		
	}

	private static void exploding() {
		boolean flag = true;
		while(flag) {
			flag = false;
			for(int i=1; i<N*N-1; i++) {
				int cnt = 1;
				if(numArr[i].val!=0 && (numArr[i].val == numArr[i+1].val)) {
					cnt++;
					for(int j=i+1; j<N*N-1; j++) {
						if(numArr[j].val==numArr[j+1].val) cnt++;
						else break;
					}
				}
				if(cnt>=4) {
					flag = true;//이동 후 또 연속있는지 확인해야하므로
					explodingSum[numArr[i].val-1] += cnt;
					for(int j=i; j<i+cnt; j++) numArr[j].val = 0;
				}
			}
			moving(); //이동
		}
	}

	private static void moving() {
		for(int i=1; i<N*N; i++) {
			int cnt = 0;
			if(numArr[i].val == 0) {
				int start = i;
				while(start<N*N && numArr[start++].val==0) cnt++;
				for(int j=i; j<i+cnt; j++) { //한칸씩 앞으로 이동
					if(j+cnt>=N*N) break;
					numArr[j].val = numArr[j+cnt].val;
					numArr[j+cnt].val = 0;
				}
			}
		}
	}

	private static void showArr() {
		for(int i=1; i<numArr.length; i++) {
			System.out.println(numArr[i].x+", "+numArr[i].y+", "+numArr[i].val);
		}
	}

	private static void breaking(int d, int s) {
		int nx = sharkX;
		int ny = sharkY;
		
		for(int i=0; i<s; i++) {
			nx += breakingDir[d][0];
			ny += breakingDir[d][1];
			
			if(nx>=1 && nx<=N && ny>=1 && ny<=N) {
				map[nx][ny] = 0;//처음단계에서만 사용, 이후는 numArr로 관리
				
				//numArr에서 찾기
				for(int j=1; j<numArr.length; j++) {
					if(nx==numArr[j].x && ny==numArr[j].y) {
						numArr[j].val = 0;
						break;
					}
				}
			}
		}
	}
}
