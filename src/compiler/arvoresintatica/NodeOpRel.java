package compiler.arvoresintatica;

import compiler.lexico.Token;

public class NodeOpRel extends Token {

    public NodeOpRel(byte type, String text, int line, int column) {
        super(type, text, line, column);
    }

    public void visit(Visitor v) {
        v.visitOpRel(this);
    }
}
