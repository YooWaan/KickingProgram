# -*- coding: utf-8 -*-

from matplotlib import pyplot as plt
from sklearn.datasets import load_iris
import numpy as np


data = load_iris()

features = data['data']
feature_names = data['feature_names']
target = data['target']
target_names = data['target_names']
labels = target_names[target]


pairs = [(0, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 3)]

for i, (p0, p1) in enumerate(pairs) :
    plt.subplot(2, 3, i + 1)
    for t, marker, c in zip(range(3), '>ox', 'rgb') :

        plt.scatter(features[target == t,0],
                    features[target == t,1],
                    marker==marker,
                    c=c)

        plt.xlabel(feature_names[p0])
        plt.ylabel(feature_names[p1])
        plt.xticks([])
        plt.yticks([])

    plt.savefig('./fig1.png')


