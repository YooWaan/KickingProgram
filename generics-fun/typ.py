
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


sv = Value[str]('hello')
iv = Value[int](100)

i = VInt(100)
s = VStr('Hello')

print(f'{sv.value()} , {iv.value()}')
print(f'{i.value()} {i.hex()} , {s.value()}')

