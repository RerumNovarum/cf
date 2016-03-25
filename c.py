RED, BLACK = True, False
def is_red(n):
    return bool(n) and n.clr
class Interval:
    def __init__(self, lo, hi):
        self.lo, self.hi, self.inf, self.sup = [lo, hi]*2
        self.clr = RED
        self.l, self.r = None, None
def contains(x1, y1, x2, y2):
    return x1 <= x2 and y2 <= y1
def intersects(x1, y1, x2, y2):
    return x1 <= x2 <= y1 or x2 <= x1 <= y2
def flip_colors(h):
    h.l.clr = h.clr
    h.r.clr = h.clr
    h.clr   = not h.clr
def fix(h):
    if h:
        h.inf = h.l.inf if h.l else h.lo
        h.sup = h.r.sup if h.r else h.hi
def rot_fix(h, x):
    fix(h)
    fix(x)
def rot_left(h):
    x = h.r
    h.r = x.l
    x.l = h
    rot_fix(h, x)
    return x
def rot_right(h):
    x   = h.l
    h.l = x.r
    x.r = h
    rot_fix(h, x)
    return x
def balance(h):
    if is_red(h.r) and not is_red(h.l):
        h = rot_left(h)
    if is_red(h.l) and is_red(h.l.l):
        h = rot_right(h)
    if is_red(h.l) and is_red(h.r):
        flip_colors(h)
    return h
def cmp(x1, y1, x2, y2):
    if x1 < x2: return -1
    if x2 < x1: return +1
    if y1 >= y2: return -1
    if y2 <  y1: return 1
    return 0
class Intervals:
    def __init__(self):
        self.root = None
    def add(self, lo, hi):
        self.root = self._add(self.root, lo, hi)
        self.root.clr = BLACK
    def _add(self, h, lo, hi):
        if h is None: return Interval(lo, hi)
        c = cmp(lo, hi, h.lo, h.hi)
        if   c < 0: h.l = self._add(h.l, lo, hi)
        elif c > 0: h.r = self._add(h.r, lo, hi)
        else:       return h

        fix(h)
        return balance(h)
    def any_sub(self, lo, hi):
        """any_sub(lo, hi)
checks if tree contains any interval which is subset of given [lo, hi]"""
        return _any_sub(self.root, lo, hi)
    def _any_sub(self, h, lo, hi):
        if h is None: return False
        if not intersects(lo, hi, h.inf, h.sup): return False
        if contains(lo, hi, h.lo, h.hi): return True
        if self._any_sub(h.l, lo, hi): return True
        if self._any_sub(h.r, lo, hi): return True
        return False

if __name__ == '__main__':
    n, m = [int(x) for x in input().split()]
    perm = [int(x) for x in input().split()]
    intervals = Intervals()
    for i in range(m):
        a, b = [int(x) for x in input().split()]
        intervals.add(a, b)

