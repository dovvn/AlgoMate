import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ20055 {
    public static int N;
    public static int K;
    public static int[] A;
    public static boolean[] robot;
    static int count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        A = new int[2 * N];
        robot = new boolean[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(st.nextToken());
        
        boolean flag = true;
        while (flag) {
        	count++;
        	//1. 벨트회전
            int tmp = A[A.length - 1];
            for (int i = A.length - 1; i > 0; i--) A[i] = A[i - 1];
            A[0] = tmp;

            //로봇도 회전
            for (int i = robot.length - 1; i > 0; i--) robot[i] = robot[i - 1];
            robot[0] = false;
            robot[N - 1] = false;
            
            // 2. 로봇 이동(내구도 체크)
            for (int i = N - 1; i > 0; i--) {   
                if (robot[i - 1] && !robot[i] && A[i] >= 1) {
                    robot[i] = true;
                    robot[i - 1] = false;
                    A[i]--;
                }
            }

            //3. 새로운 로봇 올리기
            if (A[0] > 0) {
                robot[0] = true;
                A[0]--;
            }
            
            //4. 남은 내구도 체크
            int cnt=0;
            for (int i = 0; i < A.length; i++) {
                if (A[i] == 0) cnt++;
                if (cnt >= K) {
                	flag = false;
                	break;
                }
            }
        }
        
        System.out.println(count);
    }
}
