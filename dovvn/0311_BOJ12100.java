import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] map;
	static int[] di = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dj = { 0, 0, -1, 1 };
	static int Ans; // 최대 크기

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		} // End input

		int[] selected = new int[5]; // 이동순서 저장할 배열

		if (N == 1) Ans = map[0][0];
		else per(0, selected);

		System.out.println(Ans);
	}

	private static void per(int idx, int[] selected) { // 중복순열
		if (idx > 4) {
			go(selected); //이 순열로 게임시작
			return;
		}

		for (int i = 0; i < 5; i++) {
			selected[idx] = i;
			per(idx + 1, selected);
		} // End for문
	}

	private static void go(int[] selected) {
		int[][] copyArr = new int[N][N];
		copyArr(copyArr, map);

		for (int idx = 0; idx < 5; idx++) { // 5번 이동
			int d = selected[idx];

			int[][] tmp = new int[N][N];
			Queue<Integer> queue = new LinkedList<>();
			ArrayList<Integer> result = new ArrayList<>();

			switch (d) { // 현재 방향에 따라 블록 탐색
			case 0: // 상
				for (int j = 0; j < N; j++) {
					for (int i = 0; i < N; i++) if(copyArr[i][j] != 0) queue.add(copyArr[i][j]);

					if(queue.size() == 0) continue;
					result = combine(queue); // 합치기


					// tmp에 넣기
					int index = 0;
					for (int k = 0; k < result.size(); k++) {
						tmp[index][j] = result.get(k);
						Ans = Math.max(Ans, tmp[index][j]);
						index++;
					}
				}
				break;
			case 1: // 하
				for (int j = 0; j < N; j++) {
					for (int i = N-1; i >= 0; i--) if(copyArr[i][j] != 0) queue.add(copyArr[i][j]);
					
					if(queue.size() == 0) continue;
					result = combine(queue); // 합치기

					// tmp에 넣기
					int index = N - 1;
					for (int k = 0; k <result.size(); k++) {
						tmp[index][j] = result.get(k);
						Ans = Math.max(Ans, tmp[index][j]);
						index--;
					}
				}
				break;
			case 2: // 좌
				for (int i = 0; i < N; i++) {
					for (int j = N - 1; j >= 0; j--) if(copyArr[i][j] != 0) queue.add(copyArr[i][j]);

					if(queue.size() == 0) continue;
					result = combine(queue); // 합치기

					// tmp에 넣기
					int index = 0;
					for (int k = 0; k < result.size(); k++) {
						tmp[i][index] = result.get(k);
						Ans = Math.max(Ans, tmp[i][index]);
						index++;
					}
				}
				break;
			case 3: // 우
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) if(copyArr[i][j] != 0) queue.add(copyArr[i][j]);

					if(queue.size() == 0) continue;
					result = combine(queue); // 합치기
					
					// tmp에 넣기
					int index = N-1;
					for (int k = 0; k<result.size(); k++) {
						tmp[i][index] = result.get(k);
						Ans = Math.max(Ans, tmp[i][index]);
						index--;
					}
				}
				break;
			} 
			// copyArr에 tmp복사
			copyArr(copyArr, tmp);
		}
	}

	private static void copyArr(int[][] copyArr, int[][] arr) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copyArr[i][j] = arr[i][j];
			}
		}
	}

	private static ArrayList<Integer> combine(Queue<Integer> queue) {
		ArrayList<Integer> result = new ArrayList<>();
		
		
		int value = 0;
		while(!queue.isEmpty()) {
			value = queue.poll();
			if(value == 0) continue;
			if(queue.size()==0) {
				result.add(value);
				break;
			}
			if(value == queue.peek()) {
				result.add(value*2);
				queue.poll();
				continue;
			}
			result.add(value);
		}

		return result;
	}
}
