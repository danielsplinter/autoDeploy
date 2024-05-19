package br.com.automate.deploy.git;

import br.com.automate.deploy.configuracoes.Configuracoes;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Git {

    List<String> configuracao;
    Configuracoes configuracoes;
    StyledDocument doc;
    Style style;
    JTextPane textPane;

    public Git() {
        super();
    }

    public Git(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }

    public Git(Configuracoes configuracoes, StyledDocument doc, Style style, JTextPane textPane) {
        this.configuracao = configuracao;
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
            processBuilder.directory(directory);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                String path = new File(line).getParent();
                if(path != null ){
                    String[] partesPath = line.split("/");
                    logRetorno.add(partesPath[0]);
                }
                String[] partesPath = line.split("/");

                doc.insertString(doc.getLength(), line+"\n", style);

                SwingUtilities.invokeLater(() -> {
                    textPane.setCaretPosition(doc.getLength());
                });
                //System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Comando executado com sucesso!");
            } else {
                System.out.println("Erro ao executar o comando. Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        return logRetorno;
    }

    private boolean isModule(String file){
        return new File(file).isFile();
    }



    public List<String> getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(List<String> configuracao) {
        this.configuracao = configuracao;
    }

    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }
}
