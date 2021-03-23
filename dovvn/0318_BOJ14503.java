import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static StringTokenizer st;
    static int N, M, clear;
    static int[][] map;
    static int[] di={-1, 0, 1, 0}; // 상 우 하 좌
    static int[] dj={0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine()); // 시작좌표, 방향
        int sx = Integer.parseInt(st.nextToken()); //현재 로봇 좌표
        int sy = Integer.parseInt(st.nextToken());
        int startDir = Integer.parseInt(st.nextToken()); //현재 방향

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 탐색 시작
        while (true){
            // 1. 현재 위치를 청소한다.
            if(map[sx][sy] == 0) {
            	map[sx][sy] = 9;
            	clear++; //청소한다.
            }
 
            boolean endSearch = false; //4방향 탐색 모두 탐색했으면 true로 바꿔줄 flag변수
            
            int dir = 0;
          //2. 현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
            for (int d = 0; d < 4; d++) {
            	dir = Direction(startDir); // 현재 바라보는 방향 기준으로 왼쪽 방향 구함
            	
            	//다음 좌표를 바라보자
                int chx = sx + di[dir];
                int chy = sy + dj[dir];
                
                //a. 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다
                if(map[chx][chy] == 0) {
                	startDir = dir; //현재 방향으로 회전하고
                	sx = chx; //한칸 전진
                	sy = chy;
                	break; //1번 부터 진행한다.
                }else startDir = dir; //b. 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
                
                if(d == 3) endSearch = true;
            }

            //네 방향 모두 탐색했는데 청소할 공간이 없는 경우 => 후진 판단 c,d번
            if(endSearch) {
            	int rx = sx - di[dir]; //후진했을때 좌표
            	int ry = sy - dj[dir];
            	
            	//d. 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
                if(map[rx][ry] == 1 ) { //좌표값이 0이거나 청소 했을때(9)도 후진할 수 있음!! 벽(1)일때만 후진 못함!
                	break; //작동 멈춘다.
                }
                else { //c. 벽이 아니라서 후진할 수 있으면 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
                    sx = rx;
                    sy = ry;
                }
            }
        }
        System.out.println(clear);
    }

    private static int Direction(int sd) { // 왼쪽방향 결정
        if(sd == 0) return 3;
        else if(sd == 3) return 2;
        else if(sd == 2) return 1;
        else return 0;
    }
}