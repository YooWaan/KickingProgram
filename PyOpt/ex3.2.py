
items = {1, 2, 3, 4, 5}

c = {1:50, 2:40, 3:10, 4:70, 5:55}
w = {1:7, 2:5, 3:1, 4:9, 5:6}
capacity = 15

ratio = {j: c[j]/w[j] for j in items}

sItems = [key for key, val in
          sorted(ratio.items(), key=lambda x:x[1], reverse=True)]


x={j:0 for j in items}
cap = capacity
for j in sItems:
    if w[j] <= cap:
        cap -= w[j]
        x[j] = 1
    else:
        x[j] = cap/w[j]
        break

print(x)
print('Price = ', sum(c[j]*x[j] for j in sItems))
