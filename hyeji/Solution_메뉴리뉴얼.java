package kakao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Solution_메뉴리뉴얼 {
	public static void main(String[] args) {
		String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
		int[] course = {2,3,4};
		String[] answer = solution(orders, course);
		for(String a:answer) {
			System.out.println(a);
		}
	}
	public static String[] solution(String[] orders, int[] course) {
        String[] answer = {};
        List<String> ans = new ArrayList<String>(); // 정답 리스트
        List<String> temp = new ArrayList<String>(); // 가장 많이 함께 주문한 요리 넣을 것
    	List<String> combi = new ArrayList<String>(); // N개 메뉴로 만들수 있는 조합 넣을 것
    	List<Integer> combiCnt= new ArrayList<Integer>(); // 조합이 몇번 주문 되었는지 넣을 것
        
    	for(int i=0;i<course.length;i++) {
        	int N = course[i];
        	temp.clear();
        	combi.clear();
        	combiCnt.clear();
        	int[] pick = new int[N];
        	// N개의 메뉴로 만들 수 있는 조합 찾기
        	for(String order : orders) {
        		char[] input = order.toCharArray(); 
				Arrays.sort(input);
        		comb(0,0,N,input,pick, combi,combiCnt);
        	}
    		int max = 0; // 가장 많이 주문한 횟수
    		for(int k=0;k<combiCnt.size();k++) {
    			int cnt = combiCnt.get(k);
    			if(cnt >=2 && cnt >= max) {
    				if(cnt != max) { // cnt가 max보다 큼(갱신)
    					temp.clear();
    					max = cnt;    					
    				}
    				temp.add(combi.get(k)); // max번 주문한 조합 넣기
    			}
    		}
    		for(String t:temp) {
    			ans.add(t); // 찾은 조합 정답에 넣어주기
    		}
        }
        // 사전 순으로 정렬
        Collections.sort(ans, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
        	
        });
        answer = ans.toArray(new String[ans.size()]);
        return answer;
    }
	public static void comb(int cur, int cnt, int N, char[] order, int[] pick, List<String> combi, List<Integer> combiCnt) {
		if(cnt == N) {
			StringBuffer sb = new StringBuffer(); // 조합 생성
			for(int i=0;i<pick.length;i++) {
				sb.append(order[pick[i]]);
			}
			if(!combi.contains(sb.toString())) { // 처음 만들어진 조합이면
				combi.add(sb.toString()); // 조합에 넣고
				combiCnt.add(1); // 카운트 1
			}else { // 이미 앞에 만들어졌던 조합이면
				int x = combi.indexOf(sb.toString()); // 조합 인덱스 찾고
				int num = combiCnt.remove(x);
				combiCnt.add(x, num+1); // 이전 카운트 증가
			}
			return;
		}
		for(int i=cur; i<order.length;i++) { // 조합 찾기
			pick[cnt] = i;
			comb(i+1,cnt+1,N,order,pick,combi,combiCnt);
		}
		
	}
}
