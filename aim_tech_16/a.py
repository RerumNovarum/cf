"""[Save Luke](http://codeforces.com/contest/624/problem/A)"""
d,L,v_1,v_2 = tuple(int(x) for x in input().split())
v = v_1 + v_2
t = (L-d)/v
print(t)
