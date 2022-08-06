package compiler.arvoresintatica;

public class NodePrograma extends AST {
    
    public NodeId nodeId;
    public NodeCorpo nodeCorpo;
    
    @Override
    public void visit(Visitor v) {
        v.visitPrograma(this);
    }
}
