package compiler.arvoresintatica;

public class NodeAtribuicao extends NodeComando {

    public NodeVariavel nodeVariavel;
    public NodeExpressao nodeExpressao;
    public byte type;

    @Override
    public void visit(Visitor v) {
        v.visitAtribuicao(this);

    }
}
