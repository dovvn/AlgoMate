package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_20055 {
	static int N, K;
	static int[] belt, conveyor;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		belt = new int[2 * N];
		conveyor = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 2 * N; i++) {
			belt[i] = Integer.parseInt(st.nextToken());
		}
		// ==============입력 끝=====================
		int answer = 1;
		while (true) {
			// step 1
			move();
			conveyor[N-1] = 0;
			
			//step 2
			for(int i=N-2;i>=0;i--) {
				if(conveyor[i] != 0 && conveyor[i+1] == 0 && belt[i+1] > 0) {
					conveyor[i+1] = conveyor[i];
					conveyor[i] = 0;
					belt[i+1]--;
				}
			}
			//step 3
			if(belt[0] > 0) {
				conveyor[0] = 1;
				belt[0]--;
			}
			//step 4
			if (end()) break; 
			answer++;
		}
		System.out.println(answer);
	}
	public static void move() {
		int temp = belt[2*N-1];
		for(int i=2*N-1;i>=1;i--) {
			belt[i] = belt[i-1];
		}
		belt[0] = temp;
		
		for(int i=N-1;i>=1;i--) {
			conveyor[i] = conveyor[i-1];
		}
		conveyor[0] = 0;
	}
	
	public static boolean end() {
		int cnt = 0;
		for (int i = 0; i < 2 * N; i++) {
			if (belt[i] == 0) cnt++;
		}
		if (cnt >= K) return true;
		return false;
	}

}
