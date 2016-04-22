with open('a.gen.in', encoding='utf8', mode='w') as f:
    f.write('100000\n')
    for i in range(100000):
        f.write(str(i)+' ')
    f.write('\n100000\n')
    s=0
    for i in range(100000):
        s += i
        f.write(str(s)+' ')
