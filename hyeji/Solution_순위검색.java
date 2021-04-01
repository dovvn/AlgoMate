package kakao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Solution_순위검색 {
	public static void main(String[] args) {
		String[] info = { "java backend junior pizza 150", "python frontend senior chicken 210",
				"python frontend senior chicken 150", "cpp backend senior pizza 260", "java backend junior chicken 80",
				"python backend senior chicken 50" };
		String[] query = { "java and backend and junior and pizza 100",
				"python and frontend and senior and chicken 200", "cpp and - and senior and pizza 250",
				"- and backend and senior and - 150", "- and - and - and chicken 100", "- and - and - and - 150" };
		int[] answer = solution(info, query);
		System.out.println(Arrays.toString(answer));
	}
	public static int[] solution(String[] info, String[] query) {
		int[] answer = new int[query.length];
		int[] size = {3,2,2,2};
		HashMap<String, Integer> info_case = new HashMap<String, Integer>() {
			{
				put("cpp", 0);
				put("java", 1);
				put("python", 2);
				put("backend", 0);
				put("frontend", 1);
				put("junior", 0);
				put("senior", 1);
				put("chicken", 0);
				put("pizza", 1);
			}
		};
		List<Integer>[][][][] type = new List[3][2][2][2];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					for (int l = 0; l < 2; l++) {
						type[i][j][k][l] = new ArrayList<Integer>();
					}
				}
			}
		}
		for (int i = 0; i < info.length; i++) {
			String[] infos = info[i].split(" ");
			type[info_case.get(infos[0])][info_case.get(infos[1])][info_case.get(infos[2])][info_case.get(infos[3])]
					.add(Integer.parseInt(infos[4]));
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					for (int l = 0; l < 2; l++) {
						Collections.sort(type[i][j][k][l]);
					}
				}
			}
		}
		
		int[][] for_idx = new int[4][2];
		for (int i = 0; i < query.length; i++) {
			String[] temp = query[i].split(" ");
			for (int j = 0; j < temp.length; j++) {
				if (j % 2 == 0) { // 조건문
					if(temp[j].equals("-")) {// 모든 case 허용
						for_idx[j/2][0] = 0;
						for_idx[j/2][1] = size[j/2];
					}else {
						for_idx[j/2][0] = info_case.get(temp[j]);
						for_idx[j/2][1] = for_idx[j/2][0]+1;
					}
				}
			}
			int cnt = 0;
			for (int l = for_idx[0][0]; l < for_idx[0][1]; l++) {
				for (int m = for_idx[1][0]; m < for_idx[1][1]; m++) {
					for (int n = for_idx[2][0]; n < for_idx[2][1]; n++) {
						for (int o = for_idx[3][0]; o < for_idx[3][1]; o++) {
							if(type[l][m][n][o].size() == 0) continue;
							
							int start_idx = lowerBound(type[l][m][n][o], Integer.parseInt(temp[7]));
							cnt += type[l][m][n][o].size()-start_idx;
						}
					}
				}
			}
			answer[i] = cnt;
		}
		return answer;
	}
	private static int lowerBound(List<Integer> data, int target) {
	    int begin = 0;
	    int end = data.size();
	    
	    while(begin < end) {
	    	int mid = (begin + end) / 2;
	        if(data.get(mid) >= target) {
	        	end = mid;
	        }
	        else {
	        	begin = mid + 1;
	        }
	    }
	    return end;
	}
}
