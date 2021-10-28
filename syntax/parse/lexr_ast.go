package parse

import (
	"errors"
	"fmt"
	"strings"
	//"reflect"
	"strconv"
	"text/scanner"
)

const (
	VarTypeUnknown = iota
	VarTypeInt
	VarTypeStr
	VarTypeBool

	OpUnknown = 0
	OpAnd     = 1
	OpOr      = 2

	CmpEq       = 0
	CmpLt       = 1
	CmpLe       = 2
	CmpNotMatch = 3
	CmpNotEq    = -1
	CmpGt       = -2
	CmpGe       = -3
)

var opToken = map[string]int{
	"==": EEQ,
	"!=": NEQ,
	"&&": AAND,
	"||": OOR,
}

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

	Args []Value

	EvalContext struct {
		Variables map[string]Value
	}

	Hands struct {
		Left  Expr
		Right Expr
		Op    int
	}

	ValueHands struct {
		Left  Value
		Right Value
	}

	EvalEqExpr struct {
		Hands
	}

	OpExpr struct {
		Left  Value
		Right Value
		Op    int
	}

	NamedVar struct {
		Name  string
		Value Value
		IsRef bool
	}

	NamedVars []NamedVar

	StrVar struct {
		String string
	}

	IntVar struct {
		Int64 int64
	}

	BoolVar struct {
		Bool bool
	}

	NilVar struct{}

	FuncExpr struct {
		Name string
		Args Args
	}

	Lexer struct {
		scanner.Scanner
		Value       KeyValue
		EvalContext EvalContext
		Expr        Expr
		Err         error
	}
)

var (
	NilValue = &NilVar{}
)

func NewHands(l, r Expr) *Hands {
	return &Hands{Left: l, Right: r}
}

func (h Hands) HasBoth() bool {
	return h.Left != nil && h.Right != nil
}

func (h Hands) EvalHand() bool {
	if h.Left == nil && h.Right == nil {
		return false
	}
	if h.Left == nil {
		return h.Right.Eval()
	}
	if h.Right == nil {
		return h.Left.Eval()
	}
	if h.Op == OpAnd {
		return h.Right.Eval() == h.Left.Eval()
	}
	if h.Op == OpAnd {
		return h.Right.Eval() || h.Left.Eval()
	}
	return false
}

func NewEvalContext() *EvanContext {
	return &EvalContext{Variables: map[string]Value{}}
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

func (ec *EvanContext) Merge(ec *EvanContext) {
	if ec == nil {
		return
	}
	for k, v := range ec.Variables {
		ec.Variables[k] = v
	}
}

func (ec *EvalContext) String() string {
	var sb strings.Builder

	for k, v := range ec.Variables {
		sb.WriteString(k)
		sb.WriteString("=")
		sb.WriteString(fmt.Sprintf("%v", v.Val()))
		sb.WriteString("\n")
	}

	return sb.String()
}

func (epr *EvalEqExpr) Eval() bool {
	if !epr.HasBoth() {
		return epr.EvalHand()
	}
	return epr.Left.Eval() && epr.Right.Eval()
}

func NewOp(op int, l, r Value) *OpExpr {
	return &OpExpr{Left: l, Right: r, Op: op}
}

func (op *OpExpr) Eval() bool {
	cmp := op.Left.Compare(op.Right)

	if op.Op == CmpLe {
		return cmp == CmpEq || cmp == CmpLt || cmp == CmpLe
	}

	if op.Op == CmpGe {
		return cmp == CmpEq || cmp == CmpGt || cmp == CmpGe
	}

	return op.Op == cmp
}

func NewNamedVar(n string, v Value) *NamedVar {
	return &NamedVar{Name: n, Value: v}
}

func NewRefVar(n string) *NamedVar {
	return &NamedVar{Name: n, IsRef: true}
}

func NewInteger(s string) Value {
	n, _ := strconv.ParseInt(s, 10, 64)
	return &IntVar{Int64: n}
}

func NewBool(s string) Value {
	b, _ := strconv.ParseBool(s)
	return &BoolVar{Bool: b}
}

func NewStr(s string) Value {
	return &StrVar{String: s}
}

func (nb *NamedVar) Val() interface{} { return nb.Value.Val() }
func (nb *NamedVar) Compare(v Value) int {
	if v == nil {
		return CmpNotMatch
	}
	if nb.Value == nil {
		return CmpNotMatch
	}

	if nv, ok := v.(*NamedVar); ok {
		return nb.Value.Compare(nv.Value)
	}
	return nb.Value.Compare(v)
}

func (ns NamedVars) Map() map[string]Value {
	ret := map[string]Value{}
	for _, n := range ns {
		ret[n.Name] = n.Value
	}
	return ret
}

func (b *BoolVar) Val() interface{} { return b.Bool }

func (b *BoolVar) Compare(v Value) int {
	if nb, ok := v.(*BoolVar); ok {
		if b.Bool == nb.Bool {
			return CmpEq
		}
		if b.Bool == true {
			return CmpGt
		}
		// nb.Bool == true
		return CmpLt
	}
	return CmpNotMatch
}

func (s *StrVar) Val() interface{} { return s.String }
func (s *StrVar) Compare(v Value) int {
	if ns, ok := v.(*StrVar); ok {
		if s.String == ns.String {
			return CmpEq
		}
		if s.String > ns.String {
			return CmpGt
		}
		// nb.Bool == true
		return CmpLt
	}
	return CmpNotMatch
}

func (i *IntVar) Val() interface{} { return i.Int64 }

func (i *IntVar) Compare(v Value) int {
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

func (n *NilVar) Compare(v Value) int {
	if _, ok := v.(*NilVar); ok {
		return CmpEq
	}
	return CmpNotMatch
}

func NewFuncExpr(name string, args Args) Expr {
	return &FuncExpr{
		Name: name,
		Args: args,
	}
}

func (fe *FuncExpr) Eval() bool {
	return true
}

func NewLexer() *Lexer {
	lex := new(Lexer)
	lex.EvalContext = NewEvalContext()
	return lex
}

func (l *Lexer) Lex(lval *ExpSymType) int {
	print("-------")
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

	switch text {
	case "true":
		return TRUE, text, nil
	case "false":
		return FALSE, text, nil
	case "nil":
		return NIL, text, nil
	case "=":
		return '=', text, nil
	case ";":
		return ';', text, nil
	case "\n":
		return '\n', text, nil
	}

	println("uunknown.....[", text, "], str:", scanner.ScanStrings, ",ch", scanner.ScanChars, "Int:", scanner.ScanInts, ",Float:", scanner.ScanFloats)

	return int(token), text, errors.New("unknown syntax")
}

func (l *Lexer) Error(e string) {
	panic(e)
}

func (l *Lexer) Eval() bool {
	return false
}

func (l *Lexer) Append(e Expr) {
	if e == nil {
		return
	}

	if ec, ok := e.(*EvalContext); ok {
		l.EvalContext.Merge(ec)
		return
	}

	if l.Expr == nil {
		l.Expr = e
	} else {
		l.Expr = NewHands(l.Expr, e)
	}
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
