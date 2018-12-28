from pulp import *
import numpy as np


A = np.array([[3,1,2], [1,3,0], [0,2,4]])
c = np.array([150, 200, 300])
b = np.array([60, 36, 48])

(m,n) = A.shape

prob = LpProblem(name='Production', sense=LpMaximize)

x = [LpVariable('x'+str(i+1), lowBound=0) for i in range(n)]

prob += lpDot(c, x)
for i in range(m):
    prob += lpDot(A[i], x) <= b[i], 'ineq' + str(i)

print(prob)

prob.solve()

print(LpStatus[prob.status])
print('Optimal value=', value(prob.objective))

for v in prob.variables():
    print(v.name, '=', v.varValue)
