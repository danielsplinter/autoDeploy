package br.com.automate.deploy;

import br.com.automate.deploy.configuracoes.Configuracoes;
import br.com.automate.deploy.git.Git;
import br.com.automate.deploy.processos.ManageBuild;
import br.com.automate.deploy.processos.ProcessosSistema;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main( String[] args ) {

        SwingUtilities.invokeLater(() -> {
            // Cria a janela
            JFrame frame = new JFrame("Auto Build");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 300);
            frame.setLocationRelativeTo(null);

            // Cria um JTextPane
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false); // torna o texto apenas para leitura

            // Cria um StyledDocument para adicionar estilos ao texto
            textPane.setBackground(Color.BLACK);
            StyledDocument doc = textPane.getStyledDocument();

            // Adiciona um estilo para o texto
            Style style = textPane.addStyle("ColorStyle", null);
            StyleConstants.setForeground(style, Color.GREEN); // define a cor do texto para vermelho

            // Adiciona um texto com estilo ao documento
            /*try {
                doc.insertString(doc.getLength(), "Texto colorido: ", style);
                doc.insertString(doc.getLength(), "Este texto é vermelho.", null); // texto sem estilo
            } catch (BadLocationException e) {
                e.printStackTrace();
            }*/

            // Adiciona o JTextPane a janela
            frame.add(new JScrollPane(textPane));
            frame.setVisible(true);

            Thread threadAtualizacao = new Thread(() -> {
                Configuracoes configuracoes = new Configuracoes();
                Git git = new Git(configuracoes,doc, style, textPane);
                ProcessosSistema processosSistema = new ProcessosSistema(configuracoes, doc, style, textPane);
                ManageBuild manageBuild = new ManageBuild(configuracoes, processosSistema);

                String[] comandoGit = {"git", "diff", "--name-only", "--pretty=format:\"%d\""};

                List<String> configuracao = configuracoes.getConfiguracao();

                git.execute(comandoGit).forEach(modulo -> {
                    manageBuild.executeBuild(modulo);
                });

                //manageBuild.executeBuild(configuracao.get(0));

            });
            threadAtualizacao.start();
        });


    }
}