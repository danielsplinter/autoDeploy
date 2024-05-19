package br.com.automate.deploy.processos;

import br.com.automate.deploy.configuracoes.Configuracoes;
import br.com.automate.deploy.exceptions.ProcessStopException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ProcessosSistema {

    Configuracoes configuracoes;
    StyledDocument doc;
    Style style;
    JTextPane textPane;

    public ProcessosSistema() {
    }

    public ProcessosSistema(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }

    public ProcessosSistema(Configuracoes configuracoes, StyledDocument doc, Style style, JTextPane textPane) {
        this.configuracoes = configuracoes;
        this.doc = doc;
        this.style = style;
        this.textPane = textPane;
    }

    public Set<String> execute(String[] comandos){
        Set<String> logRetorno = new HashSet<>();
        String line = "";

        try {
            File directory = new File(getConfiguracoes().getConfiguracao().get(1));
            //Process process = new ProcessBuilder("git", "status").start();
            ProcessBuilder processBuilder = new ProcessBuilder(comandos);
            //processBuilder.directory(directory);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                doc.insertString(doc.getLength(), line+"\n", style);

                SwingUtilities.invokeLater(() -> {
                    textPane.setCaretPosition(doc.getLength());
                });
                //System.out.print(line+"\n");
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Comando executado com sucesso!");
            } else {
                System.out.println("Erro ao executar o comando. Código de saída: " + exitCode);
                throw new ProcessStopException("ERRO DURANTE O BUILD");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        return logRetorno;
    }

    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }
}
