package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 4*4 공간 (x,y) x는 행, y는 열
 * 한칸에 물고기 한마리 존재, 번호와 방향을 가짐
 * 번호는 1이상 16이하 자연수, 8가지 방향
 * 상어 (0,0) 먹고. (0,0) 물고기 방향
 * 번호가 작은 물고기부터 순서대로 이동, 한칸 이동, 이동할 수 있는 칸은 빈칸 or 다른 물고기가 있는 칸
 * 상어나, 공간의 경계는 이동 못함
 * 이동 가능하면 45도 반시계회전하여 이동, 다른 물고기가 있는 칸으로 이동하면 서로 위치가 바뀜
 * 물고기 이동이 다 끝나면 상어 이동, 한번에 여러 칸 이동 가능
 * 그 칸에 있는 물고기를 먹고 그 물고기의 방향을 가짐
 * 물고기가 없는 칸으로 이동 못함
 * */
public class Solution_19236 {
	static int[] di= {0,-1,-1,0,1,1,1,0,-1},dj= {0,0,-1,-1,-1,0,1,1,1}; //반시계방향
	static int answer;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Fish> fish = new PriorityQueue<Fish>();
		Fish[][] map = new Fish[4][4];
		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 4; j++) {
				int num = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				Fish f = new Fish(i, j, num, d);
				map[i][j] = f;
				fish.add(f);
			}
		}
		// ------------------------- 입력 끝 ---------------------------
		int[] shark = new int[3];
		shark[2] = map[0][0].d;
		int cnt = map[0][0].num;
		fish.remove(map[0][0]);
		map[0][0] = null;

		go(shark, map, fish, cnt);
		
		System.out.println(answer);
	}
	static void go(int[] shark, Fish[][] map, PriorityQueue<Fish> fish, int cnt) {
		while(!fish.isEmpty()) { // 물고기 이동 (숫자 빠른 순)
			Fish f = fish.poll();
			for(int d=0;d<8;d++) {
				int dir = (f.d+d)%8 == 0?8 : (f.d+d)%8; // 1->2->3->4->5->6->7->8->
				int ni = f.i+di[dir]; 
				int nj = f.j+dj[dir];
				
				if(check(ni,nj) && (shark[0] != ni || shark[1] != nj)) {
					if(map[ni][nj] != null) { // 가려는 자리에 물고기 있음
						// 물고기 서로 자리바꿈
						if(fish.remove(map[ni][nj])) { // 트루면 숫자 큰거
							Fish n_fish = new Fish(ni, nj, f.num, dir);
							int temp_num = map[ni][nj].num;
							int temp_d = map[ni][nj].d;
							f = new Fish(f.i,f.j, temp_num, temp_d);
							map[f.i][f.j] = f;
							map[ni][nj] = n_fish;
							fish.add(f);
						}else {
							Fish n_fish = new Fish(ni, nj, f.num, dir);
							int temp_num = map[ni][nj].num;
							int temp_d = map[ni][nj].d;
							f = new Fish(f.i,f.j, temp_num, temp_d);
							map[f.i][f.j] = f;
							map[ni][nj] = n_fish;
						}
					}else { // 가려는 자리에 물고기 없음
						map[f.i][f.j] = null;
						map[ni][nj] = new Fish(ni,nj,f.num,dir); 
					}
					break;
				}
			}
		}// 물고기 이동 끝
		//상어 이동
		int shark_i = shark[0];
		int shark_j = shark[1];
		PriorityQueue<Fish> new_fish = new PriorityQueue<Fish>();
		while(check(shark_i+di[shark[2]], shark_j+dj[shark[2]])) {
			shark_i = shark_i+di[shark[2]];
			shark_j = shark_j+dj[shark[2]];
			if(map[shark_i][shark_j] == null) continue;
			int eat = map[shark_i][shark_j].num;
			int dir = map[shark_i][shark_j].d;
			int[] new_shark = {shark_i, shark_j, dir};
			Fish[][] new_map = copyMap(map);
			// 물고기 먹음
			
			new_map[shark_i][shark_j] = null;
			nextFish(new_fish, new_map);
			
			go(new_shark, new_map, new_fish, cnt+eat);
			// 먹은 물고기 다시 돌리기
		}
		//상어 더이상 이동 x
		answer = Math.max(answer, cnt);
		return ;
		
	}
	static void nextFish(PriorityQueue<Fish> new_fish, Fish[][] map){
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(map[i][j] == null) continue;
				new_fish.add(map[i][j]);
			}
		}
		
	}
	
	static Fish[][] copyMap(Fish[][] map){
		Fish[][] new_map = new Fish[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(map[i][j] == null) new_map[i][j] = null;
				else new_map[i][j] = new Fish(map[i][j].i, map[i][j].j, map[i][j].num, map[i][j].d);
			}
		}
		return new_map;
	}
	
	static void printMap(Fish[][] map) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(map[i][j] == null) System.out.print("null ");
				else System.out.print("("+map[i][j].num+","+map[i][j].d+") ");
			}
			System.out.println();
		}
	}
	
	static boolean check(int i, int j) {
		if(i >= 0 && i < 4 && j >=0 && j < 4) {
			return true;
		}
		return false;
	}
	
	static class Fish implements Comparable<Fish>{
		int i, j;
		int num, d;

		Fish(int i, int j, int num, int d) {
			this.i = i;
			this.j = j;
			this.num = num;
			this.d = d;
		}

		@Override
		public int compareTo(Fish o) {
			// TODO Auto-generated method stub
			return this.num - o.num;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "fish "+this.num+": i="+this.i+" j="+this.j+" d="+this.d;
		}
	}

}
