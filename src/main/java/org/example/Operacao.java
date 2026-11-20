package org.example;

public class Operacao {

    static void relatorioRobo(String[] statusRobo) {
        if (!statusRobo[0].equalsIgnoreCase("inativo")) {
            System.out.println("============================");
            System.out.println("RELATÓRIO DE OPERAÇÃO DO ROBO:");
            System.out.println("============================");
            System.out.println("Status do Robô: " + statusRobo[0]);
            System.out.println("Programa de controle do robô: " + statusRobo[1]);
            System.out.println("Status de comunicação: " + statusRobo[2]);
        } else {
            System.out.println("Esse robô está fora de operação.");
        }
    }

    static String[] atualizarRobo(String statusRobo, String controleRobo, String comunicacaoRobo) {
        String[] robo = { statusRobo, controleRobo, comunicacaoRobo };
        return robo;
    }

    static void aplicarStatus(String[] statusRobo, String estado, String controle, String comunicacao) {
        statusRobo[0] = estado;
        statusRobo[1] = controle;
        statusRobo[2] = comunicacao;
    }

    static void exibirMenuOperacao() {
        System.out.println("1 - ir para proxima etapa");
        System.out.println("2 - consultar estatisticas");
        System.out.println("3 - Exibir imagens da camera");
    }
}