package org.example;

import java.util.Scanner;

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

    static String[] operacaoLaboratorio(Scanner env, String[] statusRobo, int[] dadosProducao, double[] metricas) {
        if (statusRobo[0].equalsIgnoreCase("Inativo") || statusRobo[0].equalsIgnoreCase("PARADA DE EMERGÊNCIA")) {
            System.out.println("\nOperação bloqueada! Status atual: " + statusRobo[0]);
            Main.pause(env);
            return statusRobo;
        }

        int opcao;
        do {
            System.out.println("\n============================");
            System.out.println("PAINEL DA CÉLULA ROBÓTICA");
            System.out.println("Status atual: " + statusRobo[0]);
            System.out.println("============================");
            exibirMenuOperacao();
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha: ");
            opcao = env.nextInt();
            env.nextLine();

            switch (opcao) {
                case 1:
                    avancarEtapa(statusRobo, dadosProducao, metricas);
                    break;
                case 2:
                    consultarEstatisticas(dadosProducao, metricas);
                    Main.pause(env);
                    break;
                case 3:
                    exibirCamera(statusRobo, dadosProducao);
                    Main.pause(env);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        return statusRobo;
    }

    static void avancarEtapa(String[] statusRobo, int[] dadosProducao, double[] metricas) {
        dadosProducao[4]++;

        if (dadosProducao[4] == 1) {
            statusRobo[0] = "Rampa 1 - Alimentação de peça";
        } else if (dadosProducao[4] == 2) {
            statusRobo[0] = "Rampa 2 - Leitura de cor (CAM-01)";
        } else if (dadosProducao[4] == 3) {
            statusRobo[0] = "Rampa 3 em execução";
        } else {
            dadosProducao[4] = 0;
            dadosProducao[0]++;

            String corDetectada;
            if (dadosProducao[0] % 3 == 1) {
                dadosProducao[1]++;
                corDetectada = "Azul";
            } else if (dadosProducao[0] % 3 == 2) {
                dadosProducao[2]++;
                corDetectada = "Verde";
            } else {
                dadosProducao[3]++;
                corDetectada = "Vermelho";
            }

            metricas[2] = Math.min(99.0, metricas[2] + 0.5);
            statusRobo[0] = "Ciclo concluído - Peça " + corDetectada + " separada";
        }

        System.out.println(">> Etapa atualizada: " + statusRobo[0]);
    }

    static void consultarEstatisticas(int[] dadosProducao, double[] metricas) {
        System.out.println("\n================================================");
        System.out.println("ESTATÍSTICAS E MONITORAMENTO DE PEÇAS");
        System.out.println("================================================");
        System.out.println("Total de peças processadas: " + dadosProducao[0] + " peças");
        System.out.println("Tempo médio de ciclo: " + metricas[0] + " seg/peça");
        System.out.println("Taxa de acerto (separação): " + metricas[1] + "%");
        System.out.println("Eficiência Global (OEE): " + metricas[2] + "%");
        System.out.println("Disponibilidade mecânica: " + metricas[3] + "%");
        System.out.println("------------------------------------------------");
        System.out.println("DISTRIBUIÇÃO POR COR:");
        System.out.println("  - Peças Azuis:     " + dadosProducao[1]);
        System.out.println("  - Peças Verdes:    " + dadosProducao[2]);
        System.out.println("  - Peças Vermelhas: " + dadosProducao[3]);
        System.out.println("================================================");
    }

    static void exibirCamera(String[] statusRobo, int[] dadosProducao) {
        String statusVisao = statusRobo[2].equalsIgnoreCase("Online") ? "Processando" : "Offline";

        System.out.println("\n================================================");
        System.out.println("ESQUEMÁTICO DA CÉLULA - CÂMERA [CAM-01]");
        System.out.println("[● REC] | Visão Computacional: " + statusVisao);
        System.out.println("================================================");
        System.out.println("  +------------------------------------------+");
        System.out.println("  |  [CAM-01] --->  ( Sensor Óptico )        |");
        System.out.println("  |  Esteira: ====[ PEÇA ]====> Rampa 1/2/3  |");
        System.out.println("  |  Estado: " + statusRobo[0]);
        System.out.println("  |  Peças inspecionadas: " + dadosProducao[0]);
        System.out.println("  +------------------------------------------+");
    }

    static void exibirMenuOperacao() {
        System.out.println("1 - ir para proxima etapa");
        System.out.println("2 - consultar estatisticas");
        System.out.println("3 - Exibir imagens da camera");
    }
}