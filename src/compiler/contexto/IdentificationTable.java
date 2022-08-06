package compiler.contexto;

import compiler.arvoresintatica.NodeDeclaracaoDeVariavel;
import compiler.arvoresintatica.NodeId;
import compiler.arvoresintatica.NodeListaDeIds;
import java.util.ArrayList;
import compiler.lexico.Token;

public class IdentificationTable {

    private final ArrayList<NodeId> identificadores = new ArrayList<>();
    private static int i = 0;
    private boolean naoEstaContido = true;

    public void enter(NodeDeclaracaoDeVariavel v) {
        NodeListaDeIds li = v.nodeListaDeIds;
        NodeId id;
        do {
            id = li.nodeId;
            if (!identificadores.isEmpty()) {
                for (int j = 0; j < identificadores.size(); j++) {
                    if (id.getText().equals(identificadores.get(j).getText())) {
                        System.out.println("CONTEXT ERROR! -"
                                + " LINE: " + id.getLine()
                                + " COLUMN: " + id.getColumn()
                                + " - Identifier " + id.getText() + " already declared!");
                        naoEstaContido = false;
                        System.exit(0);
                    }
                }
            }
            if (naoEstaContido) {
                id.setType(v.nodeTipo.getType());
                id.setDimensao(v.nodeListaDeIds.getDimensao());
                identificadores.add(id);
                i++;
            }
            naoEstaContido = true;
            li = li.next;
        } while (li != null);
    }

    public NodeId retrieve(NodeId id) {
        if (!identificadores.isEmpty()) {
            for (int j = 0; j < identificadores.size(); j++) {
                if (!id.getText().equals(identificadores.get(j).getText())) {
                    naoEstaContido = true;
                } else {
                    naoEstaContido = false;
                    return identificadores.get(j);
                }
            }
            if (naoEstaContido) {
                System.out.println("CONTEXT ERROR! -"
                        + " LINE: " + id.getLine()
                        + " COLUMN: " + id.getColumn()
                        + " - Identifier " + id.getText() + " not declared!");
                System.exit(0);
            }
        }
        return new NodeId((byte)-1, id.getText(), id.getLine(), id.getColumn());
    }
    
    public void imprime() {
        System.out.println("Tabela de Identificadores: ");
        for (int j = 0; j < identificadores.size(); j++) {
            System.out.println("id: " + identificadores.get(j).getText() 
                    + " line: " + identificadores.get(j).getLine()
                    + " column: " + identificadores.get(j).getColumn()
                    + " tipo: " + identificadores.get(j).getType()
                    + " dimensao: " + identificadores.get(j).getDimensao());
        }
        System.out.println("");
    }
}
