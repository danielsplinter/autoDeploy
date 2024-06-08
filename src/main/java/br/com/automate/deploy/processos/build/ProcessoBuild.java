package br.com.automate.deploy.processos.build;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.exceptions.ProcessStopException;
import br.com.automate.deploy.processos.ProcessoExterno;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessoBuild implements ProcessoExterno {

    private ConfigManager configManager;
    private StyledDocument doc;
    private Style style;
    private JTextPane textPane;

    public ProcessoBuild() {
    }

    public ProcessoBuild(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public ProcessoBuild(ConfigManager configManager, StyledDocument doc, Style style, JTextPane textPane) {
        this.configManager = configManager;
        this.doc = doc;
        this.style = style;
        this.textPane = textPane;
    }

    @Override
    public List<String> execute(String[] comandos){
        List<String> logRetorno = new ArrayList<>();
        String line = "";

        try {
            File directory = new File(getConfigManager().getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder());
            ProcessBuilder processBuilder = new ProcessBuilder(comandos);
            processBuilder.directory(directory);

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

            if (exitCode != 0) {
                System.out.println("Erro ao executar o comando. Código de saída: " + exitCode);
                throw new ProcessStopException("ERRO DURANTE O BUILD");
            }

            System.out.println("Comando executado com sucesso!");
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
