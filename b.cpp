#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Contestant {
  string name;
  int region;
  int score;
};
bool lt(Contestant &c1, Contestant &c2) {
  if (c1.region != c2.region) return c1.region < c2.region;
  return c1.score > c2.score;
}
int main() {
  ios_base::sync_with_stdio(false);
  // n: no of cc
  // m: no of regions
  int n, m;
  cin >> n >> m;
  vector<Contestant> cc(n);
  vector<int> regs(m);
  for (auto it = regs.begin(); it != regs.end(); ++it)
    *it = -1;
  for (int i = 0; i < n; ++i) {
    Contestant c;
    cin >> c.name >> c.region >> c.score;
    c.region--;
    cc[i] = c;
  }

  sort(cc.begin(), cc.end(), lt);
  
  for (int i = 0, n_i; i < n; i = n_i) {
    int reg = cc[i].region;
    int sec = 0;
    for (n_i = i+1; n_i < n && cc[n_i].region == reg; ++n_i) {
      if (cc[n_i].score == cc[i+1].score) ++sec;
    }
    if (sec < 2) regs[reg] = i;
  }

  for (auto it = regs.begin(); it != regs.end(); ++it) {
    int i = *it;
    if (i == -1) cout << "?" << endl;
    else cout << cc[i].name << " " << cc[i+1].name << endl;
  }
  return 0;
}
