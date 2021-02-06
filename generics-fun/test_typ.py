from typing import List, Generic
from typ import VO, VS, VI, EList, ETList


def test_value() -> None:
    vb: VO = VO(True)
    vs: VS = VS("tst")
    vi: VI = VI(123)

    assert vb.value() == 'True'
    assert vs.value() == 'tst'
    assert vi.value() == '123'

    # duck typing
    #print(len(vb))  # runtime error
    assert len(vs) == 3
    assert len(vi) == 0
    #print(f"vs{len(vs)} vi{len(vi)}")

    # dynamic type
    ss: str = '100000'
    assert ss == '100000'
    ss = 100 # type: ignore
    assert ss == 100  # type: ignore
    ss = "fuxxing type system"

def test_invarient() -> None:
    vb: VO = VO(True)
    vs: VS = VS("tst")
    vi: VI = VI(123)

    # lst: List[VI] = [vb, vs, vi] # mypy error but runnable
    lst: List[VS] = [vs, vi, vi]

    print(lst)
    assert len(lst) == 3

    # lst.extend([vb, vs])
    lst.extend([vi, vs])
    print(lst)

    # lst = [vs, vb, vi] # mypy error but runnable
    # print(lst)


def test_covarient() -> None:
    vb: VO = VO(True)
    vs: VS = VS("tst")
    vi: VI = VI(123)

    lst: List[VS] = [vs, vs, vi]
    print(lst)

    vlt: EList[VS] = EList(lst)
    vlt.print()
    vlt.extend([vs, vi])
    # vlt.extend([vb]) # mypy error but runnable
    vlt.print()

    assert len(vlt) == 5
    assert vlt.at(0).value() == "tst"
    #data = [1,2,3] # mypy error
    #ns: EList[int] = EList(data)
    #ns.print()


def test_contravarient() -> None:
    vb: VO = VO(True)
    vs: VS = VS("tst")
    vi: VI = VI(123)

    lst: List[object] = [vb, vi, vs]
    print(lst)

    vlt: ETList[object] = ETList(lst)
    vlt.print()
    vlt.extend([vi])
    vlt.extend([vb, vs])
    vlt.extend([{"huga":"foo"}])
    vlt.print()

    print(type(vlt._value))
    #assert vlt._value[len(vlt)-1]["huga"] == "foo"