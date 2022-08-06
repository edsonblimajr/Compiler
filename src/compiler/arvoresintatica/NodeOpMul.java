package compiler.arvoresintatica;

import compiler.lexico.Token;

public class NodeOpMul extends Token {

    public NodeOpMul(byte type, String text, int line, int column) {
        super(type, text, line, column);
    }

    public void visit(Visitor v) {
        v.visitOpMul(this);
    }
}
