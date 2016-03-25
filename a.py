START, END  = 10, 22
OBSERV_TIME = 14
IMPOSSIBLE  = -1

def calc(h1, h2, a, b):
    s = h2 - h1
    v = 12*(a  - b)
    
    if s <= (END - OBSERV_TIME)*a: return 0
    s += -a*(END - OBSERV_TIME) + b*12
    if s <= (END-START)*a: return 1
    if v < 0: return IMPOSSIBLE
    return 1 + s//v


if __name__ == '__main__':
    h1, h2 = [int(x) for x in input().split()]
    a,  b  = [int(x) for x in input().split()]
    print(calc(h1, h2, a, b))
