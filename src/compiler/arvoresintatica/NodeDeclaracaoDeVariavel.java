package compiler.arvoresintatica;

public class NodeDeclaracaoDeVariavel {
    
    public NodeListaDeIds nodeListaDeIds;
    public NodeTipo nodeTipo;
    
    public void visit(Visitor v) {
        v.visitDeclaracaoDeVariavel(this);
    }
}
