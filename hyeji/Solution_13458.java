package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_13458 {
	static long answer;
	static int N,B,C;
	static int[] room;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		room = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			room[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		answer = 0;
		for(int i=0;i<N;i++) {
			if(room[i] <= B) {
				answer++; //총감독관 한명 배치
			}else {
				answer++; //총감독관 한명 배치
				//부감독관 배치
				if((room[i]-B)%C != 0) {
					answer += (room[i]-B)/C+1;
				}else {
					answer += (room[i]-B)/C;
				}
			}
		}
		System.out.println(answer);
	}

}