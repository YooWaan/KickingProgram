package parse

import (
	//"fmt"
	//"strings"
	"text/scanner"
	"strconv"
	"errors"
)

const (
	VarTypeUnknown = iota
	VarTypeInt
	VarTypeStr
	VarTypeBool
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
	}

	EvalContext struct {
		Variables map[string]Value
	}

	EvalExpr struct {
		Left  Expr
		Op    int
		Rgiht Expr
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

func (ec *EvalContext) Put(n string, v Value) {
	if ec.Variables == nil {
		ec.Variables = map[string]Value{}
	}
	ec.Variables[n] = v
}

func (ec *EvalContext) PutNV(nv *NamedVar) {
	ec.Put(nv.Name, nv)
}

func NewNamedVar(n string, v Value) *NamedVar {
	return  &NamedVar{Name:n, Value: v}
}

func NewInteger(s string) Value {
	n, _ := strconv.ParseInt(s, 10, 64)
	return &IntVar{Int64: n}
}


func (nb *NamedVar) Val() interface{} { return nb.Value.Val() }

func (b *BoolVar) Val() interface{} { return b.Bool }

func (i *IntVar) Val() interface{} { return i.Int64 }

func (i *NilVar) Val() interface{} { return nil }


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
