import time

def count(n, m, perm, bads):
    i, j = 0, 0
    c = 0
    while i < n and j < n:
        while j < n:
            v   = perm[j]
            if bads[j] is not None:
                new_i = bads[j] + 1
                dc = (j - i + 1)*(j - i)//2 - (j - new_i + 1)*(j - new_i)//2
#                print('(%d, %d, %d): %d'%(i, new_i - 1, j, dc))
                c += dc
                i = new_i
            j += 1
    j -= 1
    dc = (j - i + 2)*(j - i + 1)//2
#    print('(%d, %d): %d'%(i, j, dc))
    c += dc
    return c

if __name__ == '__main__':
    t = time.time()
    n, m = [int(x) for x in input().split()]
    perm = [int(x) - 1 for x in input().split()]
    inv_perm = [None for i in range(n)]
    bads = [None for i in range(n)]
    for i in range(n): inv_perm[perm[i]] = i
    for i in range(m):
        a, b = [int(x) for x in input().split()]
        a, b = a-1, b-1
        i_a = inv_perm[a]
        i_b = inv_perm[b]
        if i_a < i_b:
            bads[i_b] = max(bads[i_b] or -1, i_a)
        else:
            bads[i_a] = max(bads[i_a] or -1, i_b)
    print('%f sec wasted for input'%(time.time() - t))
    print(bads)
    t = time.time()
    print(count(n, m, perm, bads))
    print('%f sec for counting'%(time.time() - t))
