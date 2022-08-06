package compiler.arvoresintatica;

public class NodeExpressaoSimples {
    
    public NodeTermo nodeTermo;
    public NodeExpressaoSimplesComplemento nodeExpressaoSimplesComplemento;
    public byte type;
    public int line;
    public int column;

    public NodeTermo getNodeTermo() {
        return nodeTermo;
    }

    public void setNodeTermo(NodeTermo nodeTermo) {
        this.nodeTermo = nodeTermo;
    }

    public NodeExpressaoSimplesComplemento getNodeExpressaoSimplesComplemento() {
        return nodeExpressaoSimplesComplemento;
    }

    public void setNodeExpressaoSimplesComplemento(NodeExpressaoSimplesComplemento nodeExpressaoSimplesComplemento) {
        this.nodeExpressaoSimplesComplemento = nodeExpressaoSimplesComplemento;
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
        v.visitExpressaoSimples(this);
    }
    
}
