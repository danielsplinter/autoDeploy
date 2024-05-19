package br.com.automate.deploy.configuracoes;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Configuracoes {

    List<String> configuracao;

    public Configuracoes() {
        lerConfiguracoes();
    }

    public void lerConfiguracoes(){

        try {
            Path arquivo = Paths.get("config.conf");
            this.configuracao = Files.lines(arquivo)
                    .map(linha -> linha)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(List<String> configuracao) {
        this.configuracao = configuracao;
    }
}
