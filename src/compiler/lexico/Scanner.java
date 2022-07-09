package compiler.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Scanner {    
	private char[] content;
	private int    estado;
	private int    pos;
	private int    line;
	private int    column;
	
	public Scanner(String filename) {
            try {
		line = 1;
		column = 1;
		String txtConteudo;
		txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
		System.out.println("DEBUG --------");
		System.out.println(txtConteudo);
		System.out.println("--------------");
		content = txtConteudo.toCharArray();
		pos=0;
            }
            catch(Exception ex) {
		ex.printStackTrace();
            }
	}
	
	public Token nextToken() {
            char currentChar;
            Token token;
            String term="";
            if (isEOF()) {
		return null;
            }
            estado = 0;
            while (true) {
		currentChar = nextChar();
		column++;
		switch(estado) {
                    case 0:
			if (isChar(currentChar)) {
                            term += currentChar;
                            estado = 1;                             
			}
			else if (isDigit(currentChar)) {
                            estado = 2;
                            term += currentChar;                            
			}                        
			else if (isSpace(currentChar)) {
                            estado = 0; 
                            if ((content[pos - 2] != '\r' && currentChar == '\n') || currentChar == '\r') {
                                line++;
                                column=0;
                            }                              
			}
                        else if (isGraphic(currentChar)) {
                            //estado = 0;
                            term += currentChar; 
                            if(";".equals(term)){
                                token = new Token();
                                token.setType(Token.SEMICOLON);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }                            
                            else if(",".equals(term)){
                                token = new Token();
                                token.setType(Token.COLON);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
			}
			else if (isOperator(currentChar)) {                            
                            term += currentChar;                            
                            if("<".equals(term)){                           
                                token = new Token();
                                token.setType(Token.LESSTHAN);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if(">".equals(term)){
                                token = new Token();
                                token.setType(Token.BIGGERTHAN);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("<=".equals(term)){
                                token = new Token();
                                token.setType(Token.LESSOREQUAL);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if(">=".equals(term)){
                                token = new Token();
                                token.setType(Token.BIGGEROREQUAL);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("<>".equals(term)){
                                token = new Token();
                                token.setType(Token.DIFFERENT);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("//".equals(term)){
                                token = new Token();
                                token.setType(Token.COMMENTS);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }                            
                            else if("!".equals(term)){
                                token = new Token();
                                token.setType(Token.NEG);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("=".equals(term)){
                                token = new Token();
                                token.setType(Token.EQUALS);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("+".equals(term)){
                                token = new Token();
                                token.setType(Token.PLUS);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("-".equals(term)){
                                token = new Token();
                                token.setType(Token.MINUS);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("*".equals(term)){
                                token = new Token();
                                token.setType(Token.TIMES);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if("/".equals(term)){
                                token = new Token();
                                token.setType(Token.DIVIDEDBY);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if(":=".equals(term)){
                                token = new Token();
                                token.setType(Token.BECOMES);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else{
                                token = new Token();
                                token.setType(Token.LEXICAL_ERROR);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
			}                        
			else {
                            throw new RuntimeException("Unrecognized SYMBOL");
			}
			break;
                    case 1:
			if (isChar(currentChar) || isDigit(currentChar)) {
                            estado = 1;
                            term += currentChar;                           
			}                        
			else if (isSpace(currentChar) || isOperator(currentChar) || isGraphic(currentChar) || isEOF(currentChar)){                            
                            if (!isEOF(currentChar)){				
                                back();
                            }                            
                            if ("program".equals(term)){
                                token = new Token();
                                token.setType(Token.PROGRAM);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("begin".equals(term)){
                                token = new Token();
                                token.setType(Token.BEGIN);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("true".equals(term)){
                                token = new Token();
                                token.setType(Token.TRUE);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("if".equals(term)){
                                token = new Token();
                                token.setType(Token.IF);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("then".equals(term)){
                                token = new Token();
                                token.setType(Token.THEN);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("else".equals(term)){
                                token = new Token();
                                token.setType(Token.ELSE);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("do".equals(term)){
                                token = new Token();
                                token.setType(Token.DO);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("false".equals(term)){
                                token = new Token();
                                token.setType(Token.FALSE);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("boolean".equals(term)){
                                token = new Token();
                                token.setType(Token.BOOLEAN);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("or".equals(term)){
                                token = new Token();
                                token.setType(Token.OR);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("and".equals(term)){
                                token = new Token();
                                token.setType(Token.AND);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }
                            else if ("while".equals(term)){
                                token = new Token();
                                token.setType(Token.WHILE);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                                return token;
                            }                            
                            else{                                
                                token = new Token();
                                token.setType(Token.IDENTIFIER);
                                token.setText(term);
                                token.setLine(line);
                                token.setColumn(column - term.length());
                            return token;
                            }
                            
                        }
                        else {
                            throw new RuntimeException("Malformed Identifier");
                        }
                        break;
                    case 2:
			if (isDigit(currentChar) || currentChar == '.') {
                            estado = 2;
                            term += currentChar;
			}
			else if (!isChar(currentChar) || isEOF(currentChar )) {                            
                            if (!isEOF(currentChar)){				
                                back();                                
                            } 
                            token = new Token();
                            token.setType(Token.INTLITERAL);
                            token.setText(term);
                            token.setLine(line);
                            token.setColumn(column - term.length());
                            return token;
                        }
			else {
                            throw new RuntimeException("Unrecognized Number");
			}
			break;
		}
            }
	}
        
	private boolean isDigit(char c) {
            return c >= '0' && c <= '9';
	}
	
	private boolean isChar(char c) {
            return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}
	
	private boolean isOperator(char c) {
            return c == '>' || c == '<' || c == '=' || c == '!' || c == '+' || c == '-' || c == '*' || c == '/';
	}
        
        private boolean isGraphic(char c) {
            return c == ';' || c == ':' || c == ',' || c == '#' || c == '@';
	}
	private boolean isSpace(char c) {
            /*if (c == '\n' || c== '\r') {
            	line+=0;
            	column=0;
            }*/
            return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
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