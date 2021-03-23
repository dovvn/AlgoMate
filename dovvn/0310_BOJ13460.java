import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, M; // 세로,가로
	static int[][] map; // 연구소
	static ArrayList<Point> nullList, virusList; // 빈칸 좌표, 바이러스 리스트
	static int[] di = { -1, 0, 1, 0 }; // 상우하좌
	static int[] dj = { 0, 1, 0, -1 };
	static int Ans;

	static class Point {
		int x, y; // 좌표

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		nullList = new ArrayList<>();
		virusList = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 0) nullList.add(new Point(i, j));
				if (map[i][j] == 2) virusList.add(new Point(i, j));
			}
		} // End input

		selected = new int[3];
		
		// 1. 0인 애들 중 벽을 3개 뽑는 조합의 경우의 수를 구한다.
		comb(0, 0);
		System.out.println(Ans);
	}

	static int[] selected; // 뽑은 인덱스
	
	private static void comb(int cnt, int cur) {
		if (cnt == 3) {
			int[][] tmp = new int[N][M]; // 배열 복사

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					tmp[i][j] = map[i][j];
				}
			}

			// 벽 표시
			for (int i = 0; i < selected.length; i++) tmp[nullList.get(selected[i]).x][nullList.get(selected[i]).y] = 1;
			go(tmp); // 2. 바이러스를 확산시켜서 남는 0의 개수를 센다.
			return;
		}

		for (int i = cur; i < nullList.size(); i++) {
			selected[cnt] = i;
			comb(cnt + 1, i + 1);
		}
	}

	private static void go(int[][] tmp) {
		Queue<Point> queue = new LinkedList<>();
		for (int i = 0; i < virusList.size(); i++) queue.add(virusList.get(i));// 바이러스 담음

		while (!queue.isEmpty()) {
			int size = queue.size();
			
			for(int s=0; s<size; s++) {
				Point now = queue.poll();

				for (int d = 0; d < 4; d++) { // 4방향 탐색
					int nx = now.x + di[d];
					int ny = now.y + dj[d];

					if (nx >= 0 && nx < N && ny >= 0 && ny < M && tmp[nx][ny] == 0) {
						tmp[nx][ny] = 2;
						queue.add(new Point(nx, ny));
					}
				}
			}
			
		}//End spread
		
		//남은 안전영역 구하기
		int cnt = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(tmp[i][j] == 0) cnt++;
			}
		}
		Ans = Math.max(cnt, Ans);
	}
}
