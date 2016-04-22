"""[Making a String](http://codeforces.com/contest/624/problem/B)"""
n = int(input())
a = sorted([(int(x), i) for i,x in enumerate(input().split())], key=lambda x: x[0])
used = set()
u = [0 for i in range(n)]

for l in a:
    c, i = l
    while c in used and c > 0: c = c-1
    if c > 0:
        used.add(c)
        u[i] = c
print(sum(u))
