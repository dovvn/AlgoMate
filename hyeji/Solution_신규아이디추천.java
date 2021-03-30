package kakao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_신규아이디추천 {
	public static String solution(String new_id) {
        String answer = "";
        StringBuffer sb = new StringBuffer();
		boolean dot = false;
		//1단계 + 2단계 + 3단계
        new_id = new_id.toLowerCase();
		for(char x:new_id.toCharArray()) {
			if((x >= 'a' && x <= 'z') || x == '_' || x == '-' || (x >= '0' && x <= '9')) {
				dot = false;
				sb.append(x);
			}else if(!dot && x == '.') {
				dot = true;
				sb.append(x);
			}
		}
		//4단계
		if(sb.length() > 0 && sb.charAt(0) == '.') sb = sb.deleteCharAt(0);
		if(sb.length() > 0 && sb.charAt(sb.length()-1) == '.') sb = sb.deleteCharAt(sb.length()-1);
		//5단계, 6단계
		if(sb.length() <= 0) {
			sb.append("a");
		}else if(sb.length() >= 16) {
			sb.setLength(15);
			if(sb.charAt(sb.length()-1) == '.') sb = sb.deleteCharAt(sb.length()-1);
		}
		//7단계
		if(sb.length() <= 2) {
			while(sb.length() < 3) {
				sb.append(sb.charAt(sb.length()-1));
			}
		}
        answer = sb.toString();
        return answer;
    }
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String new_id = br.readLine();
		
		System.out.println(solution(new_id));
	}

}
