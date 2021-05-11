package kakao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_수식최대화 {
	public static void main(String[] args) {
		String expression = "100-200*300-500+20"; //"100-200*300-500+20"
		System.out.println(solution(expression));
		
	}
	public static long solution(String expression) {
        long answer = 0;
        int[][] priority = {{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,0,1},{2,1,0}};
        char[] operator = {'+', '-', '*'};
        List<Long> operands = new ArrayList<Long>(); // {100,200,300,500,20}
        List<Character> operators = new ArrayList<Character>(); // {-, *, -, +}
        StringTokenizer st = new StringTokenizer(expression, "-*+", true);
        int size = st.countTokens();
        for(int i=0;i<size;i++) {
        	if(i%2==0) {
        		operands.add(Long.parseLong(st.nextToken()));
        	}else {
        		operators.add(st.nextToken().charAt(0));
        	}
        }
        /*long num = 0;
        for(char x : expression.toCharArray()) {
        	if( x >= '0' && x <= '9') {
        		num = num*10 + (x-'0');
        	}else {
        		operands.add(num);
        		operators.add(x);
        		num = (long)0;
        	}
        }
        operands.add(num);*/
        // ---------------------------------------------------------------
        for(int i=0;i<6;i++) { // 우선순위 6가지 경우 
        	List<Long> new_operands = new ArrayList<Long>(operands);
            List<Character> new_operators = new ArrayList<Character>(operators);
        	for(int j=0;j<3;j++) { // 우선순위 순서대로 계산
        		char op = operator[priority[i][j]];
        		for(int k=0;k<new_operators.size();k++) { // 계산식에서 연산자 하나씩 찾아보면서 계산
        			if(new_operators.get(k) == op) { // k 위치 계산
        				long a = new_operands.remove(k);
        				long b = new_operands.remove(k);
        				new_operands.add(k, cal(a,b,op));
        				new_operators.remove(k);
        				k--;
        			}
        		}
        	}
        	answer = Math.max(answer, Math.abs(new_operands.get(0)));
        }
        return answer;
    }
	public static long cal(long a, long b, char op) {
		long result = 0;
		switch(op) {
		case '+':
			result= a+b;
			break;
		case '-':
			result= a-b;
			break;
		case '*':
			result= a*b;
			break;
		}
		return result;
	}

}
