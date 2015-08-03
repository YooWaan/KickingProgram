# -*- coding: utf-8 -*-

#import ut
from ut import add

import unittest

class TestUt(unittest.TestCase):

    def test_func(self):
        print "callme"
        self.assertTrue(ut.add(2,3) == 5)
