package br.com.automate.deploy;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.processos.git.ProcessoGit;
import br.com.automate.deploy.processos.ManageBuild;
import br.com.automate.deploy.processos.build.ProcessoBuild;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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
                ProcessoGit processoGit = new ProcessoGit(configManager,doc, style, textPane);

                //ProcessosSistema processosSistema = new ProcessosSistema(configuracoes);
                ProcessoBuild processoBuild = new ProcessoBuild(configManager, doc, style, textPane);
                ManageBuild manageBuild = new ManageBuild(configManager, processoBuild);

                List<String> comandoGit = new ArrayList<>(Arrays.asList());
                comandoGit.add("git");
                comandoGit.add("diff");
                comandoGit.add("--name-only");
                comandoGit.add("--pretty=format:\"%d\"");

                String modulos = processoGit.execute(comandoGit)
                        .stream()
                        .collect(Collectors.joining(","));

                String comandoMavenMontado = "mvn clean install -pl "+modulos+" -O -DskipTests; cd "+configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getModuloFinalEAR()+"; mvn clean install -O -DskipTests; cd ..";//teste

                try {
                    doc.insertString(doc.getLength(), comandoMavenMontado+"\n", style);
                    SwingUtilities.invokeLater(() -> {
                        textPane.setCaretPosition(doc.getLength());
                    });
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }

                manageBuild.executeBuild(modulos);

                //manageBuild.executeBuild(configuracao.get(0));
            });
            threadAtualizacao.start();
        });


    }
}