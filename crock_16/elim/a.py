import heapq

HOLE = 'X'

def dist(x, y):
    d = 0
    for i in range(len(x)):
        if x[i] != y[i]:
            j = y.index(x[i])
            if {i, j} in [{0,1}, {1,3}, {3, 2}, {2, 0}]: d += 1
            else: d += 2
    return d
def swapped(a, i, j):
    t = list(a)
    t[i], t[j] = t[j], t[i]
    return tuple(t)
def back(x):
    i = x.index(HOLE)
    if   i == 0: return swapped(x, 0, 1)
    elif i == 1: return swapped(x, 1, 3)
    elif i == 2: return swapped(x, 0, 2)
    elif i == 3: return swapped(x, 2, 3)
def forth(x):
    i = x.index(HOLE)
    if   i == 0: return swapped(x, 0, 2)
    elif i == 1: return swapped(x, 1, 0)
    elif i == 2: return swapped(x, 2, 3)
    elif i == 3: return swapped(x, 3, 1)
def bbfl(x, y):
    src = x
    dst = y
    twin = swapped(x, 2, 3) if src.index(HOLE) < 2 else swapped(x, 0, 1)
    l, lt = None, None
    q = [(0, 0, 0, src), (0, 0, 1, twin)]
    while q:
        p, d, t, x = heapq.heappop(q)
        if x == dst: return d if t==0 else False
        f, b = forth(x), back(x)
        d += 1
        last = l if t==0 else lt
        for w in (f, b):
            if w == last: continue
            h = dist(w, dst)
            p = d+h
            heapq.heappush(q, (p, d, t, w) )
        if t==0: l  = x
        else:    lt = x
    return False

elsie  = tuple(input()+input())
bessie = tuple(input()+input())

print('NO' if bbfl(elsie, bessie) is False else 'YES')
