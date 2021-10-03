package parse

import (
	//"fmt"
	//"strings"
	"text/scanner"
	"strconv"
	"errors"
	"reflect"
)

const (
	VarTypeUnknown = iota
	VarTypeInt
	VarTypeStr
	VarTypeBool


	CmpEq = 0
	CmpLt = 1
	CmpNotMatch = -1
	CmpGt = -2
)

type (
	Expr interface {
		Eval() bool
	}

	KeyValue struct {
		Key   string
		Value string
	}

	Value interface {
		Val() interface{}
		Compare(Value) int
	}

  FuncExpr struct {
  	 Args map[string]Value
  }

	EvalContext struct {
		Variables map[string]Value
	}

	Hands struct {
		Left  Expr
		Rgiht Expr
	}

	ValueHands struct {
		Left Value
		Right Value
	}
	
	EvalEqExpr struct {
		Hands
	}

	OpExpr struct {
		Left Value
		Right Value
		Op    int
	}

	NamedVar struct {
		Name string
		Value Value
	}
	
	StrVar struct {
		String string
	}

	IntVar struct {
		Int64 int64
	}

	BoolVar struct {
		Bool bool
	}

	NilVar struct {}
	
	Lexer struct {
		scanner.Scanner
		Value KeyValue
		EvalContext EvalContext
		Expr Expr
		Err error
	}
)

func NewHands(l, r Expr) Hands {
	return Hands{Left: l, Right: r}
}

func (h Hands) HasBoth() bool {
	return h.Left != nil && h.Right != nil
}

func (h Hands) EvalHand() bool {
	if h.Left == nil && h.Right == nil {
		return false
	}
	if h.Right != nil {
		return h.Right.Eval()
	}
	return h.Left.Eval()
}


func (ec *EvalContext) Put(n string, v Value) {
	if ec.Variables == nil {
		ec.Variables = map[string]Value{}
	}
	ec.Variables[n] = v
}

func (ec *EvalContext) PutNV(nv *NamedVar) {
	ec.Put(nv.Name, nv)
}


func (epr *EvalExpr) Eval() bool {
	if !epr.HasBoth() {
		return epr.EvalHand()
	}
	return epr.Left.Eval() && epr.Right.Eval()
}

func (op *OpExpr) Eval() bool {
	

}

func NewNamedVar(n string, v Value) *NamedVar {
	return  &NamedVar{Name:n, Value: v}
}

func NewInteger(s string) Value { 
	n, _ := strconv.ParseInt(s, 10, 64)
	return &IntVar{Int64: n}
}


func (nb *NamedVar) Val() interface{} { return nb.Value.Val() }
func (nb *NamedVar) Compare(v Value) int {
	if v == nil {
		return false
	}
	if nb.Value == nil {
		return false
	}
	
	if nv, ok := v.(*NamedVar); ok {
		return nb.Value.Compare(nv.Value)
	}
	return nb.Value.Compare(v)
}


func (b *BoolVar) Val() interface{} { return b.Bool }

func (b *BoolVar) Compare(v Value) bool {
	if nb, ok := v.(*BoolVar); ok {
		if b.Bool == nb.Bool {
			return CmpEq
		}
		if b.Bool == true {
			return GmpGt
		}
		// nb.Bool == true
		return CmpLt
	}
	return CmpNotMatch
}

func (i *IntVar) Val() interface{} { return i.Int64 }

func (i *IntVar) Compare(v Value) bool {
	if iv, ok := v.(*IntVar); ok {
		if i.Int64 == iv.Int64 {
			return CmpEq
		}
		if i.Int64 > iv.Int64 {
			return CmpGt
		}
		return CmpLt
	}
	return CmpNotMatch
}

func (n *NilVar) Val() interface{} { return nil }

func (n *NilVar) Compare(v Value) bool {
	if nv, ok := v.(*NilVar); ok {
		return CmpEq
	}
	return CmpNotMatch
}



func (l *Lexer) Lex(lval *ExpSymType) int {
	tk, text, err := l.ScanToken(l.Scan(), l.TokenText())

	println("Lex;", tk, ", txt:", text)

	if err != nil {
		l.Err = err
	}
	lval.s = text

	return tk
}

func (l *Lexer) ScanToken(token rune, text string) (int, string, error) {
	if token == scanner.EOF {
		return 0, text, nil
	}

	switch token {
	case scanner.Int:
		return INTEGER, text, nil
	case scanner.RawString, scanner.String, scanner.Ident:
		return STRING, text, nil
		//case scanner.Ident:
	}

	if text == "true" {
		return TRUE, text, nil
	}
	if text == "false" {
		return FALSE, text, nil
	}

	if text == "nil" {
		return NIL, text, nil
	}


	
	

	println("uunknown.....[", text, "], str:", scanner.ScanStrings, ",ch", scanner.ScanChars, "Int:", scanner.ScanInts, ",Float:", scanner.ScanFloats)
	
	return int(token), text, errors.New("unknown syntax")
}


func (l *Lexer) Error(e string) {
	panic(e)
}


/*

expr:
   value COLON value
   {
     println("expr:::", $1 ,", value:::", $3)
  	  $$ = KeyValue{Key: $1, Value: $3}
   }

value: ** **
   {
     println("empty value....")
	  $$ = ""
   }
   | STRING
   {
     println("value....", $1)
  	  $$ = $1
   }

*/
