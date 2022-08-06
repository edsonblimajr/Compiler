package compiler.arvoresintatica;

public class NodeIntLit extends NodeLiteral {

    public String intLiteral;

    public NodeIntLit(String intLiteral) {
        this.intLiteral = intLiteral;
    }

    public NodeIntLit(byte type, String intLiteral, int line, int column) {
        this.type = type;
        this.intLiteral = intLiteral;
        this.line = line;
        this.column = column;
    }

    @Override
    public void visit(Visitor v) {
        v.visitIntLit(this);
    }

    @Override
    public byte getType() {
        return type;
    }

    @Override
    public void setType(byte type) {
        this.type = type;
    }

    public String getIntLiteral() {
        return intLiteral;
    }

    public void setIntLiteral(String intLiteral) {
        this.intLiteral = intLiteral;
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

}
