package br.com.automate.deploy;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.dto.configuracoes.ConfiguracoesDTO;
import br.com.automate.deploy.git.Git;
import br.com.automate.deploy.processos.ManageBuild;
import br.com.automate.deploy.processos.ProcessosSistema;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main( String[] args ) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Auto Build");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 300);
            frame.setLocationRelativeTo(null);

            JTextPane textPane = new JTextPane();
            textPane.setEditable(false); // torna o texto apenas para leitura

            // Cria um StyledDocument para adicionar estilos ao texto
            textPane.setBackground(Color.BLACK);
            StyledDocument doc = textPane.getStyledDocument();

            // Adiciona um estilo para o texto
            Style style = textPane.addStyle("ColorStyle", null);
            StyleConstants.setForeground(style, Color.GREEN); // define a cor do texto para vermelho

            frame.add(new JScrollPane(textPane));
            frame.setVisible(true);

            Thread threadAtualizacao = new Thread(() -> {
                ConfigManager configManager = new ConfigManager();

                //Git git = new Git(configuracoes);
                Git git = new Git(configManager,doc, style, textPane);

                //ProcessosSistema processosSistema = new ProcessosSistema(configuracoes);
                ProcessosSistema processosSistema = new ProcessosSistema(configManager, doc, style, textPane);
                ManageBuild manageBuild = new ManageBuild(configManager, processosSistema);

                String[] comandoGit = {"git", "diff", "--name-only", "--pretty=format:\"%d\""};

                //List<String> configuracao = configuracoesDTO.getConfiguracao();

                /*
                git.execute(comandoGit).forEach(modulo -> {
                    //Montar uma string com uma lista de modulos no comando abaixo e passar para executeBuild(mdulo)
                    //mvn clean package install -pl src -DskipTests este comando deve ser montado dentro de executeBuild(mdulo)
                    manageBuild.executeBuild(modulo);
                });*/

                String modulos = git.execute(comandoGit).stream()
                        .collect(Collectors.joining(","));
                String comandoMavenMontado = configManager.getConfiguracoesDTO().getPathMaven()+" clean install -pl "+modulos+" -DskipTests; cd "+configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getModuloFinalEAR()+"; mvn clean install -DskipTests; cd ..";//teste
                try {
                    doc.insertString(doc.getLength(), comandoMavenMontado+"\n", style);
                    SwingUtilities.invokeLater(() -> {
                        textPane.setCaretPosition(doc.getLength());
                    });
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }

                //manageBuild.executeBuild(modulos);

                //manageBuild.executeBuild(configuracao.get(0));

            });
            threadAtualizacao.start();
        });


    }
}