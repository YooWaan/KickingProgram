package parse

import (
	"testing"
)

func TestNilExpr(t *testing.T) {

	ten := NewInteger("10")
	nine := NewInteger("9")

	ptns := []struct {
		Op     int
		Left   Value
		Right  Value
		Expect bool
	}{
		// nil
		{CmpEq, NilValue, NilValue, true},
		{CmpLe, NilValue, NilValue, true},
		{CmpLt, NilValue, NilValue, false},
		{CmpGt, NilValue, NilValue, false},
		{CmpGe, NilValue, NilValue, true},
		{CmpNotEq, NilValue, NilValue, false},

		// integer
		{CmpEq, ten, ten, true},

		{CmpNotEq, ten, nine, false},
	}

	for i, ptn := range ptns {
		exp := NewOp(ptn.Op, ptn.Left, ptn.Right)
		act := exp.Eval()
		if act != ptn.Expect {
			t.Errorf("[%d] %v <> %v", i, ptn.Expect, act)
		}
	}

}
