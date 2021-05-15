


https://about.sourcegraph.com/go/gophercon-2018-how-to-write-a-parser-in-go/

java flex
https://github.com/jflex-de/jflex


rust
https://docs.rs/esparse/0.1.0/esparse/lex/index.html


BNF



```
Program ::= <Rule>
    | <Rule> <Program>
    | <Var> <Program>

Vars :: = <VarConst> : <Var list>

Var list :: = <Var>
    | <Var> <Var list>

Var :: = <Arg>
    | <Variable>


Rule ::= <Name> <Condtions>


Conditions ::= <primitive>
    | <condition> <op> <condition>
    | <Function>

```


has
at

eq
ne
not


and
or



```
vars:
  var-name: 1
  var-name: "ssss"
  var-name: [1,2,3, "a", "a"]
rules:
  rule-name:
     true
  rule-name:
     num eq var
  rule-name:
     (num eq var
     and
     num eq var)
     or     
     (num eq var
     and
     num eq var)
  rule-name:

```

goyacc -p Exp -o parse/lang.go parse/lang.y

vars (
     var-name(nil|123|""|true/false|context.productID)

     companyid = nil
     product_id = 100
     no_option = true
     chk_id = context.userID
)


  productAdmin()
  productViewer()
  companyAdmin($var-name)
  
  or {
    companyBelong() or unitAdmin() or unitBelong()

  
  }

  isSysadmin
  isCompanyUserAdmin
  isOwn

  // related product
  shema(role)



  userID == ?
  companyID == ?
  unitID == ?


```
vars:
 args: companyid

rule: aaaa

 pricipal.id
 pricipal.name
 pricipal.email # company, group, product empty


 company.admin
 company.belong
 group.admin
 group.belong


 user.id
 user.name
 user.c

 company/group/product.{id,name}

 // explicit
 contract.of(user.companyid).plan.has(productid)
 // implicit
 contract.plan.of(productid).option(optionid)

```

