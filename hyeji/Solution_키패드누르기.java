package kakao;

public class Solution_키패드누르기 {
	public static void main(String[] args) {
		int[] numbers = {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5};
		String hand = "right";
		System.out.println(solution(numbers, hand));
	}
	public static String solution(int[] numbers, String hand) {
        String answer = "";
        int[][] finger = {{3,0},{3,2}}; // [0][] 왼손 위치, [1][] 오른손 위치
        
        for(int i=0;i<numbers.length;i++) {
        	int target_i = (numbers[i]-1)/3;
    		int target_j = (numbers[i]-1)%3;
    		if(numbers[i] == 0) {
    			target_i = 3;
    			target_j = 1;
    		}
    		
        	if(numbers[i]%3 == 1 ) {
        		answer = addMove(answer,finger,target_i,target_j, "L");
        	}else if(numbers[i] > 0 && numbers[i]%3 == 0) {
        		answer = addMove(answer,finger,target_i,target_j, "R");
        	}else { // 가운데줄
        		int left_len = Math.abs(finger[0][0]-target_i)+Math.abs(finger[0][1]-target_j);
        		int right_len = Math.abs(finger[1][0]-target_i)+Math.abs(finger[1][1]-target_j);
        		
        		if(left_len < right_len) { //왼손으로 누르기
        			answer = addMove(answer,finger,target_i,target_j, "L");
        		}else if(left_len > right_len) { //오른손으로 누르기
        			answer = addMove(answer,finger,target_i,target_j, "R");
        		}else {
        			if(hand.equals("right")) {
        				answer = addMove(answer,finger,target_i,target_j, "R");
        			}else {
        				answer = addMove(answer,finger,target_i,target_j, "L");
        			}
        		}
        	}
        }
        return answer;
    }
	public static String addMove(String answer, int[][] finger, int target_i, int target_j, String dir) {
		if(dir.equals("L")) {
			finger[0][0] = target_i;
			finger[0][1] = target_j;
		}else {
			finger[1][0] = target_i;
			finger[1][1] = target_j;
		}
		return answer+dir;
	}

}
