#ifndef FLOWNET
#define FLOWNET

#include <iostream>
#include <vector>
#include <deque>
#include <list>

using namespace std;

struct FlowEdge {
  long long c;
  long long f;
  int u, v;

  int other(int w) {
    if (w == this->u) return v;
    return this->u;
  }
  void addFlowTo(int w, double add) {
    if (w == this->v) this->f += add;
    else this->f -= add;
  }
  long long capTo(int w) {
    if (this->v == w) return this->c - this->f;
    else return this->f;
  }
};
struct FlowNet {
  vector< vector<FlowEdge> > g;
  int n;
  explicit FlowNet(int n) : g(vector< vector<FlowEdge> >(n)) {
    this->n = n;
  }
  void addEdge(int u, int v, long long c) {
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
    FlowNet &net;
    vector<FlowEdge*> edgeTo;
    deque<int> q;
    list<int> edgeTo_changed;

    void search_clear() {
      q.clear();
      for (auto it = edgeTo_changed.begin(); it != edgeTo_changed.end(); ++it)
        edgeTo[*it] = nullptr;
      edgeTo_changed.clear();
    }
    bool search(int s, int t) {
      this->search_clear();
      q.push_back(s);

      while(q.size() != 0) {
        int u = q.front();
        q.pop_front();
        if (u == t) {
          return true;
        }
        vector<FlowEdge>& adj = net.g[u];
        for (auto it = adj.begin(); it != adj.end(); ++it) {
          FlowEdge *e = &(*it);
          int v = e->other(u);
          long long c = e->capTo(v);
          if (c > 0 && edgeTo[v] == nullptr) {
            edgeTo[v] = e;
            edgeTo_changed.push_back(v);
            q.push_back(v);
          }
        }
      }
      return false;
    }
  public:
    explicit MaxFlow(FlowNet &flownet) : net(flownet) {
      this->edgeTo = vector<FlowEdge*>(net.n);
      for (int i = 0; i < net.n; ++i) {
        this->edgeTo[i] = nullptr;
      }
    }
    double max_flow(int s, int t) {
      double mf = 0;
      while (search(s, t)) {
        double bottleneck = -1;
        for (int x = t; x != s; x = edgeTo[x]->other(x)) {
          if (edgeTo[x]->capTo(x) < bottleneck || bottleneck == -1) {
            bottleneck = edgeTo[x]->capTo(x);
          }
        }
        for (int x = t; x != s; x = edgeTo[x]->other(x)) {
          edgeTo[x]->addFlowTo(x, bottleneck);
        }
        mf += bottleneck;
      }
      return mf;
    }
};
double optimizeWeight(const FlowNet &network, int x, int s, int t) {
  FlowNet net(network);
  MaxFlow mf(net);
  double l = 0;
  double r = 1e6+1.;
  for (int i = 0; i < 100; ++i) {
    double f = (l+r)/2;
    if (f == 0.0) continue;
    for (int i = 0; i < net.n; ++i) {
      for (int j = 0; j < net.g[i].size(); ++j) {
        net.g[i][j].c = (long long)(network.g[i][j].c/f);
        net.g[i][j].f = 0;
      }
    }
    int bears_no = mf.max_flow(s, t);
    if (bears_no >= x) {
      l = f;
    } else {
      r = f;
    }
  }
  return l*x;
}
int main() {
  ios_base::sync_with_stdio(false);
  cout.precision(15);
  int n, m, x;
  cin >> n >> m >> x;
  FlowNet net(n);
  for (int i = 0; i < m ; ++i) {
    int a, b, c;
    cin >> a >> b >> c;
    net.addEdge(a-1, b-1, c);
  }
  cout << optimizeWeight(net, x, 0, n-1);
  return 0;
}
#endif
