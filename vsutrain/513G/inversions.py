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
                    for j in range(i+1, n):
                        pxy = 2/n/(n+1)
                        d = 0
                        if y < i or j < x or i < x <= y < j:
                            # p[i][j][m+1] += pxy*p[i][j][m]
                            d = p[i][j][m]
                        elif j == x:# < y:
                            d = p[i][y][m]
                        elif y==i:# x < y == i:
                            d = p[x][j][m]
                        elif x <= i < j <= y:
                            # p[x+y-i][x+y-j][m+1] += pxy*(1-p[i][j][m])
                            # p[i][j][m+1] += pxy*(1-p[x+y-i][x+y-j][m])
                            d = (1-p[x+y-i][x+y-j][m])
                        elif x <= i < y <= j:
                            # which shitty item i'th's gonna fucking swap with?
                            # probably it's (x+y-i)
                            # i and j will swap the order
                            # if fucking what?
                            # p[x+y-i][j][m+1] += p[i][j][m]
                            # p[i][j][m+1] += pxy*p[x+y-i][j][m]
                            d = p[x+y-i][j][m]
                        elif i <= x < j <= y:
                            # i'm starting freaking out
                            # ? p[i][x+y-j][m+1] += p[i][j][m]
                            # i-x-j-y --> i-y-j-x
                            # 
                            # p[i][j][m+1] += pxy*p[i][x+y-j][m]
                            d = p[i][x+y-j][m]
                        print('x=%d y=%d i=%d j=%d d=%f'%(x,y,i,j,d*pxy))
                        p[i][j][m+1] += pxy*d
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
