package org.example;

import java.util.Scanner;

public class Operacao {


    public static String gerenciadorDeOperacao(){
        Scanner env = new Scanner(System.in);
        System.out.println("Deseja iniciar algum projeto? S/n");
        String declaracao = env.next();

        if (declaracao.equalsIgnoreCase("s")) {

        }

        return "inativo";
    }

    static String operacaoRobo(){
        return "";
    }

    static String statusDesenvolvimento(){

        return "";
    }
}
