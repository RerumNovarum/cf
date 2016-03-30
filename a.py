n, a, b = tuple(int(x) for x in input().split())
b %= n
m = a+b
if m >= 0: m = 1 + (m-1)%n
else: m = n - m

print(m)
