%{
package parse
%}



%type<kv> validator
%type<ctx> vars
%type<nv> var
%type<s> var_name
%type<v> var_value

%union{
  empty  struct{}
  ctx    EvalContext
  kv     Expr
  nv     *NamedVar
	s      string
	v      Value
}

// %token<empty> COLON AND ORÅ@FLOAT 
%token<s> STRING TRUE FALSE INTEGER NIL

%left ','

%%

validator :
   /*	nothing */
   {
      Explex.(*ExpLexer).Expr = nil
   }
   | vars
   {
			Explex.(*ExpLexer).EvalContext = $1
   }


vars:
   var
   {
	   $$ = EvalContext{}
		 $$.Put($1)
   }
   | vars newlines var
   {
		 $$.Put($3)
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
	   $$ = &NilVar{}
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

newlines : 
	newline
	| newlines newline

newline : '\n'

%%


