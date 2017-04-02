#
#
#

COLOR_FIGURE = False

import numpy as np
from matplotlib import pyplot as plt
from sklearn.datasets import load_iris
from thd import learn_model, apply_model, accuracy

data = load_iris()

features = data['data']
feature_names = data['feature_names']
species = data['target_names'][data['target']]

target = data['target']
target_names = data['target_names']


def simple_threshold():
    labels = target_names[target]
    plength = features[:, 2]
    is_setosa = (labels == 'setosa')
    print('Maximum of setosa: {0}.'.format(plength[is_setosa].max()))
    print('Minimum of others: {0}.'.format(plength[~is_setosa].min()))


def fig2():
    setosa = (species == 'setosa')
    features = features[~setosa]
    species = species[~setosa]
    virginica = species == 'virginica'

    t = 1.75
    p0, p1 = 3, 2

    if COLOR_FIGURE:
        area1c = (1., .8, .8)
        area2c = (.8, .8, 1.)
    else:
        area1c = (1., 1, 1)
        area2c = (.7, .7, .7)


    x0, x1 = [features[:, p0].min() * .9, features[:, p0].max() * 1.1]
    y0, y1 = [features[:, p1].min() * .9, features[:, p1].max() * 1.1]

    plt.fill_between([t, x1], [y0, y0], [y1, y1], color=area2c)
    plt.fill_between([x0, t], [y0, y0], [y1, y1], color=area1c)

    plt.plot([t, t], [y0, y1], 'k--', lw=2)
    plt.plot([t - .1, t - .1], [y0, y1], 'k:', lw=2)
    plt.scatter(features[virginica, p0],
                features[virginica, p1], c='b', marker='o')
    plt.scatter(features[~virginica, p0],
                features[~virginica, p1], c='r', marker='x')
    plt.ylim(y0, y1)
    plt.xlim(x0, x1)
    plt.xlabel(feature_names[p0])
    plt.ylabel(feature_names[p1])
    plt.savefig('./fig2.png')


def heldout():

    labels = data['target_names'][data['target']]
    setosa = (labels == 'setosa')
    fs = features[~setosa]
    labels = labels[~setosa]
    virginica = (labels == 'virginica')

    testing = np.tile([True, False], 50)
    training = ~testing

    model = learn_model(fs[training], virginica[training])
    train_error = accuracy(fs[training], virginica[training], model)
    test_error = accuracy(fs[testing], virginica[testing], model)

    print('''\
Training error was {0:.1%}.
Testing error was {1:.1%} (N = {2}).
'''.format(train_error, test_error, testing.sum()))



#simple_threshold()

heldout()
