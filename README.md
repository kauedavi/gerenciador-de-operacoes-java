# Sistema de Monitoramento Integrado (CLI) — Célula Robótica SENAI

Sistema supervisório desenvolvido em **Java (Console/Terminal)** para monitoramento, controle de etapas, telemetria e segurança operacional de uma célula robótica de separação de peças por cor.

O projeto foi estruturado utilizando programação procedural/estruturada em Java (métodos `static`, arrays primitivos e controle de fluxo), mantendo toda a regra de negócio e interface divididas em apenas dois arquivos.

---

## Arquitetura do Projeto

Aviso: Documentação gerada por IA.

![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSShpOQvuwGaqD1vsnX68QdXVZX_CyrOMgqKKyUcNGqZw_vyGjdfaGNlNI&s=10)

### Decisões Técnicas
* **Gerenciamento de Estado via Arrays:** Os estados da máquina (`statusRobo`), indicadores de produção (`dadosProducao`), métricas de desempenho (`metricas`), histórico (`logs`) e alertas (`notificacoes`) são compartilhados entre os métodos através de arrays tipados.
* **Sinalização Visual no Terminal:** Utilização de códigos de escape ANSI para representar estados operacionais (Verde, Azul e Vermelho) diretamente no console.

---

## Requisitos Funcionais (RF) — Implementados e Adaptados

Todos os 19 requisitos funcionais especificados na matriz foram contemplados e adaptados para operação via terminal:

| ID | Requisito | Status | Como foi implementado no Terminal |
| :--- | :--- | :--- | :--- |
| **RF-01** | Botão de Parada de Emergência | Implementado | Opção `4` no menu principal com dupla confirmação (`s/N`) e interrupção imediata da operação. |
| **RF-02** | Exibição do Status do Robô | Implementado | Exibido no topo do menu principal e no painel operacional (ex.: *"Rampa 3 em execução"*), com cor indicativa. |
| **RF-03** | Status do Programa | Implementado | Indica se o controle está `"Habilitado"` ou `"Desabilitado"` na opção `2` (*Status do Programa e Comunicação*). |
| **RF-04** | Status da Comunicação | Implementado | Exibe o estado `"Online"` ou `"Offline"` da conexão com a célula robótica. |
| **RF-05** | Contador de Peças Processadas | Implementado | Contabiliza em tempo real o total de peças que completaram o ciclo de 3 rampas. |
| **RF-06** | Medição do Tempo de Ciclo | Implementado | Exibido no painel de estatísticas em segundos por peça (`4.2 seg/peça`). |
| **RF-07** | Indicador de Taxa de Acerto | Implementado | Apresenta o percentual de precisão na separação por cor (`98.5%`). |
| **RF-08** | Visualização de Câmera/Vídeo | Adaptado | Representação esquemática em ASCII da esteira, sensor óptico e câmera `CAM-01` na opção `3` do painel operacional. |
| **RF-09** | Indicador de Gravação de Vídeo | Adaptado | Sinalizador visual `[● REC]` em vermelho exibido no painel da câmera `CAM-01`. |
| **RF-10** | Status de Processamento da Câmera | Implementado | Indica `"Processando"` quando a comunicação está Online ou `"Offline"` quando parada. |
| **RF-11** | Registro de Logs em Tempo Real | Implementado | Opção `3` do menu principal; lista cronologicamente todas as ações executadas com timestamp (`HH:mm:ss`). |
| **RF-12** | Monitoramento da Eficiência (OEE) | Implementado | Calcula e exibe o percentual de Eficiência Global do Equipamento (`85.0%` iniciais, atualizado por ciclo). |
| **RF-13** | Métricas de Disponibilidade Mecânica | Implementado | Exibe o índice de disponibilidade mecânica do robô (`96.0%`) nas estatísticas. |
| **RF-14** | Painel de Monitoramento de Peças | Implementado | Detalha a distribuição de peças separadas por cor (**Azul**, **Verde** e **Vermelho**). |
| **RF-15** | Navegação por Sidebar | Adaptado | Substituída por navegação hierárquica via menus numéricos (Painel Geral, Operação da Célula e Configurações). |
| **RF-16** | Identificação e Logotipo da Instituição | Implementado | Cabeçalho fixo exibindo `SENAI | SISTEMA DE MONITORAMENTO INTEGRADO`. |
| **RF-17** | Relógio do Sistema | Implementado | Hora atual (`HH:mm:ss`) atualizada dinamicamente a cada renderização do menu principal via `LocalTime`. |
| **RF-18** | Central de Notificações | Implementado | Contador de alertas pendentes no cabeçalho e opção `5` para leitura das notificações do sistema. |
| **RF-19** | Perfil do Usuário e Logout | Implementado | Identificação do operador/administrador no início da execução, exibição no cabeçalho e opção `0` para Logout. |

---

## Requisitos Não Funcionais (RNF) — Implementados vs. Descartados

Devido à natureza do projeto (aplicação Java SE executada localmente em terminal, sem front-end web ou integração física com CLP), os requisitos não funcionais foram tratados da seguinte forma:

### Implementados / Adaptados
* **RNF-04 (Destaque Visual para Emergência):** Aplicado código ANSI vermelho (`\u001B[31m`) na opção de Parada de Emergência e nos alertas críticos.
* **RNF-08 (Codificação Visual por Cores):** Padronização de cores no console seguindo o padrão industrial: **Verde** (Online / Habilitado / Normal), **Azul** (Em execução / Rampas) e **Vermelho** (Parada de Emergência / Inativo / Offline).
* **RNF-10 (Controle de Acesso Baseado em Perfis):** Separação entre perfis `Operador` e `Administrador`. Apenas usuários com perfil `Administrador` podem acessar o menu de **Configurações** (opção `6`) para rearmar a máquina após uma parada de emergência.

### Descartados (Não aplicáveis ao escopo CLI)
* **RNF-01 (Interface no Modo Escuro):** Exclusivo de estilização CSS/Web; no terminal, o esquema de fundo depende da configuração do console do usuário.
* **RNF-02 (Latência do Video Streaming < 200 ms):** Não há transmissão real de vídeo ou câmera física conectada, apenas simulação esquemática em texto.
* **RNF-03 (Atualização de Telemetria < 100 ms via WebSocket/MQTT):** Aplicação síncrona em memória local, sem uso de brokers MQTT ou comunicação assíncrona em rede.
* **RNF-05 (Responsividade da GUI em Full HD):** Requisito específico de layout gráfico web (1920x1080).
* **RNF-06 (Disponibilidade do Web App >= 99,5%):** Execução local via linha de comando, sem hospedagem em servidor web.
* **RNF-07 (Arquitetura Web em Navegadores):** Projeto desenvolvido em Java puro para terminal, sem interface HTML/JS.
* **RNF-09 (Persistência de Logs em Banco de Dados):** Os logs são mantidos em memória (`String[]`) durante o ciclo de vida da execução, sem conexão com SGBD externo.
* **RNF-11 (Tempo de Resposta da Emergência < 20 ms ao CLP):** Não há integração física com CLP ou atuadores reais; a interrupção ocorre imediatamente no estado em memória da aplicação.

---