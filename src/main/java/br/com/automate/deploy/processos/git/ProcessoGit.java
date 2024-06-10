package br.com.automate.deploy.processos.git;

import br.com.automate.deploy.configuracoes.ConfigManager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessoGit implements ProcessoExterno {
    private ConfigManager configManager;
    private StyledDocument doc;
    private Style style;
    private JTextPane textPane;

    public ProcessoGit() {
        super();
    }

    public ProcessoGit(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public ProcessoGit(ConfigManager configManager, StyledDocument doc, Style style, JTextPane textPane) {
        this.configManager = configManager;
        this.doc = doc;
        this.style = style;
        this.textPane = textPane;
    }

    @Override
    public List<String> execute(List<String> comandos){
        Set<String> logProcessoGit = new HashSet<>();
        String line = "";

        try {
            File directory = new File(getConfigManager().getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder());
            ProcessBuilder processBuilder = new ProcessBuilder(comandos);
            processBuilder.directory(directory);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                String path = new File(line).getParent();
                if(path != null ){
                    String[] partesPath = line.split("/");
                    logProcessoGit.add(partesPath[0]);
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

        return logProcessoGit.stream().collect(Collectors.toList());
    }

    private boolean isModule(String file){
        return new File(file).isFile();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
