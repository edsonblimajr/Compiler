package compiler.arvoresintatica;

public class NodeComandoComposto extends NodeComando {
    
    public NodeListaDeComandos nodeListaDeComandos;
    
    @Override
    public void visit(Visitor v) {
        v.visitComandoComposto(this);
    }
}
