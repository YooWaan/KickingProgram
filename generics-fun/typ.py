
from typing import Protocol, TypeVar, Generic, T

class Var(Protocol):
    def value(self) -> str:
        ...

class Hex(Var):
    def hex(self) -> str:
        ...

class ToCase(Var):
    def upper(self) -> str:
        ...
    def lower(self) -> str:
        ...


class Value(Generic[T]):

    def __init__(self, value: T):
        self._value = value

    def value(self) -> str:
        return f'{self._value}'


class VInt(Value[int]):
    def __init__(self, value: int):
        super().__init__(value)
    def hex(self) -> str:
        return hex(self._value)


class VStr(Value[str]):
    def __init__(self, value: str):
        super().__init__(value)
    def __len__(self):
        return 999
    def __str__(self):
        return self.value()


sv = Value[str]('hello')
iv = Value[int](100)

i = VInt(100)
s = VStr('Hello')

print(f'{sv.value()} , {iv.value()}')
print(f'{i.value()} {i.hex()} , {s.value()}')


ss = "1000"
print(f'{ss} = {type(ss)}')
ss = 100
print(f'{ss} = {type(ss)}')


ll = [1,2,3]
ss = "Hello"

print(f'{ll} = {len(ss)}, {ss} = {len(ss)}, {s} = {len(s)}')

