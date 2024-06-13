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
        String comandoMavenMontadoModulosAlterados = "cmd /c mvn clean install -pl "+modulos+" -o -DskipTests";//teste
        String comandoMavenModuloEAR = "cmd /c mvn clean install -o -DskipTests";//teste
        String projectFolder = configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder();
        String earFolder = configManager.getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getModuloFinalEAR();

        List<String> comandoMavenModulosAlteradosParametro = Arrays.stream(comandoMavenMontadoModulosAlterados.split(" ")).collect(Collectors.toList());

        processoBuild.setDirectoryExecute(new File(projectFolder));
        int statusExitCode =  processoBuild.execute(comandoMavenModulosAlteradosParametro);

        if(statusExitCode == 0){
            List<String> comandoMavenEARParametros = Arrays.stream(comandoMavenModuloEAR.split(" ")).collect(Collectors.toList());
            processoBuild.setDirectoryExecute(new File(projectFolder+earFolder));
            processoBuild.execute(comandoMavenEARParametros);
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
