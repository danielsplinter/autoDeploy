package br.com.automate.deploy.processos;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.processos.build.ProcessoBuild;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageBuild {
    ProcessoBuild processoBuild;
    ConfigManager configManager;

    public ManageBuild() {
        super();
    }

    public ManageBuild(ConfigManager configManager, ProcessoBuild processoBuild) {
        this.configManager = configManager;
        this.processoBuild = processoBuild;
    }

    public void executeBuild(String modulos){
        String mvnPath = configManager.getConfiguracoesDTO().getPathMaven();
        String comandoMavenMontado = "cmd /c mvn clean install -pl "+modulos+" -o -DskipTests";//teste
        //String comandoMavenMontado = "cmd /c mvn clean install  -o -DskipTests";//teste
        String projectFolder = configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder();
        String earFolder = configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getModuloFinalEAR();

        String vbScriptPath = "runMaven.vbs";
        String mavenCommand = "mvn clean install"; // Substitua pelo comando Maven desejado
        // Configura o comando para executar o VBScript com o comando Maven como parâmetro
        List<String> command = new ArrayList<>();
        command.add("cscript");
        command.add("//NoLogo");
        command.add(vbScriptPath);
        command.add(projectFolder);
        command.add(comandoMavenMontado);
        command.add(earFolder);


        //String comandoMavenMontado = getConfigManager().getConfiguracoesDTO().getPathMaven()+" clean install -f "+getConfigManager().getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder()+"\\"+modulos+"\\pom.xml -DskipTests";
        //String comandoMavenMontado = "mvn clean package install";
        //System.out.println(comandoMavenMontado);
        /*String[] comandoMaven = Stream.of(comandoMavenMontado.split(" "))
                .toArray(String[]::new);*/
        List<String> comandoMaven = Arrays.stream(comandoMavenMontado.split(" ")).collect(Collectors.toList());

        processoBuild.setDirectoryExecute(new File(projectFolder));
        int statusExitCode =  processoBuild.execute(comandoMaven);

        if(statusExitCode == 0){
            processoBuild.setDirectoryExecute(new File(earFolder));
            processoBuild.execute(comandoMaven);
        }
    }

    public void executeDeploy(String modulo){
        //String comandoMavenMontado = getConfiguracoes().getConfiguracao().get(2)+" clean package install -f "+getConfiguracoes().getConfiguracao().get(1)+"\\"+modulo+"\\pom.xml -DskipTests";
        //String comandoMavenMontado = "mvn clean package install";
        //System.out.println(comandoMavenMontado);
        /*String[] comandoMaven = Stream.of(comandoMavenMontado.split(" "))
                .toArray(String[]::new);

        processosSistema.execute(comandoMaven);*/
    }


    public ProcessoBuild getProcessosSistema() {
        return processoBuild;
    }

    public void setProcessosSistema(ProcessoBuild processoBuild) {
        this.processoBuild = processoBuild;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
