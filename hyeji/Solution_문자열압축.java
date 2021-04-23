package kakao;

public class Solution_문자열압축 {
	public static void main(String[] args) {
		String s = "aa";
		System.out.println(solution(s));
	}
	public static int solution(String s) {
		int answer = s.length();
		for (int k = 1; k <= s.length() / 2; k++) {
			StringBuffer sb = new StringBuffer();
			// 압축 시작 (k크기로)
			for (int i = 0; i < s.length();) {
				if (s.length() - i < 2 * k) { // 남은 길이가 압축비율 2배보다 작으면 남은 문자열 다 붙이기
					sb.append(s.substring(i));
					break;
				}
				String str = s.substring(i, i + k); // k 크기로 자른 문자열
				int cnt = 1;
				int jump = k; // 다음으로 건너뛸 크기
				for (int j = i + k; j <= s.length() - k; j += k) { // 문자열 비교
					String target = s.substring(j, j + k);
					if (str.equals(target)) {
						cnt++;
						jump += k;
					} else {
						break;
					}
				}
				if(cnt > 1) sb.append(cnt);
				sb.append(str);
				i += jump;
			}
			answer = Math.min(answer, sb.toString().length());
		}
		return answer;
	}

}
