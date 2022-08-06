package compiler.arvoresintatica;

public class NodeCorpo {
    
    public NodeDeclaracoes nodeDeclaracoes;
    public NodeComandoComposto nodeComandoComposto;
    
    public void visit(Visitor v) {
        v.visitCorpo(this);
    }
}
