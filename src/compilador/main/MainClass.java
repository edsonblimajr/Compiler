package compilador.main;

import compilador.exceptions.LexicalException;
import compilador.lexico.Scanner;
import compilador.lexico.Token;


public class MainClass {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner("teste.txt");
            Token token = null;

            do {
                token = sc.nextToken();
                if (token != null) {
                    System.out.println(token);
                }
            } while (token != null);
            System.out.println("Compilation Successful!");
        } catch (LexicalException ex) {
            System.out.println("Lexical Error " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Generic Error!!");
            System.out.println(ex.getClass().getName());
        }
    }
}