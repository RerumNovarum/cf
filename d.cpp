#include <iostream>
#include <vector>
#include <queue>
#include <list>

using namespace std;

struct FlowEdge {
  double c;
  double f;
  int u, v;

  int other(int w) {
    if (w == this->u) return v;
    return this->u;
  }
  double flowTo(int w) {
    if (w == this->v) return this->f;
    return this->c - this->f;
  } 
  double capTo(int w) {
    if (this->v == w) return this->c - this->f;
    else return this->f;
  }
};
struct FlowNet {
  vector<vector<FlowEdge>> g;
  int n;
  FlowNet(int n) : g(vector<FlowEdge>()) {
    this->n = n;
  }
  void addEdge(int u, int v, double c) {
    FlowEdge e;
    e.u = u;
    e.v = v;
    e.c = c;
    e.f = .0;
    g[u].push_back(e);
    g[v].push_back(e);
  }
};
class MaxFlow {
  private:
    NetFlow &net;
    int s, t; // src, target
    vector<FlowEdge*> edgeTo;
    queue<int> q;
    list<int> edgeTo_changed;

    void search_clear();
    bool search();
  public:
    MaxFlow(NetFlow &net, int s, int t) {
      this->net = net;
      this->s = s;
      this->t = t;
      this->edgeTo = vector<FlowEdge*>(net.n);
      search_clear();
    }
  private:
    void search_clear() {
      q.clear();
      for (auto it = edgeTo_changed.begin(); it != edgeTo_changed.end(); ++it)
        edgeTo[*it] = nullptr;
      edgeTo_changed.clear();

      while (search()) {
        double bottleneck = -1;
        for (int x = t; x != s; x = edgeTo[x].other(x)) {
          if (edgeTo[x].capTo(x) < bottleneck || bottleneck = -1) {
            bottleneck = edgeTo[x].capTo(x);
          }
        }
      }
    }
    bool search() {
      q.push_back(s);
      
      while(q.size() != 0) {
        int u = q.pop_front();
        if (u == t) {
          search_clear();
          return true;
        }
        vector<FlowEdge>& adj = net.g[u];
        for (auto it = adj.begin(); it != adj.end(); ++it) {
          FlowEdge e = *it;
          int v = e.other(w);
          double c = e.capTo(v);
          if (c > 0 && edgeTo[v] == nullptr) {
            edgeTo[v] = e;
            edgeTo_changed.push_back(v);
            q.push_back(v);
          }
        }
      }
    }
};
int main() {
  return 0;
}
