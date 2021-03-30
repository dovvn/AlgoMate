import java.util.*;

class Solution {
    public List<String> solution(String[] orders, int[] course) {
        List<String> answerList = new ArrayList<>();

        //모든 order중 개수에 따라 만들 수 있는 조합 구하기
        for(int num : course){
            map = new HashMap<>(); //조합, 횟수

            for(int i=0; i<orders.length; i++){
                String order = orders[i];
                char[] charArr = order.toCharArray();
                Arrays.sort(charArr);
                order = new String(charArr);

                if(order.length() < num) continue;
                int[] selected = new int[num];
                comb(0, 0 , selected, num, order);


            }
            List<String> keyList = new ArrayList<>(map.keySet());

            Collections.sort(keyList, new Comparator<String>() { //가장 많이 나온순으로 내림차순
                @Override
                public int compare(String o1, String o2) {
                    return map.get(o2).compareTo(map.get(o1));
                }
            });


            int maxCnt = 0; //이 조합이 손님들에게 주문받은 개수
            System.out.println("답");
            for(String key : keyList){
                if(map.get(key) == 1) break; //2명 이상의 손님에게 선택된 경우만 포함하므로
                if(maxCnt <= map.get(key)){
                    maxCnt = Math.max(maxCnt, map.get(key));
                    answerList.add(key);
                }
                else if(maxCnt > map.get(key)) break;
            }
        }//End

        //알파벳순으로 정렬
        Collections.sort(answerList);
        System.out.println(answerList.toString());
        return answerList;
    }

    static Map<String, Integer> map;

    private static void comb(int cnt, int cur, int[] selected, int num, String order) {
        if(cnt == num){
            StringBuilder sb=new StringBuilder();
            for(int i=0; i<selected.length; i++) sb.append(order.charAt(selected[i]));
            if(map.containsKey(sb.toString())){
                int value = map.get(sb.toString());
                map.replace(sb.toString(), value+1);
            }else map.put(sb.toString(),1);
            System.out.println();
            return;
        }

        for(int i=cur; i<order.length(); i++){
            selected[cnt] = i;
            comb(cnt+1, i+1, selected, num, order);
        }
    }
}