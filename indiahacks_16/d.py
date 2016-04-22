import collections

n, m, x = [int(x) for x in input().split()]
g = dict()
for i in range(n): g[i] = []
# edge ::= [src, dst, cap, flow]
for i in range(m):
    s, d, c = [int(x) for x in input().split()]
    s, d = s-1, d-1
    e = [s, d, c, .0]
    g[s].append( e )
    g[d].append( e )
def find_max(w):
    if w == 0: return 0
    def cap(e): return int(e[2]/w)
    def res_cap_to(e, v):
        c = cap(e)
        f = e[3]
        if e[0] == v: return f
        return c - f
    def add_res_to(e, to, f):
        if e[0] == to: e[3] -= f
        else:          e[3] += f
    def other(e, v):
        if v == e[0]: return e[1]
        return e[0]
    def find_aug():
        q = collections.deque()
        q.append(0)
        edge_to = dict()
        while q and not (n-1 in edge_to):
            s = q.popleft()
            for e in g[s]:
                src, dst, cap, flow = e
                src = s
                dst = other(e, s)
                c = res_cap_to(e, dst)
                if c > 0 and not dst in edge_to:
                    edge_to[dst] = e
                    q.append(dst)
        if not n-1 in edge_to: return False
        return edge_to
    while True:
        aug = find_aug()
        if not aug: break
        neck = 10**6
        v = n-1
        while v != 0:
            e = edge_to=[v]
            neck = min(neck, res_cap_to(e, v))
            v = other(e, v)
        v = n-1
        while v != 0:
            e = edge_to[v]
            add_res_to(e, v, neck)
            v = other(e, v)
    total = 0
    for e in g[0]:
        total += e[3]
    for i in range(n):
        for e in g[i]: e[3] = 0
    total *= w
    return total
    
def search():
    l, r = 0, 10**6
    stop = 21
    sup  = 0
    while (l < r) and stop > 0:
        stop -= 1
        mid = (l+r)/2
        t   = find_max(mid)
        if t == 0: r = mid - 1
        elif t < sup: r = mid - 1
        else: l = mid + 1
        sup = max(sup, t)
    return sup
print(search())
