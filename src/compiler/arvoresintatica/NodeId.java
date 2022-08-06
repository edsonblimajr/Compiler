package compiler.arvoresintatica;

import compiler.lexico.Token;

public class NodeId extends Token {

    private int dimensao;
    
    public NodeId(byte type, String text, int line, int column) {
        super(type, text, line, column);
    }

    public int getDimensao() {
        return dimensao;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }

    @Override
    public byte getType() {
        return type;
    }

    @Override
    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public void setColumn(int column) {
        this.column = column;
    }
    
    

    public void visit(Visitor v) {
        v.visitId(this);
    }
}
