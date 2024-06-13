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

    private File directoryExecute;

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
    public int execute(List<String> comandos){
        String line = "";
        int exitProcessCode = 0;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(comandos);
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(getDirectoryExecute());

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                doc.insertString(doc.getLength(), line+"\n", style);

                SwingUtilities.invokeLater(() -> {
                    textPane.setCaretPosition(doc.getLength());
                });
                //System.out.print(line+"\n");
            }

            exitProcessCode = process.waitFor();

            if (exitProcessCode != 0) {
                System.out.println("Erro ao executar o comando. Código de saída: " + exitProcessCode);
                throw new ProcessStopException("ERRO DURANTE O BUILD");
            }

            System.out.println("Comando executado com sucesso!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        return exitProcessCode;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public File getDirectoryExecute() {
        return directoryExecute;
    }

    public void setDirectoryExecute(File directoryExecute) {
        this.directoryExecute = directoryExecute;
    }
}
