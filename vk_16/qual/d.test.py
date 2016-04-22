import unittest
import subprocess
import tempfile
import time

def run(cmd, input):
    i, o = tempfile.NamedTemporaryFile(), tempfile.NamedTemporaryFile()
    t    = time.time()
    i.write(input.encode('utf8'))
    i.flush()
    i.seek(0)
    p = subprocess.Popen(cmd, stdin=i, stdout=o)
    p.wait()
    o.seek(0)
    output = o.read().decode('utf8')
    o.close()
    i.close()
    return (p.poll(), time.time()-t, output)

class TestD(unittest.TestCase):
    def test_complexity(self):
        n, m, s, d = 2*10**5, 10**9, 10**1, 10**4
        input = '%d %d %d %d\n'%(n,m,s,d)\
                +' '.join(str(2*i+1) for i in range(n))
        exitcode, timing, output = run(['python', 'd.py'], input)
        print('n=2*10**5: %f sec'%timing)
        self.assertLessEqual(timing, 1)

if __name__ == '__main__':
    unittest.main()
