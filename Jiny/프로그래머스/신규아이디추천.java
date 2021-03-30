package programmers;
// 2021 카카오 코테 기출
public class 신규아이디추천 {
    public static void main(String[] args) {
        solution("z-+.^.");
    }

    public static String solution(String new_id) {
        String answer = "";
        // 1단계 new_id의 모든 대문자를 대응되는 소문자로 치환합니다.
        new_id = new_id.toLowerCase();
        System.out.println("1: "+new_id);

        //2단계 new_id에서 알파벳 소문자, 숫자, 빼기(-), 밑줄(_), 마침표(.)를 제외한 모든 문자를 제거합니다.
        StringBuffer sb = new StringBuffer();
        sb.append(new_id);
        for (int i = 0; i < sb.length(); i++) {
            int now = sb.charAt(i);
            if((now>=97 && now <=122) || (now>=48 && now<=57) || now == 45 || now == 95 || now == 46) continue;
            sb.deleteCharAt(i);
            i--;
        }
        System.out.println("2: "+sb.toString());

        //3단계 new_id에서 마침표(.)가 2번 이상 연속된 부분을 하나의 마침표(.)로 치환합니다.
        for (int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '.'){ // .이면
                if(i < sb.length()-1){ // 다음칸이 있을 때 다음칸이 .인지 보기
                    if(sb.charAt(i+1) == '.'){
                        sb.deleteCharAt(i+1);
                        i--;
                    }
                }
            }
        }
        System.out.println("3: "+sb.toString());

        //4단계 new_id에서 마침표(.)가 처음이나 끝에 위치한다면 제거합니다.
        if(sb.charAt(0) == '.') sb.deleteCharAt(0);
        if(sb.length() != 0 && sb.charAt(sb.length()-1) == '.') sb.deleteCharAt(sb.length()-1);
        System.out.println("4: "+sb.toString());

        //5단계 new_id가 빈 문자열이라면, new_id에 "a"를 대입합니다.
        if(sb.length() == 0) sb.append("a");
        new_id = sb.toString();
        System.out.println("5: "+new_id);

        //6단계 new_id의 길이가 16자 이상이면, new_id의 첫 15개의 문자를 제외한 나머지 문자들을 모두 제거합니다.
        //     만약 제거 후 마침표(.)가 new_id의 끝에 위치한다면 끝에 위치한 마침표(.) 문자를 제거합니다.
        if(sb.length() >= 16) {
            new_id = sb.substring(0, 15);
            if(new_id.charAt(14) == '.'){
                StringBuffer sbn = new StringBuffer();
                sbn.append(new_id);
                sbn.deleteCharAt(14);
                new_id = sbn.toString();
            }
        }
        System.out.println("6: "+new_id);

        //7단계 new_id의 길이가 2자 이하라면, new_id의 마지막 문자를 new_id의 길이가 3이 될 때까지 반복해서 끝에 붙입니다.
        if(new_id.length() <= 2){
            StringBuffer nsb = new StringBuffer();
            nsb.append(new_id);
            char end = nsb.charAt(nsb.length()-1);
            while (true){
                if(nsb.length() == 3) break;
                nsb.append(end);
            }
            new_id = nsb.toString();
        }
        System.out.println("7: "+new_id);
        answer = new_id;
        System.out.println("결과 : "+answer);
        return answer;
    }
}