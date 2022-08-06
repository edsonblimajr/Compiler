package compiler.contexto;

import compiler.arvoresintatica.NodeId;
import compiler.arvoresintatica.NodeTipo;

public class Node {

    private NodeId nodeId;
    private NodeTipo nodeTipo;

    public NodeId getNodeId() {
        return nodeId;
    }

    public void setNodeId(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public NodeTipo getNodeTipo() {
        return nodeTipo;
    }

    public void setNodeTipo(NodeTipo nodeTipo) {
        this.nodeTipo = nodeTipo;
    }
}
