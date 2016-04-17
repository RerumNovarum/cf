#ifndef FLOWNET
#include <iostream>
#include <vector>
#include <deque>
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
  void addFlowTo(int w, double add) {
    if (w == this->v) this->f += add;
    else this->f -= add;
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
  FlowNet(int n) {
    g = vector<vector<FlowEdge>>(n);
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
    FlowNet &net;
    int s, t; // src, target
    vector<FlowEdge*> edgeTo;
    deque<int> q;
    list<int> edgeTo_changed;
    double maxFlow;

    void search_clear();
    bool search();
  public:
    MaxFlow(FlowNet &flownet) : net(flownet) {
      this->edgeTo = vector<FlowEdge*>(net.n);
    }
    double max_flow(int s, int t) {
      this->s = s;
      this->t = t;
      this->maxFlow = 0.0;
      search_clear();

      while (search()) {
        double bottleneck = -1;
        for (int x = t; x != s; x = edgeTo[x]->other(x)) {
          if (edgeTo[x]->capTo(x) < bottleneck || bottleneck == -1) {
            bottleneck = edgeTo[x]->capTo(x);
          }
        }
        for (int x = t; x != s; x = edgeTo[x]->other(x)) {
          edgeTo[x]->addFlowTo(x, bottleneck);
        }
        this->maxFlow += bottleneck;
#ifdef DEBUG
        cout << "found aug path" << endl;
        cout << "+" << bottleneck << endl;
#endif
      }
      return this-> maxFlow;
    }
};
void MaxFlow::search_clear() {
  q.clear();
  for (auto it = edgeTo_changed.begin(); it != edgeTo_changed.end(); ++it)
    edgeTo[*it] = nullptr;
  edgeTo_changed.clear();
}
bool MaxFlow::search() {
  search_clear();
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
      double c = e->capTo(v);
      if (c > 0 && edgeTo[v] == nullptr) {
        edgeTo[v] = e;
        edgeTo_changed.push_back(v);
        q.push_back(v);
      }
    }
  }
}
double optimizeWeight(const FlowNet &network, int s, int t) {
  FlowNet net(network);
  MaxFlow mf(net);
  double l = 0;
  double r = 1e6;
  double f_max = 0;
  for (int i = 0; i < 100; ++i) {
    double f = (l+r)/2;
    for (int i = 0; i < net.n; ++i) {
      for (int j = 0; j < net.g[i].size(); ++j) {
        net.g[i][j].c = network[i][j].c/f; // probably, should use int capacity and floor
        net.g[i][j].f = 0;
      }
    }
    double bears_no = mf.max_flow(f);
    double total_weight = bears_no*f;

  }
}
int main() {
  int n, m, x;
  cin >> n >> m >> x;
  FlowNet net(n);
  for (int i = 0; i < m ; ++i) {
    int a, b, c;
    cin >> a >> b >> c;
    net.addEdge(a, b, (double) c);
  }
  return 0;
}
#endif
