package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner env = new Scanner(System.in);

        int action;

        do {
            exibir();
            System.out.println("Ação:");
            action = env.nextInt();
            menu(action, env);
        }while(action != 0);

    }

    static void exibir(){
        System.out.println("================================");
        System.out.println("MENU DE OPERAÇÃO - LABORATORIO");
        System.out.println("================================");

        System.out.println("""
        
                1 - Status do Programa
                2 - Registro de Logs em Tempo Real
                3 - Botão de Parada de Emergência
                0 - Sair
                """);
    }

    static int menu(int action, Scanner env){

        switch (action){

            case 1:

              break;

            case 2:
                System.out.println("Status do Programa:");
                break;
            case 3:
                String emergencia = emergencia(env);
                boolean status = verificacao(emergencia, env);
                System.out.println(status == true ? "Operação encerrada." : "Operação ainda em processo.");
                break;
        }
        return 0;
    }

    static boolean verificacao(String confirm, Scanner env){
        if(confirm.equalsIgnoreCase("s")){
            System.out.println("Você tem certeza que deseja confirmar sua ação? s/N");
            String decision = env.next();

            return decision.equalsIgnoreCase("s");
        }
        return false;
    }

    static String emergencia(Scanner env){
        System.out.println("Ativar botão de emergência? s/N");
        String decision = env.next();
        return decision;
    }
}
