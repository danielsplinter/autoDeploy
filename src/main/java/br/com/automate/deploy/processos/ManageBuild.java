package br.com.automate.deploy.processos;

import br.com.automate.deploy.configuracoes.Configuracoes;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.util.stream.Stream;

public class ManageBuild {
    Configuracoes configuracoes;
    ProcessosSistema processosSistema;

    public ManageBuild() {
        super();
    }

    public ManageBuild(Configuracoes configuracoes, ProcessosSistema processosSistema) {
        this.configuracoes = configuracoes;
        this.processosSistema = processosSistema;
    }

    public void executeBuild(String modulo){
        String comandoMavenMontado = getConfiguracoes().getConfiguracao().get(2)+" clean package install -f "+getConfiguracoes().getConfiguracao().get(1)+"\\"+modulo+"\\pom.xml -DskipTests";
        //String comandoMavenMontado = "mvn clean package install";
        //System.out.println(comandoMavenMontado);
        String[] comandoMaven = Stream.of(comandoMavenMontado.split(" "))
                .toArray(String[]::new);

        processosSistema.execute(comandoMaven);
    }



    public Configuracoes getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }

    public ProcessosSistema getProcessosSistema() {
        return processosSistema;
    }

    public void setProcessosSistema(ProcessosSistema processosSistema) {
        this.processosSistema = processosSistema;
    }
}
