package compiler.main;

import compiler.lexico.Scanner;
import compiler.lexico.Token;

public class MainClass {
    public static void main(String[] args){
        Scanner sc = new Scanner("teste.txt");
        Token token = null;
    
        do{
            token = sc.nextToken();
            if(token != null){
                System.out.println(token);
            }
        }while(token != null);   
    }    
}