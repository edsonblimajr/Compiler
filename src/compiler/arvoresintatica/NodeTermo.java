package compiler.arvoresintatica;

public class NodeTermo {

    public NodeFator nodeFator;
    public NodeTermoComplemento nodeTermoComplemento;
    public byte type;
    public int line;
    public int column;

    public NodeFator getNodeFator() {
        return nodeFator;
    }

    public void setNodeFator(NodeFator nodeFator) {
        this.nodeFator = nodeFator;
    }

    public NodeTermoComplemento getNodeTermoComplemento() {
        return nodeTermoComplemento;
    }

    public void setNodeTermoComplemento(NodeTermoComplemento nodeTermoComplemento) {
        this.nodeTermoComplemento = nodeTermoComplemento;
    }

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
    
    public void visit(Visitor v) {
        v.visitTermo(this);
    }
}
