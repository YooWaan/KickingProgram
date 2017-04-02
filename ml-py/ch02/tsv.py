# -*- coding: utf-8 -*-

import milksets.iris
import milksets.seeds


def save_as_tsv(fname, module):
    features, labels = module.load()
    nlabels = [module.label_names[ell] for ell in labels]

    with open(fname, 'w') as ofile:
    
        for f, n in zip(features, nlabels):
            print("\t".join(map(str, f)) + [n])
            


    """
            ss = "\t".join(map(str, f) + [n])
            print(ss)
            print(ss, file=ofile)
    """

save_as_tsv('iris.tsv', milksets.iris)
save_as_tsv('seeds.tsv', milksets.seeds)
