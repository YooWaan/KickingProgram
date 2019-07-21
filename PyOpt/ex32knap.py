from pulp import *

class KnapsackProblem(object):
    """docstring for KnapsackProblem."""
    def __init__(self, name, capacity, items, costs, weights, zeros=set(), ones=set()):
        self.name = name
        self.capacity = capacity
        self.items = items
        self.costs = costs
        self.weights = weights
        self.zeros = zeros
        self.ones = ones
        self.lb = -100
        self.ub = -100

        ratio = {j:costs[j]/weights[j] for j in items}
        self.sitemList = [k for k, v in sorted(ratio.items(), key=lambda x:x[1], reverse=True)]
        self.xlb = {j:0 for j in self.items}
        self.xub = {j:0 for j in self.items}
        self.bi = None

    def getbounds(self):
        """ Calc the upper lower bounds"""
        for j in self.zeros:
            self.xlb[j] = self.xub[j] = 0
        for j in self.ones:
            self.xlb[j] = self.xub[j] = 1
        if self.capacity < sum(self.weights[j] for j in self.ones):
            self.lb = self.ub = -100
            return 0

        ritems = self.items - self.zeros - self.ones
        sitems = [j for j in self.sitemList if j in ritems]
        cap = self.capacity - sum(self.weights[j] for j in self.ones)
        for j in sitems:
            if self.weights[j] <= cap:
                cap -= self.weights[j]
                self.xlb[j] = self.xub[j] = 1
            else:
                self.xub[j] = cap/self.weights[j]
                self.bi = j
                break
        self.lb = sum(self.costs[j]*self.xlb[j] for j in self.items)
        self.ub = sum(self.costs[j]*self.xub[j] for j in self.items)

    def __str__(self):
        return ('Name = ' + self.name + ', capacity=' +str(self.capacity)+',\n'+
            'items='+str(self.items)+', \n' +
            'costs='+str(self.costs)+', \n'+
            'weights='+str(self.weight)+', \n' +
            'zeros='+str(self.zeros)+', ones='+ str(self.ones)+'\n'+
            'bi='+str(self.bi)+'\n')

def KnapsackProblemSolver(capacity, items, costs, weights):
    from collections import deque
    queue = deque()
    root = KnapsackProblem('KP', capacity=capacity, items = items,
                        costs=costs, weights = weights,
                        zeros = set(), ones = set())
    root.getbounds()
    best = root
    queue.append(root)
    while queue != deque([]):
        p = queue.popleft()
        p.getbounds()
        if p.ub > best.lb:
            if p.lb > best.lb:
                best = p
            if p.ub > p.lb:
                k = p.bi
                p1 = KnapsackProblem(p.name+'+'+str(k),
                            capacity=p.capacity, items = p.items,
                            costs=p.costs, weights=p.weights,
                            zeros = p.zeros, ones = p.ones.union({k}))

                queue.append(p1)
                p2 = KnapsackProblem(p.name+'-'+str(k),
                            capacity=p.capacity, items = p.items,
                            costs=p.costs, weights=p.weights,
                            zeros = p.zeros.union({k}), ones = p.ones)
                queue.append(p2)
    return 'Optimal', best.lb, best.xlb


capacity = 15
items = {1,2,3,4,5}
c = {1:50, 2:40, 3:10, 4:70, 5:55}
w = {1:7, 2:5, 3:1, 4:9, 5:6}

res = KnapsackProblemSolver(capacity=capacity, items=items, costs=c, weights=w)

print('Optimal value = ', res[1])
print('Optimal solution = ', res[2])
