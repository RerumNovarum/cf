n, m, s, d = [int(x) for x in input().split()]
a_raw = sorted([int(x) for x in input().split()], reverse=True)
a = []
def process_obstacle(crd):
    # print('processing %d'%crd)
    if a:
        segment = a.pop()
        # distance you can actually run between obstacles:
        # you start at position (segment[1]+1)
        # and end up at (crd-1)
        dist = crd-segment[1] - 2 
        if dist < s or crd==segment[1]+1:
            a.append((segment[0], crd))
            return
        else: a.append(segment)
    a.append((crd, crd))
while a_raw: process_obstacle(a_raw.pop())
a_raw = None
a = a[::-1]

def freakout():
    print('IMPOSSIBLE')
    exit(0)

x = 0
steps = []
while x != m:
    if a:
        l, r = a.pop()
        if l < x: continue
        if l > m:
            a.clear()
            continue
        dist, length = l-x-1, r-l+2
        # print('x=%d l=%d r=%d dist=%d len=%d'%(x, l, r, dist, length))
        if dist < s:   freakout()
        if length > d: freakout()
        steps.append('RUN %d'%dist)
        steps.append('JUMP %d'%length)
        # x = r+1
        x += dist + length
    else:
        steps.append('RUN %d'%(m-x))
        x = m
print('\n'.join(steps))
