import heapq

def main():
    n, k, q = [int(x) for x in input().split()]
    t = [int(x) for x in input().split()]
    top = []
    top_members = set()

    for j in range(q):
        type, id = [int(x) for x in input().split()]
        id -= 1
        if type == 1:
            heapq.heappush(top, (t[id], id))
            top_members.add(id)
            if len(top) > k:
                id = heapq.heappop(top)[1]
                top_members.remove(id)
        else:
            print('YES' if id in top_members else 'NO')

if __name__ == '__main__': main()
