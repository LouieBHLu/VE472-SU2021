import numpy as np


def cholesky_decomposition(a):
    n = a.shape[0]
    c = np.empty([n,n])

    for i in range(n):
        for j in range(i+1, n):
            c[i][j] = 0
    
    for j in range(n):
        c[j][j] = np.sqrt(a[j][j])
        for i in range(j+1, n):
            c[i][j] = a[i][j] / c[j][j]
            for k in range(j, i+1):
                a[i][k] = a[i][k] - c[i][j] * c[k][j]

    return c


def line2matrix(line,n):
    a = np.empty([n,n])
    line = line.split(" ")

    row = 0
    for i in range(len(line)):
        col = i % n
        if i % n == 0 and i != 0:
            row = row + 1
        
        a[row][col] = int(line[i])

    return a


if __name__ == "__main__":
    with open("test/1.txt", "r") as f:
        a = f.readline()
        n1 = int(f.readline())
    f.close()
    
    with open("test/2.txt", "r") as f:
        b = f.readline()
        n2 = int(f.readline())
    f.close()

    with open("test/3.txt", "r") as f:
        c = f.readline()
        n3 = int(f.readline())
    f.close()

    a = line2matrix(a,n1)
    b = line2matrix(b,n2)
    c = line2matrix(c,n3)

    L1 = cholesky_decomposition(a)
    L2 = cholesky_decomposition(b)
    L3 = cholesky_decomposition(c)

    print(L1)
    print(L2)
    print(L3)




    

    