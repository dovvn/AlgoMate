package kakao;

import java.util.Arrays;
import java.util.HashMap;

public class Solution_보석쇼핑 {
	public static void main(String[] args) {
		String[] gems = {"A", "A", "B", "A", "A"};
		System.out.println(Arrays.toString(solution(gems)));
		
	}
	public static int[] solution(String[] gems) {
        int[] answer = new int[2];
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        HashMap<String, Integer> hash_map = new HashMap<String, Integer>();
        for(int i=0;i<gems.length;i++) {
        	if(!map.containsKey(gems[i])) {
        		map.put(gems[i], 1);
        	}else {
        		map.replace(gems[i], map.get(gems[i])+1);
        	}
        }
        int size = map.size();
        int left = 0;
        int right = 0;
        int min_len = gems.length+1;
        
        hash_map.put(gems[right], 1);
        
        while(true) {
    		if(hash_map.size() == size) {
    			
    			if(right-left+1 < min_len) { // 구간 길이 최소
    				min_len = right-left+1;
    				answer[0] = left;
    				answer[1] = right;
    			}
    			// left 이동
    			if(hash_map.get(gems[left])-1 == 0) {
    				hash_map.remove(gems[left]);
    			}else {
    				hash_map.replace(gems[left], hash_map.get(gems[left])-1);    				
    			}
    			left++;
    			if(left >= gems.length) break;
    			if(left > right) right = left;
    		}else if(hash_map.size() < size) {
    			// right 이동
    			right++;
    			if(right >= gems.length) break;
    			
    			if(!hash_map.containsKey(gems[right])) {
    				hash_map.put(gems[right], 1);
    			}else {
    				hash_map.replace(gems[right], hash_map.get(gems[right])+1);
    			}
    		}
        }
        answer[0]++;
        answer[1]++;
        return answer;
    }
}
