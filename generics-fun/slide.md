---
marp: true
theme: "white"
transition: "none"
---



# slide1


 ![](https://pbs.twimg.com/profile_images/874976180/fukusukephoto_400x400.jpg)

---


# slide 2

struct typing とは？ は禁句です

調べ終わっていない

![](https://img.fril.jp/img/319545783/l/900517281.jpg)



---


![](https://img-cdn.jg.jugem.jp/946/280096/20081003_469732.jpg)



---

## まとめ

不変

- 参照、追加、更新、削除出来る
- 幅は無い

共変

- 参照に使える
- 要素追加は、元の型の保障が出来んよになる


反変

- 参照に使えない。typesafe で無くてもよければで使える
- 要素追加は、なんでも入れれる（ほぼ）



---
https://blog.magrathealabs.com/pythons-covariance-and-contravariance-b422c63f57ac

```python
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

    assert vlt.at(0).value() == "tst"
    #data = [1,2,3] # mypy error
    #ns: EList[int] = EList(data)
    #ns.print()
```