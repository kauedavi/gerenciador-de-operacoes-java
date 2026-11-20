package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner env = new Scanner(System.in);

        int action;
        String[] statusRobo = Operacao.atualizarRobo("Inativo", "Desabilitado", "Offline");
        int[] dadosProducao = { 0, 0, 0, 0, 0 }; // [0]Total, [1]Azul, [2]Verde, [3]Vermelho, [4]Etapa
        double[] metricas = { 4.2, 98.5, 85.0, 96.0 }; // [0]Ciclo, [1]Acerto, [2]OEE, [3]Disponibilidade

        System.out.println("Deseja iniciar maquina? S/n");
        String iniciarPrograma = env.next();

        if (iniciarPrograma.equalsIgnoreCase("s")) {
            statusRobo = Operacao.atualizarRobo("Iniciando...", "Habilitado", "Online");
        }

        do {
            exibirInterface(statusRobo[0]);
            System.out.print("Ação: ");
            action = env.nextInt();
            env.nextLine();
            menu(action, env, statusRobo, dadosProducao, metricas);
        } while (action != 0);
    }

    static void exibirInterface(String statusLaboratorio) {
        System.out.println("================================");
        System.out.println("MENU DE OPERAÇÃO - LABORATÓRIO");
        System.out.println("================================");

        System.out.println("Status da maquina: " + statusLaboratorio);
        System.out.println("\n1 - Ações da maquina");
        System.out.println("2 - Status do Programa");
        System.out.println("3 - Registro de Logs em Tempo Real");
        System.out.println("4 - Botão de Parada de Emergência");
        System.out.println("0 - Sair");
    }

    static int menu(int action, Scanner env, String[] statusRobo, int[] dadosProducao, double[] metricas) {
        switch (action) {
            case 1:
                Operacao.operacaoLaboratorio(env, statusRobo, dadosProducao, metricas);
                break;

            case 2:
                System.out.println("Carregando relatório...");
                Operacao.relatorioRobo(statusRobo);
                pause(env);
                break;

            case 3:
                System.out.println("Status do desenvolvimento:");
                break;

            case 4:
                String emergencia = emergencia(env);
                boolean status = verificacao(emergencia, env);
                env.nextLine();
                if (status) {
                    Operacao.aplicarStatus(statusRobo, "PARADA DE EMERGÊNCIA", "Desabilitado", "Offline");
                    System.out.println("Operação encerrada por emergência.");
                } else {
                    System.out.println("Operação ainda em processo.");
                }
                pause(env);
                break;
        }
        return 0;
    }

    static boolean verificacao(String confirm, Scanner env) {
        if (confirm.equalsIgnoreCase("s")) {
            System.out.println("Você tem certeza que deseja confirmar sua ação? s/N");
            String decision = env.next();
            return decision.equalsIgnoreCase("s");
        }
        return false;
    }

    static String emergencia(Scanner env) {
        System.out.println("Ativar botão de emergência? s/N");
        String decision = env.next();
        return decision;
    }

    static void pause(Scanner env) {
        System.out.println("\nDeseja continuar? Pressione ENTER...");
        env.nextLine();
    }
}