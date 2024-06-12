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

    public static void runMavenScript(String[] comandos) throws Exception {
        String vbScriptPath = "C:\\caminho\\para\\seu\\runMaven.vbs";
        String mavenCommand = "mvn clean install"; // Substitua pelo comando Maven desejado
        // Configura o comando para executar o VBScript com o comando Maven como parâmetro
        List<String> command = new ArrayList<>();
        command.add("cscript");
        command.add("//NoLogo");
        command.add(vbScriptPath);
        command.add(mavenCommand);

        // Configura o ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Redireciona o erro para a saída padrão

        // Inicia o processo
        Process process = processBuilder.start();

        // Captura a saída do script
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Lê a saída padrão do comando linha por linha
        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s); // Imprime cada linha da saída
        }

        // Espera o processo terminar e captura o código de retorno
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Maven build succeeded.");
        } else {
            System.out.println("Maven build failed with exit code: " + exitCode);
        }
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
