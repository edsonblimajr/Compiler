package compiler.arvoresintatica;

public abstract class NodeTipo {

    public byte type;
    private int dimensao;

    public int getDimensao() {
        return dimensao;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }
    
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
    
    public abstract void visit(Visitor v);
}
