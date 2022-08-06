package compiler.arvoresintatica;

public class NodeFloatLit extends NodeLiteral {

    public String floatLiteral;

    public NodeFloatLit(String floatLiteral) {
        this.floatLiteral = floatLiteral;
    }

    public NodeFloatLit(byte type, String floatLiteral, int line, int column) {
        this.type = type;
        this.floatLiteral = floatLiteral;
        this.line = line;
        this.column = column;
    }

    public String getFloatLiteral() {
        return floatLiteral;
    }

    public void setFloatLiteral(String floatLiteral) {
        this.floatLiteral = floatLiteral;
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
        v.visitFloatLit(this);
    }
}
