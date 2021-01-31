import copy
from typing import Protocol, TypeVar, Generic, TypeVar, List, Union, Any

T = TypeVar('T')

class Var(Protocol):
    def value(self) -> str:
        ...


class Value(Generic[T]):
    def __init__(self, value: T):
        self._value = value
    def value(self) -> str:
        return f'{self._value}'
    def __repr__(self) -> str:
        return self.value()

class VInt(Value[int]):
    def __init__(self, value: int):
        super().__init__(value)
    def hex(self) -> str:
        return hex(self._value)


class VStr(Value[str]):
    def __init__(self, value: str):
        super().__init__(value)
    def __len__(self) -> int:
        return 999
    def __str__(self) -> str:
        return self.value()


class VO:
    def __init__(self, value: Any):
        self._value = value
    def value(self) -> str:
        return f'{self._value}'
    def __repr__(self) -> str:
        return self.value()

class VS(VO):
    def __init__(self, v: str):
        super().__init__(v)
    def __len__(self) -> int:
        return 999
    def __str__(self) -> str:
        return self.value()


class VI(VS):
    def __init__(self, v: int):
        super().__init__(str(v))
    def hex(self) -> str:
        return hex(int(self._value))



# covariant, contravariant, invariant
E = TypeVar('E', VO, VS, covariant=True)
V = TypeVar('V', VS, VI, contravariant=True)
I = TypeVar('I', bound=VI)


def printT(name: str, lst: List[T]) -> None:
    print(f'{name} => {lst}')


def printE(name: str, lst: List[E]) -> None:
    print(f'{name} => {lst}')

def printV(name: str, lst: List[V]) -> None:
    print(f'{name} => {lst}')

"""
sv = Value[bool](False)
iv = Value[float](100.01)
i = VInt(100)
s = VStr('Hello')
"""

sv = VO(True)
iv = VO(100.01)
i = VI(100)
s = VS('Hello')

# dynamic type
ss = "1000"
print(f'{ss} = {type(ss)}')
#ss = 100
#print(f'{ss} = {type(ss)}')

ll = [1,2,3]
ss = "Hello"

print(f'{ll} = {len(ss)}, {ss} = {len(ss)}, {s} = {len(s)}')


vlst: List[Var] = [sv, iv, i, s]
vs: List[VO] = [sv, iv, i, s]
# vbs: List[Value[bool]] = [sv]
# vbs = [sv, iv, i, s]


printT('vlst', vlst)
printT('vs', vs)
#printT('vbs', vbs)

printE('e-vlst', vlst)
printE('e-vs', vs)
#printE('e-vbs', vbs)

printV('v-vlst', vlst)
printV('v-vs', vs)
#printV('v-vbs', vbs)


"""
snum = '100'
inum = 100

print(inum + snum)
print(snum + inum)
"""