#include <iostream>
#include <map>

static const int OFFSET = 32, RADIX = 128 - OFFSET;

struct Trie {
  std::map<char,Trie*> links;
  bool word = 0;

  Trie() {}
};

int  main() {
  // std::ios_base::sync_with_stdio(false);

  freopen("bacon.in", "r", stdin);
  freopen("bacon.out", "w", stdout);

  Trie t;

  std::string s;
  std::cin >> s;

  long count = 0;
  for(int i = 0; i < s.size(); ++i) {
    Trie *tt = &t;
    for(int j = i; j < s.size(); ++j) {
      char c = s[j] - OFFSET;
      if (tt->links[c] == nullptr)
        tt->links[c] = new Trie();
      tt = tt->links[c];
      if (!tt->word) {
        tt->word = true;
        ++count;
      }
    }
  }

  std::cout << count;
  return 0;
}
