package compiler.sintatico;

import compiler.arvoresintatica.NodeAtribuicao;
import compiler.arvoresintatica.NodeBoolLit;
import compiler.arvoresintatica.NodeComando;
import compiler.arvoresintatica.NodeComandoComposto;
import compiler.arvoresintatica.NodeCondicional;
import compiler.arvoresintatica.NodeCorpo;
import compiler.arvoresintatica.NodeDeclaracao;
import compiler.arvoresintatica.NodeDeclaracaoDeVariavel;
import compiler.arvoresintatica.NodeDeclaracoes;
import compiler.arvoresintatica.NodeExpressao;
import compiler.arvoresintatica.NodeExpressaoSimples;
import compiler.arvoresintatica.NodeExpressaoSimplesComplemento;
import compiler.arvoresintatica.NodeFator;
import compiler.arvoresintatica.NodeFloatLit;
import compiler.arvoresintatica.NodeId;
import compiler.arvoresintatica.NodeIntLit;
import compiler.arvoresintatica.NodeIterativo;
import compiler.arvoresintatica.NodeListaDeComandos;
import compiler.arvoresintatica.NodeListaDeIds;
import compiler.arvoresintatica.NodeLiteral;
import compiler.arvoresintatica.NodeOpAd;
import compiler.arvoresintatica.NodeOpMul;
import compiler.arvoresintatica.NodeOpRel;
import compiler.arvoresintatica.NodePrograma;
import compiler.arvoresintatica.NodeSeletor;
import compiler.arvoresintatica.NodeTermo;
import compiler.arvoresintatica.NodeTermoComplemento;
import compiler.arvoresintatica.NodeTipo;
import compiler.arvoresintatica.NodeTipoAgregado;
import compiler.arvoresintatica.NodeTipoSimples;
import compiler.arvoresintatica.NodeVariavel;
import compiler.main.MainClass;

import java.io.IOException;
import compiler.lexico.Scanner;
import compiler.lexico.Token;

public class Parser {
    private Token currentToken;
    public Scanner scanner;

    public Parser() throws IOException {
        scanner = new Scanner("teste.txt");
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    private void accept(byte type) throws IOException {
        if (currentToken.getType() == type) {
            currentToken = scanner.nextToken();
        } else {
            System.out.println("SYNTAX ERROR! - "
                    + "LINE: " + currentToken.getLine()
                    + " COLUMN: " + currentToken.getColumn()
                    + " - A Token of type \"" + Token.spellings[type]
                    + "\" was expected and not token " + "\"" + currentToken.getText() + "\"");
            System.exit(0);
        }
    }

    private void acceptIt() throws IOException {
       currentToken = scanner.nextToken();
    }
    
    public NodePrograma parse() throws IOException {
        currentToken = scanner.nextToken();
        return parsePrograma();
    }

    private NodeAtribuicao parseAtribuicao() throws IOException {
        NodeAtribuicao a = new NodeAtribuicao();
        a.nodeVariavel = parseVariavel();
        accept(Token.BECOMES);
        a.nodeExpressao = parseExpressao();
        return a;
    }

    private NodeBoolLit parseBoolLit() throws IOException {
        //String boolLit = scanner.getCurrentSpelling().toString(); 
        String boolLit = scanner.nextToken().getText();

        NodeBoolLit b = null;
        switch (currentToken.getType()) {
            case Token.TRUE:
            case Token.FALSE:
                int column = currentToken.getColumn();
                acceptIt();
                b = new NodeBoolLit(Token.BOOLEAN, boolLit, currentToken.getLine(), column);
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.TRUE] + "\""
                        + " | \"" + Token.spellings[Token.FALSE] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error
        }
        return b;
    }
    
    private NodeComando parseComando() throws IOException {
        NodeComando c = null;
        switch (currentToken.getType()) {
            case Token.IDENTIFIER:
                c = parseAtribuicao();
                break;
            case Token.IF:
                c = parseCondicional();
                break;
            case Token.WHILE:
                c = parseIterativo();
                break;
            case Token.BEGIN:
                c = parseComandoComposto();
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.IDENTIFIER] + "\""
                        + " | \"" + Token.spellings[Token.IF] + "\""
                        + " | \"" + Token.spellings[Token.WHILE] + "\""
                        + " | \"" + Token.spellings[Token.BEGIN] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error
        }
        return c;
    }

    private NodeComandoComposto parseComandoComposto() throws IOException {
        NodeComandoComposto c = new NodeComandoComposto();
        accept(Token.BEGIN);
        c.nodeListaDeComandos = parseListaDeComandos();
        accept(Token.END);
        return c;
    }

    private NodeCondicional parseCondicional() throws IOException {
        NodeCondicional c = new NodeCondicional();
        accept(Token.IF);
        c.nodeExpressao = parseExpressao();
        accept(Token.THEN);
        c.nodeComandoIf = parseComando();
        if (currentToken.getType() == Token.ELSE) {
            acceptIt();
            c.nodeComandoElse = parseComando();
        } else {
            c.nodeComandoElse = null;
            //report a systatic error
        }
        return c;

    }

    private NodeCorpo parseCorpo() throws IOException {
        NodeCorpo c = new NodeCorpo();
        c.nodeDeclaracoes = parseDeclaracoes();
        c.nodeComandoComposto = parseComandoComposto();
        return c;
    }

    private NodeDeclaracao parseDeclaracao() throws IOException {
        return new NodeDeclaracao(parseDeclaracaoDeVariavel());
    }

    private NodeDeclaracaoDeVariavel parseDeclaracaoDeVariavel() throws IOException {
        NodeDeclaracaoDeVariavel d = new NodeDeclaracaoDeVariavel();
        accept(Token.VAR);
        d.nodeListaDeIds = parseListadeIds();
        accept(Token.COLON);
        d.nodeTipo = parseTipo();
        return d;
    }

    private NodeDeclaracoes parseDeclaracoes() throws IOException {
        NodeDeclaracoes d, first, last;
        first = null;
        last = null;
        while (currentToken.getType() == Token.VAR) {
            d = new NodeDeclaracoes(parseDeclaracao(), null);
            accept(Token.SEMICOLON);
            if (first == null) {
                first = d;
            } else {
                last.next = d;
            }
            last = d;
        }
        return first;
    }

    private NodeExpressao parseExpressao() throws IOException {
        NodeExpressao e = new NodeExpressao();
        e.nodeExpressaoSimples1 = parseExpressaoSimples();
        switch (currentToken.getType()) {
            case Token.BIGGEROREQUAL:
            case Token.BIGGERTHAN:
            case Token.DIFFERENT:
            case Token.EQUALS:
            case Token.LESSOREQUAL:
            case Token.LESSTHAN:
                e.nodeOpRel = parseOpRel();
                e.nodeExpressaoSimples2 = parseExpressaoSimples();
                break;
            default:
                e.nodeExpressaoSimples2 = null;
                e.nodeOpRel = null;
            //report a systatic erro    
        }
        return e;
    }

    private NodeExpressaoSimples parseExpressaoSimples() throws IOException {
        NodeExpressaoSimples eS = new NodeExpressaoSimples();
        NodeExpressaoSimplesComplemento eSC, first, last;
        eS.nodeTermo = parseTermo();
        first = null;
        last = null;
        while (currentToken.getType() == Token.PLUS
                || currentToken.getType() == Token.OR
                || currentToken.getType() == Token.MINUS) {
            eSC = new NodeExpressaoSimplesComplemento();
            eSC.nodeOpAd = parseOpAd();
            eSC.nodeTermo = parseTermo();
            eSC.next = null;
            if (first == null) {
                first = eSC;
            } else {
                last.next = eSC;
            }
            last = eSC;
        }
        eS.nodeExpressaoSimplesComplemento = first;
        return eS;
    }

    private NodeFator parseFator() throws IOException {
        NodeFator f = null;
        switch (currentToken.getType()) {
            case Token.IDENTIFIER:
                f = parseVariavel();
                break;
            case Token.TRUE:
            case Token.FALSE:
            case Token.INTLITERAL:
            //case Token.FLOAT_LIT:
                f = parseLiteral();
                break;
            case Token.LPAREN:
                acceptIt();
                f = parseExpressao();
                accept(Token.RPAREN);
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.IDENTIFIER] + "\""
                        + " | \"" + Token.spellings[Token.TRUE] + "\""
                        + " | \"" + Token.spellings[Token.FALSE] + "\""
                        + " | \"" + Token.spellings[Token.INTLITERAL] + "\""
            //            + " | \"" + Token.spellings[Token.FLOAT_LIT] + "\""
                        + " | \"" + Token.spellings[Token.LPAREN] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error
        }
        return f;
    }
    /* Falta Implementar o Token.FLOATLITERAL
    private NodeFloatLit parseFloatLit() throws IOException { //Não sei se precisa
        int column;
        //String floatLiteral = scanner.getCurrentSpelling().toString();
        String floatLiteral = scanner.nextToken().getText();
        column = currentToken.getColumn();
        accept(Token.FLOAT_LIT);
        return new NodeFloatLit(Token.FLOAT_LIT, floatLiteral, currentToken.getLine(), column);
    }*/

    private NodeId parseId() throws IOException { //Não sei se precisa
        int column;
        //String identificador = scanner.getCurrentSpelling().toString();
        String identificador = scanner.nextToken().getText();
        column = currentToken.getColumn();
        accept(Token.IDENTIFIER);
        return new NodeId(Token.IDENTIFIER, identificador, currentToken.getLine(), column);
    }

    private NodeIntLit parseIntLit() throws IOException {  //Não sei se precisa
        int column;
        //String intLiteral = scanner.getCurrentSpelling().toString();
        String intLiteral = scanner.nextToken().getText();
        column = currentToken.getColumn();
        accept(Token.INTLITERAL);
        return new NodeIntLit(Token.INTLITERAL, intLiteral, currentToken.getLine(), column);
    }

    private NodeIterativo parseIterativo() throws IOException {
        NodeIterativo i = new NodeIterativo();
        accept(Token.WHILE);
        i.nodeExpressao = parseExpressao();
        accept(Token.DO);
        i.nodeComando = parseComando();
        return i;
    }

    private NodeListaDeComandos parseListaDeComandos() throws IOException {
        NodeListaDeComandos lC, first, last;
        first = null;
        last = null;
        while (currentToken.getType() == Token.IDENTIFIER
                || currentToken.getType() == Token.IF
                || currentToken.getType() == Token.WHILE
                || currentToken.getType() == Token.BEGIN) {
            lC = new NodeListaDeComandos();
            lC.nodeComando = parseComando();
            accept(Token.SEMICOLON);
            lC.next = null;
            if (first == null) {
                first = lC;
            } else {
                last.next = lC;
            }
            last = lC;
        }
        return first;
    }

    private NodeListaDeIds parseListadeIds() throws IOException {
        int column;
        //String identificador = scanner.getCurrentSpelling().toString();
        String identificador = scanner.nextToken().getText();
        NodeListaDeIds l, first, last;
        column = currentToken.getColumn();
        accept(Token.IDENTIFIER);
        l = new NodeListaDeIds(new NodeId(Token.IDENTIFIER, identificador, currentToken.getLine(), column), null);
        first = l;
        last = l;
        while (currentToken.getType() == Token.COMMA) {
            acceptIt();
            //identificador = scanner.getCurrentSpelling().toString();
            identificador = scanner.nextToken().getText();
            column = currentToken.getColumn();
            accept(Token.IDENTIFIER);
            l = new NodeListaDeIds(new NodeId(Token.IDENTIFIER, identificador, currentToken.getLine(), column), null);
            last.next = l;
            last = l;
        }
        return first;
    }

    private NodeLiteral parseLiteral() throws IOException {
        NodeLiteral l = null;
        switch (currentToken.getType()) {
            case Token.TRUE:
            case Token.FALSE:
                l = parseBoolLit();
                break;
            case Token.INTLITERAL:
                l = parseIntLit();
                break;
            /*case Token.FLOAT_LIT:
                l = parseFloatLit();
                break;*/
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.TRUE] + "\""
                        + " | \"" + Token.spellings[Token.FALSE] + "\""
                        + " | \"" + Token.spellings[Token.INTLITERAL] + "\""
            //            + " | \"" + Token.spellings[Token.FLOAT_LIT] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error    
        }
        return l;
    }

    private NodeOpAd parseOpAd() throws IOException {
        //String opAd = scanner.getCurrentSpelling().toString();
        String opAd = scanner.nextToken().getText();
        int column;
        NodeOpAd o = null;
        byte type;
        switch (currentToken.getType()) {
            case Token.PLUS:
            case Token.OR:
            case Token.MINUS:
                column = currentToken.getColumn();
                type = currentToken.type;
                acceptIt();
                o = new NodeOpAd(type, opAd, currentToken.line, column);
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.PLUS] + "\""
                        + " | \"" + Token.spellings[Token.OR] + "\""
                        + " | \"" + Token.spellings[Token.MINUS] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error    
        }
        return o;
    }

    private NodeOpMul parseOpMul() throws IOException {
        //String opMul = scanner.getCurrentSpelling().toString();
        String opMul = scanner.nextToken().getText();
        int column;
        NodeOpMul o = null;
        byte type;
        switch (currentToken.getType()) {
            case Token.AND:
            case Token.DIVIDEDBY:
            case Token.TIMES:
                column = currentToken.getColumn();
                type = currentToken.type;
                acceptIt();
                o = new NodeOpMul(type, opMul, currentToken.line, column);
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.AND] + "\""
                        + " | \"" + Token.spellings[Token.DIVIDEDBY] + "\""
                        + " | \"" + Token.spellings[Token.TIMES] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error    
        }
        return o;
    }

    private NodeOpRel parseOpRel() throws IOException {
        //String opRel = scanner.getCurrentSpelling().toString();
        String opRel = scanner.nextToken().getText();
        int column;
        NodeOpRel o = null;
        byte type;
        switch (currentToken.getType()) {
            case Token.BIGGEROREQUAL:
            case Token.BIGGERTHAN:
            case Token.DIFFERENT:
            case Token.EQUALS:
            case Token.LESSOREQUAL:
            case Token.LESSTHAN:
                column = currentToken.getColumn();
                type = currentToken.type;
                acceptIt();
                o = new NodeOpRel(type, opRel, currentToken.line, column);
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.BIGGEROREQUAL] + "\""
                        + " | \"" + Token.spellings[Token.BIGGERTHAN] + "\""
                        + " | \"" + Token.spellings[Token.DIFFERENT] + "\""
                        + " | \"" + Token.spellings[Token.EQUALS] + "\""
                        + " | \"" + Token.spellings[Token.LESSOREQUAL] + "\""
                        + " | \"" + Token.spellings[Token.LESSTHAN] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error    
        }
        return o;
    }

    private NodePrograma parsePrograma() throws IOException {
        NodePrograma p = new NodePrograma(); //Deletar o construtor
        accept(Token.PROGRAM);
        p.nodeId = parseId();
        accept(Token.SEMICOLON);
        p.nodeCorpo = parseCorpo();
    //    accept(Token.DOT);
        return p;
    }

    private NodeSeletor parseSeletor() throws IOException {
        NodeSeletor s, first, last;
        first = null;
        last = null;
        while (currentToken.getType() == Token.LEFTBRACKET) {
            acceptIt();
            s = new NodeSeletor();
            s.nodeExpressao = parseExpressao();
            accept(Token.RIGHTBRACKET);
            s.next = null;
            if (first == null) {
                first = s;
            } else {
                last.next = s;
            }
            last = s;
        }
        return first;
    }

    private NodeTermo parseTermo() throws IOException { //Voltar aqui em algum momento para encarrar isso!!!
        NodeTermo t = new NodeTermo();
        NodeTermoComplemento tC, first, last;
        t.nodeFator = parseFator();
        first = null;
        last = null;
        while (currentToken.getType() == Token.AND
                || currentToken.getType() == Token.DIVIDEDBY
                || currentToken.getType() == Token.TIMES) {
            tC = new NodeTermoComplemento();
            tC.nodeOpMul = parseOpMul();
            tC.nodeFator = parseFator();
            tC.next = null;
            if (first == null) {
                first = tC;
            } else {
                last.next = tC;
            }
            last = tC;
        }
        t.nodeTermoComplemento = first;
        return t;
    }

    private NodeTipo parseTipo() throws IOException {
        NodeTipo t = null;
        switch (currentToken.getType()) {
            /*case Token.ARRAY:
                t = parseTipoAgregado();
                break;*/
            case Token.INTEGER:
            case Token.REAL: // FALTA IMPLEMENTAR O REAL
            case Token.BOOLEAN:
                t = parseTipoSimples();
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        //+ " - A Token of type \"" + Token.spellings[Token.ARRAY] + "\""
                        + " | \"" + Token.spellings[Token.INTEGER] + "\""
                        + " | \"" + Token.spellings[Token.REAL] + "\""
                        + " | \"" + Token.spellings[Token.BOOLEAN] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error 
        }
        return t;
    }

    private NodeTipoAgregado parseTipoAgregado() throws IOException {
        NodeTipoAgregado tA = new NodeTipoAgregado();
        //accept(Token.ARRAY);
        accept(Token.LEFTBRACKET);
        //tA.nodeLiteral1 = parseLiteral(); //Errado? o correto seria <int-lit>?
        tA.nodeLiteral1 = parseIntLit();
        //accept(Token.DOTDOT);
        //tA.nodeLiteral2 = parseLiteral(); //Errado? o correto seria <int-lit>?
        tA.nodeLiteral2 = parseIntLit();
        accept(Token.RIGHTBRACKET);
        accept(Token.OF);
        tA.nodeTipo = parseTipo();
        return tA;
    }

    private NodeTipoSimples parseTipoSimples() throws IOException {
        //String tipoSimples = scanner.getCurrentSpelling().toString();
        String tipoSimples = scanner.nextToken().getText();
        NodeTipoSimples tS = null;
        switch (currentToken.getType()) {
            case Token.INTEGER:
            case Token.REAL:
            case Token.BOOLEAN:
                byte t = currentToken.type;
                acceptIt();
                tS = new NodeTipoSimples(tipoSimples);
                tS.type = t;
                break;
            default:
                System.out.println("SYNTAX ERROR! - "
                        + "LINE: " + currentToken.getLine()
                        + " COLUMN: " + currentToken.getColumn()
                        + " - A Token of type \"" + Token.spellings[Token.INTEGER] + "\""
                        + " | \"" + Token.spellings[Token.REAL] + "\""
                        + " | \"" + Token.spellings[Token.BOOLEAN] + "\""
                        + " was expected and not token " + "\""
                        + currentToken.getText() + "\"");
                System.exit(0);
            //report a systatic error    
        }
        return tS;
    }

    private NodeVariavel parseVariavel() throws IOException {
        int column;
        //String identificador = scanner.getCurrentSpelling().toString();
        String identificador = scanner.nextToken().getText();
        NodeVariavel v = new NodeVariavel();
        column = currentToken.getColumn();
        accept(Token.IDENTIFIER);
        v.nodeId = new NodeId(Token.IDENTIFIER, identificador, currentToken.getLine(), column);
        v.nodeSeletor = parseSeletor();
        return v;
    }

}
