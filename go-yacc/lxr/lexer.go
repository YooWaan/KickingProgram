package main

import (
	"fmt"
)

/*
 * Statement =
 * CondExpr = Cond (ConcatOp CondExpr)
 * Cond = Field Op Value
 * ConcatOp = '&' | '|'
 * Field = FieldGruop FieldName
 * FieldGroup = "deployment." | "meta." | "tag"
 * FieldName = Letter
 * Value = String | Num | Bool | StringArray | NumArray
 * Op = '=' | '!=' | '>' | '>=' | '<' | '<=' | '::'
 *
 *
 */

const (
	tokenErr = iota
	tokenEOF
	tokenOpEq
	tokenOpNotEq
	tokenOpLt
	tokenOpLe
	tokenOpGt
	tokenOpGe
	tokenAnd
	tokenOr
	tokenLetters
	tokenString
	tokenNumber
)

type (
	Lexer struct {
		input   string
		pos     int
		readPos int
		ch      byte
	}

	Token struct {
		Type int
		Text string
	}

	Parser struct {
		lex *Lexer
	}

	Node interface {
		node()
	}

	Value interface {
		Node
		value()
	}

	Expr interface {
		Node
		expr()
	}

	Cond struct {
		Field string
		Op    int
		Val   Value
	}

	Statement struct {
		Left  Expr
		Next  Op
		Right Expr
	}

	ValueStr struct {
		Val string
	}

	ValueNum struct {
		Val float64
	}

	ValueBool struct {
		Val bool
	}
)

func (c *Cond) node()
func (c *Cond) expr()

func (c *Statement) node()
func (c *Statement) expr()

func (c *ValueStr) node()
func (c *ValueStr) expr()

func (c *ValueNum) node()
func (c *ValueNum) expr()

func (c *ValueBool) node()
func (c *ValueBool) expr()

func main() {
	line := `aaa = "bbb" | cccc=123.3 & dd = -0.3`
	l := New(line)

	for {
		tk := l.next()
		if tk.Type == tokenEOF {
			return
		}
		fmt.Printf("%d[%s]\n", tk.Type, tk.Text)
	}
}

func Parse(i string) Statement {
	p := &Parser{lex: New(i)}
	return p.Parse()
}

func (p *Parser) Parse() (*Statement, error) {
	var stmt *Statement
	for {
		cond, tkType, err := parseCond()
		if err != nil {
			return nil, err
		}
		if stmt == nil {
			stmt = &Statement{
				Left: cond
			}
		} else {
			stmt.Right = cond
			if tkType == tokenAnd || tyType == tokenOr {
				stmt = &Statement{ Left: stmt, Op: tkType }
			}
		}

		if !(tkType == tokenAnd || tyType == tokenOr) {
			break
		}
	}
	return stmt, nil
}

func (p *Parser) xxx(op int) {
	if op == 0 {

	} else if op == And {

	} else if op == Or {

	}
}

func (p *Parse) parseCond() (*Cond, int, error) {
	cond := &Cond{}

	tk := p.lex.next()
	if tk.Type == tokenLetters {

	} else {
		return nil, tokenErr, err
	}
	tk = p.lex.next()
	if tk.Type >= tokenOpEq && tk.Type <= tokenOpGe {

	} else {
		return nil, tokenErr, err
	}

	tk = p.lex.next()
	if tk.Type == tokenString {
	} else if tk.Type == tokenNumber {

	} else {
		return nil, tokenErr, err
	}

	tk = p.lex.next()
	return cond, tk.Type, nil
}

func New(i string) *Lexer {
	l := &Lexer{input: i}
	l.readCh()
	return l
}

func (l *Lexer) readCh() {
	l.ch = l.peekCh()
	l.pos = l.readPos
	l.readPos++
}

func (l *Lexer) peekCh() byte {
	if l.readPos >= len(l.input) {
		return 0
	}
	return l.input[l.readPos]
}

func (l *Lexer) next() Token {
	var tok Token
	l.skipWhitespace()

	switch l.ch {
	case '=':
		tok = l.tk(tokenOpEq, string(l.ch))
	case '&':
		tok = l.tk(tokenAnd, string(l.ch))
	case '|':
		tok = l.tk(tokenOr, string(l.ch))
	case '!':
		tok = l.opOrWithEq(tokenErr, tokenOpNotEq)
	case '>':
		tok = l.opOrWithEq(tokenOpGt, tokenOpGe)
	case '<':
		tok = l.opOrWithEq(tokenOpLt, tokenOpLe)
	case '"':
		tok = l.readStr()
	case '-':
		tok = l.readNum(true)
	case 0:
		tok = l.tk(tokenEOF, "")
	default:
		if l.isLetter(l.ch) {
			return l.readLetters()
		} else if l.isDigit(l.ch) {
			return l.readNum(false)
		} else {
			tok = l.tk(tokenErr, string(l.ch))
		}
	}

	l.readCh()

	return tok
}

func (l *Lexer) opOrWithEq(single, eq int) Token {
	if l.peekCh() == '=' {
		ch := l.ch
		l.readCh()
		return l.tk(eq, string(ch)+string(l.ch))
	}
	return l.tk(single, string(l.ch))
}

func (l *Lexer) skipWhitespace() {
	for l.isSpace() {
		l.readCh()
	}
}

func (l *Lexer) isSpace() bool { return l.ch == ' ' || l.ch == '\t' || l.ch == '\n' || l.ch == '\r' }

func (l *Lexer) isLetter(ch byte) bool {
	return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_' || ch == '.'
}

func (l *Lexer) isDigit(ch byte) bool {
	return ('0' <= ch && ch <= '9') || ch == '.'
}

func (l *Lexer) readNum(isMinus bool) Token {
	position := l.pos
	for l.isDigit(l.ch) {
		l.readCh()
	}
	if isMinus {
		return l.tk(tokenNumber, "-"+l.input[position:l.pos])
	}
	return l.tk(tokenNumber, l.input[position:l.pos])
}

func (l *Lexer) readLetters() Token {
	position := l.pos
	for {
		l.readCh()
		if l.isSpace() || !l.isLetter(l.ch) || l.ch == 0 {
			break
		}
	}
	return l.tk(tokenLetters, l.input[position:l.pos])

}

func (l *Lexer) readStr() Token {
	position := l.pos + 1
	for {
		l.readCh()
		if l.ch == '"' || l.ch == 0 {
			break
		}
	}
	return l.tk(tokenString, l.input[position:l.pos])
}

func (l *Lexer) tk(ty int, txt string) Token { return Token{Type: ty, Text: txt} }
