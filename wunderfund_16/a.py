"""[Slime combining](http://codeforces.com/contest/618/problem/A)"""
n = int(input())
a = []
for i in range(0,n):
    a.append(1)
    while len(a)>1 and a[-1] == a[-2]:
        a.pop()
        a.append(a.pop()+1)

print(' '.join(str(j) for j in a))
