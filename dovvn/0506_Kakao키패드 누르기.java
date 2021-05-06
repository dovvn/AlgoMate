public class 키패드누르기 {
    public static void main(String[] args) {
        int[] numbers = {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5};
        String hand = "right";
        System.out.println(solution(numbers, hand));
    }

    public static String solution(int[] numbers, String hand) {
        StringBuilder sb = new StringBuilder();
        int left=10; //왼손
        int right=12; //오른손

        for(int i=0;i<numbers.length;i++) {
            if(numbers[i]==0) numbers[i]=11;

            if(numbers[i]%3==1) { //왼쪽
                sb.append("L");
                left=numbers[i];
            }
            else if(numbers[i]%3==0) { //오른쪽
                sb.append("R");
                right=numbers[i];
            }
            else {//가운데
                int dist_l = getDist(left, numbers[i]);
                int dist_r = getDist(right, numbers[i]);
                if(dist_l > dist_r){
                    sb.append('R');
                    right = numbers[i];
                }else if(dist_l < dist_r){
                    sb.append('L');
                    left = numbers[i];
                }else{
                    if(hand.equals("right")){
                        sb.append('R');
                        right = numbers[i];
                    }else{
                        sb.append('L');
                        left = numbers[i];
                    }
                }
            }
        }

        return sb.toString();
    }

    private static int getDist(int idx, int key) {
        if(idx==0) idx = 11;

        int idx_x = (idx-1) / 3;
        int idx_y = (idx-1) % 3 ;
        int key_x = (key-1) / 3;
        int key_y = 1;
        return Math.abs(idx_x-key_x)+Math.abs(idx_y-key_y);
    }
}
