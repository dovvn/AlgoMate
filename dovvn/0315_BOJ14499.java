import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int N, M, x, y, K; // 세로,가로,주사위좌표,명령수
	static int[][] map; // 지도
	static int[] dice;
	static int up = 1; // 위좌표
	static int[] di = { 0, 0, 0, -1, 1 }; // 동1 서2 북3 남4
	static int[] dj = { 0, 1, -1, 0, 0 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		dice = new int[7]; // 1부터 시작

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		} // End map input

		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < K; i++) {
			int d = Integer.parseInt(st.nextToken()); // 현재 방향
			// 지도 다음 위치
			int nx = x + di[d];
			int ny = y + dj[d];

			if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;

			int tmp = 0;

			// 주사위 방향 변경
			switch (d) {
			case 1: // 동
				tmp = dice[1];
				dice[1] = dice[4];
				dice[4] = dice[6];
				dice[6] = dice[3];
				dice[3] = tmp;
				break;

			case 2: // 서
				tmp = dice[1];
				dice[1] = dice[3];
				dice[3] = dice[6];
				dice[6] = dice[4];
				dice[4] = tmp;
				break;

			case 3: // 북
				tmp = dice[1];
				dice[1] = dice[5];
				dice[5] = dice[6];
				dice[6] = dice[2];
				dice[2] = tmp;
				break;

			case 4: // 남
				tmp = dice[1];
				dice[1] = dice[2];
				dice[2] = dice[6];
				dice[6] = dice[5];
				dice[5] = tmp;
				break;
			}

			// 지도 <- 주사위 바닥
			if (map[nx][ny] == 0) map[nx][ny] = dice[6];
			else {
				// 주사위 바닥 <- 지도, 지도 == 0으로
				dice[6] = map[nx][ny];
				map[nx][ny] = 0;
			}
			x = nx;
			y = ny;
			
			sb.append(dice[1]+"\n");
		} // End input
		
		System.out.println(sb+"");
	}
}
