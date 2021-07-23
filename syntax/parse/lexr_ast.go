package parse

import (
	"fmt"
	"strings"
	"text/scanner"
	"strconv"
	"errrors"
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
		Context EvalContext
	}
)

func (ec *EvalContext) Put(n string, v Value) {
	if ec.Variables == nil {
		ec.Variables = map[strng]Value{}
	}
	ec.Variables[n] = v
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

func (l *Lexer) Lex(lval *ExpSymType) int {
	token, ss := l.Scan(), l.TokenText()
	println(fmt.Sprintf("Lex:[%v] %v %v [%s] %v", token, scanner.Ident, scanner.Comment, ss, scanner.ScanStrings))
	if token == scanner.EOF {
		return 0
	}
	println(fmt.Sprintf("%d; %v>%s", ':', token, ss))
	if token == ':' {
		return COLON
	}
	println(fmt.Sprintf("Lex:[%v] <> %v", token, scanner.String))
	if token == scanner.RawString || token == scanner.Ident || token == scanner.String {
		lval.s = ss
		return STRING
	}
	return int(token)
}

func (l *Lexer) Scan(token rune, text string) (int, string, error) {
	if token == scanner.EOF {
		return 0, text, nil
	}

	switch token {
	case scanner.Int:

	case scanner.RawString, scanner.String:

	case scanner.Ident:

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

	return token, text, errors.New("unknown syntax")
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
