#include <iostream>
#include <queue>
#include <vector>
#include <set>

using namespace std;

struct Friend {
  int level;
  int id;
};

int main() {
  std::ios_base::sync_with_stdio(false);
  int n, k, q;
  auto fcmp = [] (Friend &f1, Friend &f2) { return f1.level > f2.level; };
  priority_queue<Friend, vector<Friend>, decltype(fcmp)> top(fcmp);
  set<int> top_members;
  cin >> n >> k >> q;
  vector<int> t(n);
  for (int i = 0; i < n; ++i) cin >> t[i];
  for (int i = 0; i < q; ++i) {
    int type, id;
    cin >> type >> id;
    id--;
    if (type == 1) {
      Friend f;
      f.level = t[id];
      f.id = id;
      top.push(f);
      top_members.insert(id);
      if (top.size() > k) {
        f = top.top();
        top.pop();
        top_members.erase(f.id);
      }
    } else {
      auto it = top_members.find(id);
      if (it != top_members.end()) cout << "YES" << endl;
      else cout << "NO" << endl;
    }
  }
  return 0;
}
