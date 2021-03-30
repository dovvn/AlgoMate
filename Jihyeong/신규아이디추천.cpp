#include <string>
#include <vector>
using namespace std;

string solution(string new_id) {
	int idx = 0;
	int pointIdx = 0, cnt=0;
	while (1) {
		if (new_id.length() <= idx) break;
		if (!(('0' <= new_id[idx] && new_id[idx] <= '9') || ('a' <= new_id[idx] && new_id[idx] <= 'z') || ('A' <= new_id[idx] && new_id[idx] <= 'Z') || new_id[idx] == '-' || new_id[idx] == '.' || new_id[idx] == '_')) {
			new_id.erase(idx,1);
			continue;
		}
		if (new_id[idx] == '.') {
			if (cnt == 0) {
				pointIdx = idx;
				cnt++;
			}
			else {
				cnt++;
			}
		}
		else {
			if (cnt != 0) {
				if (pointIdx == 0) {
					new_id.erase(pointIdx, cnt);
					idx -= cnt;
				}
				else {
					new_id.erase(pointIdx, cnt - 1);
					idx -= (cnt - 1);
				}
				cnt = 0;
			}
		}
		if ('A' <= new_id[idx] && new_id[idx] <= 'Z')
			new_id[idx] += 32;
		idx++;
		
	}
	if (cnt > 0) new_id.erase(pointIdx);

	if (new_id.length() == 0) new_id.append("aaa");
	else if (new_id.length() >= 16) new_id.erase(15);
	while (new_id.back() == '.')
		new_id.pop_back();
	while (new_id.length() < 3) {
		new_id+= new_id.back();
	}

	return new_id;
}
int main() {
	solution("=.=");
}