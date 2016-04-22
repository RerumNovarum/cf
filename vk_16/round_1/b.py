n, d, h = [int(x) for x in input().split()]

if d >= 2*h+1 or (n>(h+1) and d == h):
    print(-1)
    exit(0)

j = 2
n -= 1

lines = []

# phase 1: height
while n > 0 and j-2 < h:
    lines.append('%d %d'%(j-1, j))
    j += 1
    n -= 1
if not j-2 == h:
    print(-1)
    exit(0)
print('\n'.join(lines))

# phase 2
prev = 1
l = h
while n > 0:
    if l == d:
        prev = 1
        l = h
    print('%d %d'%(prev, j))
    prev = j
    j += 1
    l += 1
    n -= 1
