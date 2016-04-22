i, j = [int(x) for x in input().split()]

# original shitty greedy solution is now working
T=0
while i>0 and j>0:
    # because of this
    # and i was wondering during whole contest
    # 'why the hell it doesn't work?'
    # wasted an hour for task A
    # and haven't even looked at others tasks
    if i == 1 and j == 1: 
        break; # again, never attend codeforces if you're drunk
    i, j = min(i, j), max(i, j)
    t = j//2
    if not t>0:
        T += i
        break
    if 2*t == j and t>1: t -= 1
    i += t
    j -= 2*t
    T += t
print(T)
