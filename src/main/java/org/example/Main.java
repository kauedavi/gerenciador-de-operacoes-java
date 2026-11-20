package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    // Códigos de cores ANSI para o terminal (RNF-08 e RNF-04)
    static final String RESET = "\u001B[0m";
    static final String VERMELHO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AMARELO = "\u001B[33m";
    static final String AZUL = "\u001B[34m";

    public static void main(String[] args) {
        Scanner env = new Scanner(System.in);

        int action;
        String[] statusRobo = Operacao.atualizarRobo("Inativo", "Desabilitado", "Offline");
        int[] dadosProducao = { 0, 0, 0, 0, 0 };
        double[] metricas = { 4.2, 98.5, 85.0, 96.0 };

        String[] logs = new String[100];
        String[] notificacoes = new String[50];
        int[] contadores = { 0, 0 };

        String[] usuario = realizarLogin(env);
        registrarLog(logs, contadores, "Usuário '" + usuario[0] + "' (" + usuario[1] + ") iniciou sessão.");

        System.out.println("\nDeseja iniciar maquina? S/n");
        String iniciarPrograma = env.next();

        if (iniciarPrograma.equalsIgnoreCase("s")) {
            Operacao.aplicarStatus(statusRobo, "Iniciando...", "Habilitado", "Online");
            registrarLog(logs, contadores, "Máquina iniciada. Programa Habilitado e comunicação Online.");
            registrarNotificacao(notificacoes, contadores, "Célula robótica inicializada com sucesso.");
        } else {
            registrarLog(logs, contadores, "Sistema aberto com a máquina em modo Inativo.");
        }

        do {
            exibirInterface(statusRobo, usuario, contadores[1]);
            System.out.print("Ação: ");
            action = env.nextInt();
            env.nextLine();
            menu(action, env, statusRobo, dadosProducao, metricas, logs, notificacoes, contadores, usuario);
        } while (action != 0);

        registrarLog(logs, contadores, "Logout realizado pelo usuário '" + usuario[0] + "'.");
        System.out.println("\nSessão encerrada (Logout). Até logo, " + usuario[0] + "!");
    }

    static String[] realizarLogin(Scanner env) {
        System.out.println("================================================");
        System.out.println("SENAI - SISTEMA DE MONITORAMENTO INTEGRADO");
        System.out.println("================================================");
        System.out.print("Nome do operador: ");
        String nome = env.nextLine();

        System.out.println("Selecione o perfil de acesso:");
        System.out.println("1 - Operador");
        System.out.println("2 - Administrador");
        System.out.print("Perfil: ");
        int opcaoPerfil = env.nextInt();
        env.nextLine();

        String perfil = (opcaoPerfil == 2) ? "Administrador" : "Operador";
        String[] usuario = { nome, perfil };
        return usuario;
    }

    static void exibirInterface(String[] statusRobo, String[] usuario, int totalNotificacoes) {
        String horaAtual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String corStatus = obterCorStatus(statusRobo[0]);

        System.out.println("\n================================================");
        System.out.println("SENAI | SISTEMA DE MONITORAMENTO INTEGRADO");
        System.out.println("Hora: " + horaAtual + " | Usuário: " + usuario[0] + " (" + usuario[1] + ")");
        System.out.println("Notificações pendentes: [" + totalNotificacoes + "]");
        System.out.println("================================================");

        System.out.println("Status da maquina: " + corStatus + statusRobo[0] + RESET);
        System.out.println("\n1 - Ações da maquina (Painel Operacional)");
        System.out.println("2 - Status do Programa e Comunicação");
        System.out.println("3 - Registro de Logs em Tempo Real");
        System.out.println(VERMELHO + "4 - [!] BOTÃO DE PARADA DE EMERGÊNCIA" + RESET);
        System.out.println("5 - Central de Notificações");
        System.out.println("6 - Configurações e Rearme (Restrito Admin)");
        System.out.println("0 - Sair (Logout)");
    }

    static int menu(int action, Scanner env, String[] statusRobo, int[] dadosProducao,
                    double[] metricas, String[] logs, String[] notificacoes,
                    int[] contadores, String[] usuario) {
        switch (action) {
            case 1:
                Operacao.operacaoLaboratorio(env, statusRobo, dadosProducao, metricas, logs, notificacoes, contadores);
                break;

            case 2:
                System.out.println("\nCarregando relatório...");
                Operacao.relatorioRobo(statusRobo);
                registrarLog(logs, contadores, "Consulta ao status do programa e comunicação.");
                pause(env);
                break;

            case 3:
                exibirLogs(logs, contadores[0]);
                pause(env);
                break;

            case 4:
                String emergencia = emergencia(env);
                boolean status = verificacao(emergencia, env);
                env.nextLine();
                if (status) {
                    Operacao.aplicarStatus(statusRobo, "PARADA DE EMERGÊNCIA", "Desabilitado", "Offline");
                    registrarLog(logs, contadores, "ALERTA CRÍTICO: Botão de Parada de Emergência acionado!");
                    registrarNotificacao(notificacoes, contadores, "EMERGÊNCIA ACIONADA! Célula interrompida.");
                    System.out.println(VERMELHO + "PARADA DE EMERGÊNCIA ATIVADA! Operação interrompida." + RESET);
                } else {
                    System.out.println("Operação ainda em processo.");
                }
                pause(env);
                break;

            case 5:
                exibirNotificacoes(notificacoes, contadores);
                pause(env);
                break;

            case 6:
                menuConfiguracoes(env, statusRobo, logs, notificacoes, contadores, usuario);
                break;

            case 0:
                break;

            default:
                System.out.println("Opção inválida.");
                break;
        }
        return 0;
    }

    static void menuConfiguracoes(Scanner env, String[] statusRobo, String[] logs,
                                  String[] notificacoes, int[] contadores, String[] usuario) {
        if (!usuario[1].equalsIgnoreCase("Administrador")) {
            System.out.println(VERMELHO + "\nAcesso negado! Apenas perfil Administrador pode acessar Configurações." + RESET);
            registrarLog(logs, contadores, "Tentativa de acesso não autorizado às Configurações por " + usuario[0] + ".");
            pause(env);
            return;
        }

        System.out.println("\n============================");
        System.out.println("CONFIGURAÇÕES DO SISTEMA");
        System.out.println("============================");
        System.out.println("1 - Rearmar / Reiniciar máquina");
        System.out.println("2 - Colocar máquina em modo Inativo");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");
        int opc = env.nextInt();
        env.nextLine();

        if (opc == 1) {
            Operacao.aplicarStatus(statusRobo, "Iniciando...", "Habilitado", "Online");
            registrarLog(logs, contadores, "Máquina rearmada pelo Administrador " + usuario[0] + ".");
            registrarNotificacao(notificacoes, contadores, "Sistema rearmado e pronto para operar.");
            System.out.println(VERDE + "Máquina rearmada e Online!" + RESET);
        } else if (opc == 2) {
            Operacao.aplicarStatus(statusRobo, "Inativo", "Desabilitado", "Offline");
            registrarLog(logs, contadores, "Máquina colocada em modo Inativo pelo Administrador.");
            System.out.println("Máquina colocada em modo Inativo.");
        }
        pause(env);
    }

    static void registrarLog(String[] logs, int[] contadores, String mensagem) {
        if (contadores[0] < logs.length) {
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            logs[contadores[0]] = "[" + hora + "] " + mensagem;
            contadores[0]++;
        }
    }

    static void exibirLogs(String[] logs, int totalLogs) {
        System.out.println("\n================================================");
        System.out.println("LOGS DO ROBÔ EM TEMPO REAL");
        System.out.println("================================================");
        if (totalLogs == 0) {
            System.out.println("Nenhum evento registrado até o momento.");
        } else {
            for (int i = 0; i < totalLogs; i++) {
                System.out.println(logs[i]);
            }
        }
    }

    static void registrarNotificacao(String[] notificacoes, int[] contadores, String alerta) {
        if (contadores[1] < notificacoes.length) {
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            notificacoes[contadores[1]] = "[" + hora + "] " + alerta;
            contadores[1]++;
        }
    }

    static void exibirNotificacoes(String[] notificacoes, int[] contadores) {
        System.out.println("\n============================");
        System.out.println("CENTRAL DE NOTIFICAÇÕES");
        System.out.println("============================");
        if (contadores[1] == 0) {
            System.out.println("Nenhuma notificação no momento.");
        } else {
            for (int i = 0; i < contadores[1]; i++) {
                System.out.println((i + 1) + ". " + notificacoes[i]);
            }
        }
    }

    static String obterCorStatus(String status) {
        if (status.equalsIgnoreCase("PARADA DE EMERGÊNCIA") || status.equalsIgnoreCase("Inativo")) {
            return VERMELHO;
        } else if (status.contains("Rampa") || status.equalsIgnoreCase("Iniciando...")) {
            return AZUL;
        }
        return VERDE;
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
        System.out.println(VERMELHO + "Ativar botão de emergência? s/N" + RESET);
        String decision = env.next();
        return decision;
    }

    static void pause(Scanner env) {
        System.out.println("\nDeseja continuar? Pressione ENTER...");
        env.nextLine();
    }
}