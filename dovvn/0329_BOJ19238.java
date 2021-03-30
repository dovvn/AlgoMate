import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//스타트택시
public class Main {

    static int N,M;
    static int[][] map;
    static boolean[][] visited;
    static int fuel; //남은 연료 양
    static int taxiX, taxiY; //현재 택시 위치
    static Point[] ends; //도착 좌표 저장
    static int[] di ={-1, 1, 0, 0}; //상하좌우
    static int[] dj = {0, 0, -1, 1};
    static Queue<Point> q;

    static class Point{
        int x, y; //좌표

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());

        map = new int[N+1][N+1];
        ends = new Point[M+1];

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++){
                if(Integer.parseInt(st.nextToken()) == 1) map[i][j] = -1; //-1:벽
            }
        }

        st = new StringTokenizer(br.readLine());
        taxiX = Integer.parseInt(st.nextToken());
        taxiY = Integer.parseInt(st.nextToken());

        for(int i=1; i<=M; i++){
            st = new StringTokenizer(br.readLine());
            int sx = Integer.parseInt(st.nextToken());
            int sy = Integer.parseInt(st.nextToken());
            int ex = Integer.parseInt(st.nextToken());
            int ey = Integer.parseInt(st.nextToken());
            ends[i] = new Point(ex, ey);
            map[sx][sy] = i;
        }//End input

        int cnt = 0; //승객수
        while(true){
            //1. 택시위치로 부터 가장 가까운 손님 구하기
            List<Point> list = getUser();

            if(list.size() == 0){
                System.out.println(-1);
                break;
            }

            Collections.sort(list, new Comparator<Point>() { //행 작은순, 행같으면 열 번호 작은순 (오름차순)
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x > o2.x)
                        return 1;
                    else if (o1.x == o2.x) {
                        if (o1.y > o2.y)
                            return 1;
                    }
                    return -1;
                }
            });

            taxiX = list.get(0).x;
            taxiY = list.get(0).y;

            int ex = ends[map[taxiX][taxiY]].x; //도착 좌표
            int ey = ends[map[taxiX][taxiY]].y;

            map[taxiX][taxiY] = 0;

            //2. 목적지까지 이동
            if(!go(ex, ey)){
                System.out.println(-1);
                break;
            }
            cnt++;
            if(cnt == M){
                System.out.println(fuel);
                break;
            }
        }//End drive
    }

    private static boolean go(int ex, int ey) {
        q = new LinkedList<>();
        visited = new boolean[N+1][N+1];

        q.add(new Point(taxiX, taxiY));
        visited[taxiX][taxiY] = true;

        int dist = 0;
        while(!q.isEmpty()){
            fuel--;
            dist++; //소모 연료(이동 거리)
            int size = q.size();
            for(int s=0; s<size; s++){
                Point p = q.poll();

                for(int d=0; d<4; d++){
                    int nx = p.x + di[d];
                    int ny = p.y + dj[d];

                    if(nx>=1 && nx<=N && ny>=1 && ny<=N && map[nx][ny] != -1 && !visited[nx][ny]){
                        if(nx == ex && ny == ey){ //목적지까지 왔다면
                            taxiX = nx;
                            taxiY = ny;
                            fuel += dist * 2;
                            return true;
                        }else{
                            q.add(new Point(nx, ny));
                            visited[nx][ny] = true;
                        }
                    }
                }
            }
            if(fuel == 0) return false; //중간에 연료 모두 소모
        }
        return false;
    }

    private static List getUser() {
        List<Point> list = new ArrayList<>(); //승객 리스트

        if(map[taxiX][taxiY] >= 1){ //승객좌표==택시좌표
            list.add(new Point(taxiX, taxiY));
            return list;
        }

        q = new LinkedList<>();
        visited = new boolean[N+1][N+1];

        q.add(new Point(taxiX, taxiY));
        visited[taxiX][taxiY] = true;

        boolean flag = false; //승객 찾았는지

        while(!q.isEmpty()){

            fuel--; //거리 증가할때마다 연료 감소

            int size = q.size();
            for(int s=0; s<size; s++){
                Point p = q.poll();
                for(int d=0; d<4; d++){
                    int nx = p.x + di[d];
                    int ny = p.y + dj[d];

                    if(nx>=1 && nx<=N && ny>=1 && ny<=N && map[nx][ny] != -1 && !visited[nx][ny]){
                        if(map[nx][ny] == 0){
                            q.add(new Point(nx, ny));
                            visited[nx][ny] = true;
                        }else if(map[nx][ny] >= 1){ //승객 찾음
                            list.add(new Point(nx, ny));
                            visited[nx][ny] = true;
                            flag = true;
                        }
                    }

                }
            }

            if(flag) return list;
            if(fuel == 0) return new ArrayList<>(); //중간에 연료 모두 소모
        }
        return new ArrayList<>();
    }
}
