from typ import VO


def test_value():
    sv = VO(True)

    assert sv.value() == 'True'


def test_invarient():
    ...

def test_covarient():
    ...

def test_contravarient():
    ...
