---
marp: true
theme: "white"
transition: "none"
---



# slide1


---


# slide 2



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
