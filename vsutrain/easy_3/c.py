n, p, T = input().split();
n, p, T = int(n), float(p), int(T)

# pr[t][c] = \pr\{ 'c' persons are staying on escalator at the moment 't' \}
pr = [[0.0 for c in range(n+1)] for t in range(T+1)]

pr[0][0] = 1.0

for t in range(T):
    for c in range(n+1):
        if not c==n:                        # given 'c' persons already on escalator at the moment 't'
            pr[t+1][c+1] += pr[t][c]*p      # each another person in queue can either proceed to escalator
            pr[t+1][c]   += pr[t][c]*(1-p)  # or stay in queue
        else: pr[t+1][c] += pr[t][c]        # or it could happen there's no people on the queue
# print("\n".join("\t".join(str(x) for x in pr[t]) for t in range(T+1)))
# print()
E = 0
for c in range(n+1):
    E += c*pr[T][c]
print(E)
