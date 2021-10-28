%{
package parse
%}



%type<kv> validator
%type<kv> func_expr
%type<kv> condtion_expr
%type<kv>
%type<ctx> vars
%type<args> args
%type<v> arg
%type<nv> var
%type<s> var_name
%type<v> var_value
%type<s> var_type

%union{
  empty  struct{}
  ctx    EvalContext
  kv     Expr
  nv     *NamedVar
  s      string
  args   Args
  v      Value
}

// %token<empty> COLON AND ORÅ@FLOAT 
%token<s> STRING TRUE FALSE INTEGER NIL EQ EEQ NEQ AAND OOR BOOL INT STR

%left ','
%right '='

%%

validator :
   opt_term
   {
      $$ = nil
   }
   | exprs opt_newlines
   {
      $$ = $1
   }


exprs :
   opt_newlines expr
   {
      if l, ok := Explex.(*Lexer); ok {
         l.Append(l)
      }
   }
   | exprs term expr
   {
      if l, ok := Explex.(*Lexer); ok {
         l.Append(l)
      }
   }

expr :
   vars
   {
     $$ = $1
   }
   | func_expr
   {
     $$ = $1
   }
   | '(' exprs ')'
   {
     $$ = NewHands($1, $2)
   }

func_expr:
   func_name '(' args ')'
   {
      $$ = NewFuncExpr($1, $3)
   }

condition_expr:
   op_comp
   {
      $$ = $1
   }
   | op_binary
   {
      $$ = $1
   }

op_comp:
   func_expr EEQ func_expr
   {
      $$ = NewOp(CmpEq, $1, $3)
   }
   | func_expr NEQ func_expr
   {
      $$ = NewOp(CmpNotEq, $1, $3)
   }

op_binary:
    expr AAND expr
    {
    }
    | expr OOR expr
    {
    }


args:
   arg
   {
     $$ = $1
   }
   | args ',' opt_newlines arg
   {
     if len($1) == 0 {
        Explex.Error("syntax error invalid ','")
     }
     $$ = append($1, $4)
   }

arg:
   var_name
   {
     $$ = NewRefVar($1)
   }
   | var_value
   {
     $$ = $1
   }

vars:
   var
   {
     $$ = NewEvalConetxt()
     $$.PutNV($1)
   }
   | vars term var
   {
     $$ = $1
     $$.PutNV($3)
   }

var :
   var_name '=' var_value
   {
     $$ = NewNamedVar($1, $3)
   }


var_name :
   STRING
   {
     $$ = $1
   }

var_value :
   NIL
   {
     $$ = NilValue
   }
   | TRUE
   {
     $$ = &BoolVar{Bool:true}
   }
   | FALSE
   {
     $$ = &BoolVar{Bool:false}
   }
   | INTEGER
   {
     $$ = NewInteger($1)
   }
   | '"' STRING '"'
   {
     $$ = NewStr($2)
   }

var_type:
   BOOL
   {
     $$ = $1
   }
   INT
   {
     $$ = $1
   }
   STR
   {
     $$ = $1
   }


opt_newlines:
   /* nothing */
   | newlines

newlines : 
  newline
  | newlines newline

newline : '\n'

term :
	';' newlines
	| newlines
	| ';'

%%
