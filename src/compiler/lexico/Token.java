package compiler.lexico;

public class Token {
	public static final int TK_IDENTIFIER  = 0;
	public static final int TK_NUMBER      = 1;
	public static final int TK_OPERATOR    = 2;
	public static final int TK_PONCTUATION = 3;
	public static final int TK_ASSIGN      = 4;
      	public static final int TK_IF          = 5;

	
	public static final String TK_TEXT[] = {
			"IDENTIFIER", "NUMBER", "OPERATOR", "PONCTUACTION", "ASSIGNMENT" , "IF"
	};
	
	private int    type;
	private String text;
	private int    line;
	private int    column;
	
	public Token(int type, String text, int line, int column) {
		super();
		this.type = type;
		this.text = text;
                this.line = line;
		this.column = column;
	}

	public Token() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
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
		//return "Token [type= " + type + ", text= " + text + "]";
                return "Token [type=" + type + ", text=" + text + ", line=" + line + ", column=" + column + "]";

        }
}
