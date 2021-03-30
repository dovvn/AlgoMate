#define _CRT_SECURE_NO_WARNINGS
#include <cstdio>
#include <vector>
#include <queue>
using namespace std;
struct Taxi {
	int x, y, fuel;
};
struct Passenger {
	int x1, y1, x2, y2;
};
struct point {
	int x, y;
};
const int MAX = 20;
int dx[] = { -1,1,0,0 };
int dy[] = { 0,0,-1,1 };
int n, m;
int map[MAX][MAX] = { 0, };
int start[MAX][MAX] = { 0, };
int person = -1;
bool isPossible = true;
Taxi taxi;
vector <Passenger> passengers;

int findPassenger() {
	if (start[taxi.x][taxi.y] != 0) {
		person = start[taxi.x][taxi.y];
		return 0;
	}
	int targetX = MAX, targetY = MAX;
	queue <point> q;
	int dist = 1;
	bool visit[MAX][MAX] = { false, };
	q.push({ taxi.x,taxi.y });
	visit[taxi.x][taxi.y] = true;
	while (!q.empty()) {
		int size = q.size();
		while (size--) {
			point p = q.front();
			q.pop();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				
				if (nx < 0 || ny < 0 || nx >= n || ny >= n || map[nx][ny] == 1 || visit[nx][ny]) continue;
				visit[nx][ny] = true;
				q.push({ nx,ny });
				if (start[nx][ny] != 0) {
					if (targetX > nx) {
						person = start[nx][ny];
						targetX = nx; targetY = ny;
					}
					else if (targetX == nx) {
						if (targetY > ny) {
							person = start[nx][ny];
							targetY = ny;
						}
					}
				}
			}
		}
		if (targetX != MAX || targetY != MAX) break;
		dist++;
	}
	if (targetX == MAX && targetY == MAX) return -1;
	return dist;
}
int go() {
	queue <point> q;
	int dist = 1;
	bool visit[MAX][MAX] = { false, };
	q.push({ taxi.x,taxi.y });
	visit[taxi.x][taxi.y] = true;
	while (!q.empty()) {
		int size = q.size();
		while (size--) {
			point p = q.front();
			q.pop();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if (nx == passengers[person].x2 && ny == passengers[person].y2) return dist;
				if (nx < 0 || ny < 0 || nx >= n || ny >= n || map[nx][ny] == 1 || visit[nx][ny]) continue;
				visit[nx][ny] = true;
				q.push({ nx,ny });
			}
		}
		dist++;
	}
	return -1;
}

int main() {
	passengers.push_back({ -1,-1,-1,-1 });
	scanf("%d %d %d ", &n, &m, &taxi.fuel);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			scanf("%d ", &map[i][j]);
		}
	}
	scanf("%d %d ", &taxi.x, &taxi.y);
	taxi.x -= 1; taxi.y -= 1;
	for (int i = 1; i <= m; i++) {
		int x1, y1, x2, y2;
		scanf("%d %d %d %d", &x1, &y1, &x2, &y2);
		start[x1 - 1][y1 - 1] = i;
		passengers.push_back({ x1 - 1,y1 - 1,x2 - 1,y2 - 1 });
	}
	while (m--) {
		int dist1 = findPassenger();
		if (dist1 == -1 || dist1 > taxi.fuel) break;
		taxi.fuel -= dist1;
		taxi.x = passengers[person].x1;
		taxi.y = passengers[person].y1;
		int dist2 = go();
		if (dist2==-1 || dist2 > taxi.fuel) break;
		taxi.fuel += dist2;
		start[passengers[person].x1][passengers[person].y1] = 0;
		taxi.x = passengers[person].x2;
		taxi.y = passengers[person].y2;
	}
	if (m != -1) printf("-1\n");
	else printf("%d\n", taxi.fuel); 
	return 0;
}