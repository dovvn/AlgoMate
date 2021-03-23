import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14499_주사위굴리기 {
    static StringTokenizer st;
    static int N, M, x, y, K;
    static int[][] map; // 이동 판
    static int[] dice; // 위 앞 밑 뒤 왼 오    순서로 주사위 숫자 저장
    static int[] di={0, 0, 0, -1, 1}; // 0 우 좌 상 하
    static int[] dj={0, 1, -1, 0, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // map 크기
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken()); // 시작점
        y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken()); // 이동횟수
        map = new int[N][M]; // map 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        StringBuilder sb = new StringBuilder();
        dice = new int[6]; // 주사위 숫자 저장
        st = new StringTokenizer(br.readLine()); // 이동방향의 입력
        for (int i = 0; i < K; i++) {
            // 방향에 따른 처리
            int dir = Integer.parseInt(st.nextToken()); // 현재의 방향
            int nx = x + di[dir];
            int ny = y + dj[dir];
            if(nx<0 || nx>=N || ny<0 || ny>=M) continue; //범위 밖이면 이동도 안하고 출력도 안하고
            updateDice(dice , dir); // 배열의 위치 조정
            if(map[nx][ny] == 0){ // map 에 0이면
                map[nx][ny] = dice[2]; // 지도에 주사위 숫자를 기록
            }else{ // map 에 숫자면
                dice[2] = map[nx][ny]; // 주사위에 지도 숫자를 기록
                map[nx][ny] = 0; // map 숫자 지우기
            }
            x = nx; y = ny; // 시작점 갱신
            sb.append(dice[0]+"\n");
        }
        System.out.println(sb);
    }
    // 방향에 따라 dice 배열 갱신
    private static void updateDice(int[] d , int dir) {
       int[] tmp = new int[6]; // 넘어온 dice 배열 복사
        for (int i = 0; i < 6; i++) {
            tmp[i] = d[i];
        }
        if(dir == 1){
            d[4] = tmp[0]; // 위 -> 왼
            d[2] = tmp[4]; // 왼 -> 밑
            d[5] = tmp[2]; // 밑 -> 우
            d[0] = tmp[5]; // 우 -> 위
       }else if(dir == 2){
            d[5] = tmp[0]; // 위 -> 우
            d[2] = tmp[5]; // 우 -> 밑
            d[4] = tmp[2]; // 밑 -> 좌
            d[0] = tmp[4]; // 좌 -> 위
       }else if(dir == 3){
            d[3] = tmp[0]; // 위 -> 뒤
            d[2] = tmp[3]; // 뒤 -> 밑
            d[1] = tmp[2]; // 밑 -> 앞
            d[0] = tmp[1]; // 앞 -> 위
       }else if(dir == 4){
            d[3] = tmp[2]; // 밑 -> 뒤
            d[0] = tmp[3]; // 뒤 -> 위
            d[1] = tmp[0]; // 위 -> 앞
            d[2] = tmp[1]; // 앞 -> 밑
       }
//        for (int i = 0; i < 6; i++) {
//            System.out.print(d[i]+" ");
//        }
//        System.out.println();
        for (int i = 0; i < 6; i++) {
            dice[i] = d[i];
        }
    }
}
