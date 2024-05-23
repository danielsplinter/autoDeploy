package br.com.automate.deploy.processos;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.dto.configuracoes.ConfiguracoesDTO;
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

    ConfigManager configManager;
    StyledDocument doc;
    Style style;
    JTextPane textPane;

    public ProcessosSistema() {
    }

    public ProcessosSistema(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public ProcessosSistema(ConfigManager configManager, StyledDocument doc, Style style, JTextPane textPane) {
        this.configManager = configManager;
        this.doc = doc;
        this.style = style;
        this.textPane = textPane;
    }

    public Set<String> execute(String[] comandos){
        Set<String> logRetorno = new HashSet<>();
        String line = "";

        try {
            File directory = new File(getConfigManager().getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder());
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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
