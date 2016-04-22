n = int(input())
a = sorted(int(x) for x in input().split())
m = int(input())
b = sorted(int(x) for x in input().split())

i, j   = 0, 0
ca, cb = 0, 0
a_i, b_j = a[i], b[j]
m = - (2**31)

def score(c, p):
    return c*2 - (p-c)*3
while i<n or j<m:
    if i<n: a_i = a[i]
    if j<m: b_i = b[i]
    x = min(a_i, b_j)
    while i<n and a[i]==x: i += 1
    while j<m and b[j]==x: j += 1
    d_x = score(ca, n) - score(cb, m)
    if d_x > m:
        m = d_x
