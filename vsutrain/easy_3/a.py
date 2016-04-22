n = int(input())
a = [int(x) for x in input().split()]
for i in range(1, n): a[i] += a[i-1]

def search(x, l=0, r=n-1):
#    print('[I] q=%d'%x)
    while l<r:
        m = (l+r)//2
        v = a[m]
#        print('[I] l=%d r=%d m=%d'%(l, r, m))
        if x<=v and (m==0 or a[m-1]<x ): return m
        elif x<v:    r=m-1
        else:        l=m+1
    return (l+r)//2

m = int(input())
for q in (int(x) for x in input().split()):
    print(search(q)+1)
