package compiler.arvoresintatica;

import compiler.lexico.Token;

public class NodeOpAd extends Token {

    public NodeOpAd(byte type, String text, int line, int column) {
        super(type, text, line, column);
    }

    public void visit(Visitor v) {
        v.visitOpAd(this);
    }
}
