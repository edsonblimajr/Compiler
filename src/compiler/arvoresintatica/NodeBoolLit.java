package compiler.arvoresintatica;

public class NodeBoolLit extends NodeLiteral {

    public String booleano;

    public NodeBoolLit(String booleano) {
        this.booleano = booleano;
    }

    public NodeBoolLit(byte type, String booleano, int line, int column) {
        this.type = type;
        this.booleano = booleano;
        this.line = line;
        this.column = column;
    }

    public String getBooleano() {
        return booleano;
    }

    public void setBooleano(String booleano) {
        this.booleano = booleano;
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
    
    @Override
    public void visit(Visitor v) {
        v.visitBoolLit(this);
    }
}
