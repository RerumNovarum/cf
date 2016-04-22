n=int(input())
X=dict()
Y=dict()
P=dict()

for i in range(n):
    x, y = [int(_) for _ in input().split()]
    p = (x, y)
    P[p] = P[p]+1 if p in P else 1
    X[x] = X[x]+1 if x in X else 1
    Y[y] = Y[y]+1 if y in Y else 1
C = 0
for x in X:
    c  = X[x] - 1
    C += c*(c+1)//2
for y in Y:
    c = Y[y] - 1
    C += c*(c+1)//2
for p in P:
    if P[p] > 1:
        s = P[p] - 1
        s = s*(s+1)//2
        C -= s
print(C)
