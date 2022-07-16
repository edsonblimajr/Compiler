package compilador.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import compilador.exceptions.LexicalException;

public class Scanner {

    private char[] content;
    private int estado;
    private int pos;
    private int line;
    private int column;

    public Scanner(String filename) {
        try {
            line = 1;
            column = 0;
            String txtConteudo;
            txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            content = txtConteudo.toCharArray();
            pos = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Token nextToken() {
        char currentChar;
        Token token;
        String term = "";
        if (isEOF()) {
            return null;
        }
        estado = 0;

        while (true) {
            currentChar = nextChar();
            column++;

            switch (estado) {

                // Estado Geral de Leitura
                case 0:
                    if (isChar(currentChar)) {
                        term += currentChar;
                        estado = 1;
                    } else if (isDigit(currentChar)) {
                        term += currentChar;
                        estado = 3;
                    } else if (isSpace(currentChar)) {
                        estado = 0;
                    } else if (isOperator(currentChar)) {
                        estado = 5;
                        term += currentChar;
                    } else if (IsGraphic(currentChar)) {
                        estado = 7;
                        term += currentChar;
                    } else if (isEndLine(currentChar)) {
                        estado = 10;
                    } else {
                        throw new LexicalException("Unrecognized SYMBOL");
                    }
                    break;

                // Enquanto ler um sequencia de letras
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    } else if (isSpace(currentChar) || isOperator(currentChar) || isEOF(currentChar) || isEndLine(currentChar) || IsGraphic(currentChar)) {
                        estado = 2;
                    } else {
                        throw new LexicalException("Malformed Identifier");
                    }
                    break;

                // Quando chegou ao fim de uma sequencia de letras e encontrou
                // um identificador
                case 2:
                    if (!isEOF(currentChar)) {
                        back();
                    }
                    switch (term) {
                        case "program":
                            token = new Token();
                            token.setType(Token.PROGRAM);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "begin":
                            token = new Token();
                            token.setType(Token.BEGIN);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "true":
                            token = new Token();
                            token.setType(Token.TRUE);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "if":
                            token = new Token();
                            token.setType(Token.IF);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "then":
                            token = new Token();
                            token.setType(Token.THEN);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "else":
                            token = new Token();
                            token.setType(Token.ELSE);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "do":
                            token = new Token();
                            token.setType(Token.DO);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "false":
                            token = new Token();
                            token.setType(Token.FALSE);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "boolean":
                            token = new Token();
                            token.setType(Token.BOOLEAN);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "or":
                            token = new Token();
                            token.setType(Token.OR);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "and":
                            token = new Token();
                            token.setType(Token.AND);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "while":
                            token = new Token();
                            token.setType(Token.WHILE);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        default:
                            token = new Token();
                            token.setType(Token.IDENTIFIER);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                    }

                // Enquanto ler um sequencia de numeros
                case 3:
                    if (isDigit(currentChar)) {
                        estado = 3;
                        term += currentChar;
                    } else if (isSpace(currentChar) || isChar(currentChar) || isOperator(currentChar) || isEOF(currentChar) || isEndLine(currentChar) || IsGraphic(currentChar)) {
                        estado = 4;
                    } else {
                        throw new LexicalException("Malformed Identifier");
                    }
                    break;

                // Quando chegou ao fim de uma sequencia de numeros e encontrou
                // um numero literal
                case 4:
                    if (!isEOF(currentChar)) {
                        back();
                    }
                    token = new Token();
                    token.setType(Token.INTLITERAL);
                    token.setText(term);
                    token.setLine(line);
                    token.setColumn(column - term.length());
                    return token;

                // Enquanto ler um sequencia de operadores
                case 5:
                    if (isOperator(currentChar)) {
                        term += currentChar;
                        estado = 5;
                    } else if (isSpace(currentChar) || isChar(currentChar) || isEOF(currentChar) || isEndLine(currentChar) || IsGraphic(currentChar)) {
                        estado = 6;
                    } else {
                        throw new LexicalException("Malformed Identifier");
                    }
                    break;

                // Caso seja um operador
                case 6:
                    if (!isEOF(currentChar)) {
                        back();
                    }
                    switch (term) {
                        case "+":
                            token = new Token();
                            token.setType(Token.PLUS);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "-":
                            token = new Token();
                            token.setType(Token.MINUS);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "*":
                            token = new Token();
                            token.setType(Token.TIMES);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "/":
                            token = new Token();
                            token.setType(Token.DIVIDEDBY);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "<":
                            token = new Token();
                            token.setType(Token.LESSTHAN);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "<=":
                            token = new Token();
                            token.setType(Token.LESSOREQUAL);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "<>":
                            token = new Token();
                            token.setType(Token.DIFFERENT);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case ">":
                            token = new Token();
                            token.setType(Token.BIGGERTHAN);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case ">=":
                            token = new Token();
                            token.setType(Token.BIGGEROREQUAL);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case "=":
                            token = new Token();
                            token.setType(Token.EQUALS);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        default:
                            token = new Token();
                            token.setType(Token.LEXICAL_ERROR);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                    }

                // Enquanto ler um sequencia de Graphic
                case 7:
                    if (IsGraphic(currentChar) || currentChar == '=') {
                        estado = 7;
                        term += currentChar;
                    } else {
                        estado = 8;
                    }
                    break;

                // Caso seja um Graphic
                case 8:
                    switch (term) {
                        case ":=":
                            token = new Token();
                            token.setType(Token.BECOMES);
                            token.setText(":=");
                            token.setLine(line);
                            token.setColumn(column - 2);
                            return token;
                        case "!":
                            estado = 9;
                            break;
                        case ",":
                            if (!isEOF(currentChar)) {
                                back();
                            }
                            token = new Token();
                            token.setType(Token.COLON);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        case ";":
                            if (!isEOF(currentChar)) {
                                back();
                            }
                            token = new Token();
                            token.setType(Token.SEMICOLON);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        default:
                            if (!isEOF(currentChar)) {
                                back();
                            }
                            token = new Token();
                            token.setType(Token.LEXICAL_ERROR);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                    }

                // Se comentário
                case 9:
                    estado = 9;
                    if (isEndLine(currentChar)) {
                        estado = 10;
                        column = 0;
                    }
                    break;

                // Se é final de linha
                case 10:
                    estado = 10;
                    if (!isEndLine(currentChar)) {
                        line++;
                        column = 1;
                        estado = 0;
                        if (!isEOF(currentChar)) {
                            back();
                        }
                    }
                    break;
            }
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean IsGraphic(char c) {
        return c == ';' || c == ':' || c == ',' || c == '!';
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }

    private boolean isEndLine(char c) {
        return c == '\n' || c == '\r';
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return content[pos++];
    }

    private boolean isEOF() {
        return pos >= content.length;
    }

    private void back() {
        pos--;
        column--;
    }

    private boolean isEOF(char c) {
        return c == '\000';
    }
}
