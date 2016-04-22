n, k = [int(x) for x in input().split()]
assert k < n
rooms = [i for i,c in enumerate(input()) if c == '0']
inf = 2**31
for i in range(len(rooms)):
    if i+k >= len(rooms): break
    mid = (i+k)//2
    inf = min(inf, max(rooms[mid]-rooms[i], rooms[i+k]-rooms[mid]))
print(inf)
