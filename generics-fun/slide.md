---
marp: true
theme: "white"
transition: "none"
---
<!-- theme: gaia -->

# カタカタ型ターン！！

![](https://naoblog.net/wp-content/uploads/2018/07/%E3%82%AB%E3%82%BF%E3%82%AB%E3%82%BF%E3%82%AB%E3%82%BF-225x300.gif) こんな感じで終わりたかった話

---

<!-- _backgroundColor: aqua -->

## もくじ

- 編集後記
- 概要：型
- Java
- Python
- Typescript
- まとめ

---
<!-- Scoped style -->
<style scoped>
p {
  color: red;
  font-weight:bold;
}
</style>

# 編集後記

Java 型 わかる Go 型 わかる
Typescript 型 JSじゃ無いからギリわかる
Python Typing 型 はぁ？

![width:200px](https://pbs.twimg.com/profile_images/874976180/fukusukephoto_400x400.jpg)



---
<!-- Scoped style -->
<style scoped>
h1 {
  color: blue;
}
</style>

# slide 2

struct typing とは？ は禁句です

調べ終わっていない

![width:200px](https://img.fril.jp/img/319545783/l/900517281.jpg)



---

<!-- Scoped style -->
<style scoped>
* {
  text-align:center;
  font-weight:bold;
}
p {
  font-size: 2em;
}
</style>


![](https://img-cdn.jg.jugem.jp/946/280096/20081003_469732.jpg)

型さん
**申し訳ありませんでした**

---
<!-- Scoped style -->
<style scoped>
* {
  text-align:center;
  font-weight:bold;
}
p {
  font-size: 1em;
}
</style>

加えて

![width:400px](https://pbs.twimg.com/media/Dx1YYQjVYAAC_ZH.jpg)

こういうタイプのプログラマは
今日の話オモロ無いです

---

![bg](https://career-picks.com/wp-content/uploads/2019/03/%E8%A6%81%E7%B4%84.003.jpeg)

---

# 概要

| | |
| - | - |
| [PYPL PopularitY of Programming Language](https://pypl.github.io/PYPL.html) |  ![](./imgs/popular.png) |

ランキングTop３な言語を見てみると
うちで利用している言語とかぶるので
最近どうなのか？軽く見てみるか？

---

#### 概要 （そんな軽い気持ちだった）

型システムは考え方がたくさんあります
その中でも 共変と反変 を見ていきたいと思います

| JA| EN |
| - | - |
| ![width:150px](./imgs/type-ja.png) | ![width:200px](./imgs/type-en.png) |




---


![bg](https://kanji.jitenon.jp/shotai1/490.gif)

---

# 型

[共変性と反変性 (計算機科学)](https://ja.wikipedia.org/wiki/%E5%85%B1%E5%A4%89%E6%80%A7%E3%81%A8%E5%8F%8D%E5%A4%89%E6%80%A7_(%E8%A8%88%E7%AE%97%E6%A9%9F%E7%A7%91%E5%AD%A6))

>・共変 (covariant): 広い型（例：double）から狭い型（例：float）へ変換する(できる)こと。  
> ・反変 (contravariant): 狭い型（例：float）から広い型（例：double）へ変換する(できる)  こと。  
> ・不変 (invariant): 型を変換できないこと。  
> ・双変 (bivariant): 広い型にも狭い型にも変換できること。


---

# Java

---

# Python

---

# Typescript


---

## まとめ

* 不変
  - 参照、追加、更新、削除出来る
  - 幅は無い
* 共変
  - 参照に使える
  - 要素追加は、元の型の保障が出来んよになる
* 反変
  - 参照に使えない。typesafe で無くてもよければで使える
  - 要素追加は、なんでも入れれる（ほぼ）


---
<!-- Scoped style -->
<style scoped>
* {
  text-align:center;
  font-weight:bold;
}
p {
  font-size: 1em;
}
</style>

![bg](https://ehonkan.co.jp/wp/wp-content/uploads/2020/09/446.jpg)

✨
Screenの前のあなた
共変とか反変とかどうでもいいよ
そんな気持ちかと思います

---
<!-- Scoped style -->
<style scoped>
* {
  text-align:center;
  font-weight:bold;
}
p {
  font-size: 1em;
}
</style>

# お土産になるか？わからない考察の話

Consitency の個人的な見解

![](https://www.castlegreen.co.uk/uploads/blog-post-images/1101490-blog-banners-7.png)

[The Circle - why consistency is key](https://www.castlegreen.co.uk/blog/news/2019/9/the-circle-why-consistency-is-key.aspx)
フィットネスの話ぽいです（記事とは関係ありません）

---

# お土産になるか？わからない考察の話


Frontend ⇔ Backend

Developer Experience

```

   +------+             +-------+
   | Front|  <--type--> | Back  |  Communication
   +------+     safe    +-------+  Transport

   .-------.            .-------.
   | Ts    |  Signature |Py/Java|  Coding
   `-------'  Declare   `-------'  Linter
```

---

# お土産になるか？わからない考察の話


Service ⇔ Service

Service Building Experience


```
   +------+             +-------+
   |      |  <--type--> |       |
   +------+     sage    +-------+

   .-------.            .-------.
   | Ts    |CodingStyle |Py/Java|  Skill/Knowledge
   `-------'  Spirit    `-------'  Transfer

```

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