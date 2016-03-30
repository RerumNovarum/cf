def ccw(a, b, c):
    ab = (b[1]-a[1], b[0]-a[0])
    ac = (c[1]-a[1], c[0]-a[0])
    charge = (ab[0]*ac[1] - ab[1]*ac[0])
    return charge > 0
def countccw(points):
    c = 0
    for i in range(len(points)-2):
        p1, p2, p3 = points[i], points[i+1], points[i+2]
        p4 = points[i+3] if i < len(points)-3 else points[0]
        if ccw(p1, p2, p3) and not ccw(p2, p3, p4): c += 1
    return c
def countcw(points):
    c = 0
    for i in range(len(points)-2):
        p1, p2, p3 = points[i], points[i+1], points[i+2]
        p4 = points[i+3] if i < len(points)-3 else points[0]
        if not ccw(p1, p2, p3) and ccw(p2, p3, p4): c += 1
    return c
if __name__ == '__main__':
    n = int(input())
    points = [ tuple(int(x) for x in input().split()) for i in range(n+1) ]
    if ccw(points[-3], points[-2], points[-1]):
        print(countcw(points))
    else:
        print(countccw(points))
