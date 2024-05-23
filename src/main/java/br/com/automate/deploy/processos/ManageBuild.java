package br.com.automate.deploy.processos;

import br.com.automate.deploy.configuracoes.ConfigManager;
import br.com.automate.deploy.dto.configuracoes.ConfiguracoesDTO;

import java.util.stream.Stream;

public class ManageBuild {
    ProcessosSistema processosSistema;
    ConfigManager configManager;

    public ManageBuild() {
        super();
    }

    public ManageBuild(ConfigManager configManager, ProcessosSistema processosSistema) {
        this.configManager = configManager;
        this.processosSistema = processosSistema;
    }

    public void executeBuild(String modulo){
        String comandoMavenMontado = getConfigManager().getConfiguracoesDTO().getPathMaven()+" clean install -f "+getConfigManager().getConfiguracoesDTO().getProjectPerfils().get(0).getBuildConfigDTO().getProjectFolder()+"\\"+modulo+"\\pom.xml -DskipTests";
        //String comandoMavenMontado = "mvn clean package install";
        //System.out.println(comandoMavenMontado);
        String[] comandoMaven = Stream.of(comandoMavenMontado.split(" "))
                .toArray(String[]::new);

        processosSistema.execute(comandoMaven);
    }

    public void executeDeploy(String modulo){
        //String comandoMavenMontado = getConfiguracoes().getConfiguracao().get(2)+" clean package install -f "+getConfiguracoes().getConfiguracao().get(1)+"\\"+modulo+"\\pom.xml -DskipTests";
        //String comandoMavenMontado = "mvn clean package install";
        //System.out.println(comandoMavenMontado);
        /*String[] comandoMaven = Stream.of(comandoMavenMontado.split(" "))
                .toArray(String[]::new);

        processosSistema.execute(comandoMaven);*/
    }


    public ProcessosSistema getProcessosSistema() {
        return processosSistema;
    }

    public void setProcessosSistema(ProcessosSistema processosSistema) {
        this.processosSistema = processosSistema;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
