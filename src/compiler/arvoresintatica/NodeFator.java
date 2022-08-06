package compiler.arvoresintatica;

public abstract class NodeFator {
    
    public byte type;
    public int line;
    public int column;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public abstract void visit(Visitor v);
}
