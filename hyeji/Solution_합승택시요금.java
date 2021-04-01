package kakao;

public class Solution_합승택시요금 {
	public static void main(String[] args) {
		int n = 6; // 지점 갯수
		int s = 4; // 출발지
		int a = 6; // a 도착지
		int b = 2; // b 도착지
		int[][] fares = {{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}};
		System.out.println(solution(n,s,a,b,fares));
	}
	public static int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = 0;
        int[][] graph = new int[n+1][n+1];
        for(int i=0;i<fares.length;i++) {
        	graph[fares[i][0]][fares[i][1]] = fares[i][2];
        	graph[fares[i][1]][fares[i][0]] = fares[i][2];
        }
        for(int i=1;i<n+1;i++) {
        	for(int j=1;j<n+1;j++) {
        		if(i == j) graph[i][j] = 0;
        		else if(graph[i][j] == 0) graph[i][j] = Integer.MAX_VALUE;
        	}
        }// --------------현재 그래프 상황 저장 --------------------
        
        
        for(int i=1;i<n+1;i++) { // 경유지
        	for(int j=1;j<n+1;j++) { // 출발지
        		if(i == j || graph[i][j] == Integer.MAX_VALUE) continue; //출발지랑 경유지 같을 때, 출발지랑 경유지 길 없을 때 넘기기
        		for(int k=1;k<n+1;k++) { //도착지
        			if(i == k || j == k || graph[i][k] == Integer.MAX_VALUE) continue; // 출발지랑 도착지, 경유지랑 도착지 같을 때, 경우지랑 도착지 길 없을 때 넘기기
        			graph[j][k] = (graph[j][k] < graph[j][i] + graph[i][k])? graph[j][k]: graph[j][i] + graph[i][k]; // 출발지랑 도착지 바로 가는 것과 경유지에 경유해서 가는 것 중 최소 요금
        		}
        	}
        }
        answer = graph[a][s] + graph[b][s];// a,b 따로 갈 때 드는 요금
        for(int i=1;i<n+1;i++) {
        	int cost = graph[a][i] + graph[b][i] + graph[i][s]; // a와 b가 i번 지점에서 만나서 출발지까지 가는 최소 요금
        	if(cost < answer) answer = cost; // 최소값으로 갱신
        	
        }
        return answer;
    }

}
