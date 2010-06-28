/*
PPGCA, 2010

*/
package logicamente.parser;
import java_cup.runtime.*;

%%

%class SyntaxTreeLexer
%public

%line
%column

%cup

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

WhiteSpace     = [ \t\f]

dec_int_id = [A-Za-z_][A-Za-z_0-9,]*

%%

<YYINITIAL>{

    "!"                { return symbol(sym.NEG); }
    "&"                { return symbol(sym.AND); }
    "|"                { return symbol(sym.OR); }
    "->"                {return symbol(sym.IMPLIES); }
    "("                { return symbol(sym.LPAREN); }
    ")"                { return symbol(sym.RPAREN); }

    {dec_int_id}       { return symbol (sym.ID, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }
}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }

