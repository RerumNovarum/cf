import random

with open('c.in', encoding='utf8', mode='w') as f:
    n, m = [3*10**5]*2
    p = list(range(1, n+1))
    random.shuffle(p)
    f.write('%d %d\n'%(n, m))
    for x in p:
        f.write(str(x))
        f.write(' ')
    for i in range(m):
        x, y = random.randint(1, n), random.randint(1, n)
        f.write('%d %d\n'%(x, y))
    f.flush()
