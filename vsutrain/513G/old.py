if __name__ == '__main__':
    n, k = [int(x) for x in input().split()]
    perm = [int(x) for x in input().split()]
    # p[i][j][m] := \Pr{i'th and j'th items swap the order after m steps}
    p = [[[0 for m in range(k+1)] for j in range(n)]  for i in range(n)]
    # let's begin with shitty O(k*n**4) solution and then get reduce to O(k*n**2)
    for m in range(k):
        for x in range(n):
            for y in range(x, n):
                for i in range(n):
                    for j in range(i, n):
                        pxy = 2/n/(n+1)
                        delta  = 0
                        if y < i or j < x or i < x <= y < j:
                            delta = p[i][j][m]
                        elif x <= i < j <= y:
                            # bad fucking guess --- it doesn't work
                            delta = 1-p[x+y-i][x+y-j][m]
                        elif x <= i < y <= j:
                            # which shitty item i'th's gonna fucking swap with?
                            # probably it's (x+y-i)
                            # i and j will swap the order
                            # if ...? fucking what?
                            # if new i'th swapped with j before?
                            # if i'th swapped with j'th before
                            # and old i'th haven't
                            # or new i'th haven't and old fucking had
                            # delta = p[x+y-i][j][m]
                            delta = p[x+y-i][j][m]*(1-p[i][j][m]) + \
                                    (1-p[x+y-i][j][m])*p[i][j][m]
                        elif i <= x < j <= y:
                            # i'm starting freaking out
                            # delta = p[i][x+y-j][m]
                            delta = p[i][x+y-j][m]*(1-p[i][j])
                        p[i][j][m+1] += pxy*delta
    # now let's calculate expectation
    E_inv = 0
    for i in range(n):
        for j in range(i+1, n):
            inv    = perm[i] > perm[j]
            p_swap = p[i][j][k]
            E_inv  += (1-p_swap) if inv else p_swap
    print(E_inv)
    
    for m in range(k+1):
        print('m=%d'%m)
        for i in range(n):
            row = []
            for j in range(n):
                row.append('%.4f'%p[i][j][m])
            print("\t".join(row))
