s = input()
t = input()

ss, tt = {}, {}
for c in s:
    ss[c] = ss[c]+1 if c in ss else 1
for c in t:
    tt[c] = tt[c]+1 if c in tt else 1

yay, whoops = 0, 0
for c in ss:
    if c in tt:
        d =  min(ss[c], tt[c])
        yay   += d
        ss[c] -= d
        tt[c] -= d
for c in ss:
    if ss[c]>0 and c.swapcase() in tt:
        d = min(ss[c], tt[c.swapcase()])
        whoops += d
        ss[c]  -= d
        tt[c.swapcase()] -= d
print("%d %d"%(yay, whoops))
