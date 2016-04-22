#include <iostream>
#include <set>

using namespace std;

template<typename T>
bool contains(set<T> &s, T item) {
  auto it = s.find(item);
  return it != s.end();
}

int main() {
  set<int> used;
  int m, n;
  cin >> n >> m;
  for (int i = 0; i < n; ++i) {
    int a;
    cin >> a;
    used.insert(a);
  }
  int M = m;
  int k = 0;
  for (int i = 1; m >= i; ++i) {
    if (!contains(used, i)) {
      ++k;
      m -= i; 
    }
  }
  cout << k << endl;
  m = M;
  for (int i = 1; m >= i; ++i) {
    if (!contains(used, i)) {
      cout << i << " ";
      m -= i; 
    }
  }
  return 0;
}

