package compiler.lexico;

public class Token {
	    
        public final static byte  
            IDENTIFIER = 0, 
            INTLITERAL = 1, 
            PROGRAM = 2,
            BEGIN = 3,
            TRUE = 4,
            IF = 5,
            THEN = 6,
            ELSE = 7,
            DO = 8,
            FALSE = 9,
            INTEGER = 10,//
            BOOLEAN = 11,
            REAL = 12,// INTLITERAL?
            OR = 13,
            AND = 14,
            WHILE = 15,
            PLUS = 16,
            MINUS = 17,
            TIMES = 18,
            DIVIDEDBY = 19,  
            LESSTHAN = 20,
            BIGGERTHAN = 21,    
            LESSOREQUAL = 22,
            BIGGEROREQUAL = 23,
            DIFFERENT = 24,  
            EQUALS = 25, 
            DOUBLEBAR = 26,
            SEMICOLON = 27, 
            BECOMES = 28,
            COLON = 29,    
            LPAREN = 30, // como verificar com conteudo dentro
            RPAREN = 31,//  como verificar com conteudo dentro
            COMMENTS = 32,
            HASH = 33,//    falta
            ATSIGN = 34,//  falta
            COMMA = 35,   //falta
            EOT = 36,    // falta
            LEXICAL_ERROR = -1; 

        private final static String[] spellings = {
            "<identifier>", 
            "<integer-literal>", 
            "<program>", 
            "begin", 
            "true", 
            "if", 
            "then",
            "else",
            "do",
            "false",
            "integer",
            "boolean",
            "real",
            "or",
            "and",
            //"eof",
            "while",
            "+",
            "<=",
            ">=",
            "<>",
            "-",
            "*",
            "//",
            ">",
            "<",
            "=",
            "/",
            "(",
            ")",
            ";",
            ":",
            "!",
            "#",
            ".", //remover
            "@",
            ":=",
            ",",
            "<eot>"};
	
	private byte    type;
	private String text;
	private int    line;
	private int    column;
	
	public Token(byte type, String text, int line, int column) {
		super();
		this.type = type;
		this.text = text;
                this.line = line;
		this.column = column;
	}

	public Token() {
		super();
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLine() {
		return line;
	}
        
	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
        
        @Override
	public String toString() {                		
                return "Token [type=" + type + ", text=" + text + ", line=" + line + ", column=" + column + "]";
        }
}
