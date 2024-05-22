package br.com.automate.deploy.configuracoes;

import br.com.automate.deploy.dto.configuracoes.ConfiguracoesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private ConfiguracoesDTO configuracoesDTO;

    public ConfigManager(){
        lerConfiguracoes();
    }

    public void lerConfiguracoes(){
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "configuracoes.json";

        try {
            configuracoesDTO = objectMapper.readValue(new File(filePath), ConfiguracoesDTO.class);
            System.out.println("Path Maven: " + configuracoesDTO.getPathMaven());
            System.out.println("Build Config - Modulo Final EAR: " + configuracoesDTO.getBuildConfig().getModuloFinalEAR());
            System.out.println("Build Config - Project Folder: " + configuracoesDTO.getBuildConfig().getProjectFolder());
            System.out.println("Deploy Config - wsadmin Path: " + configuracoesDTO.getDeployConfig().getWsadminPath());
            System.out.println("Deploy Config - App Name: " + configuracoesDTO.getDeployConfig().getAppName());
            System.out.println("Deploy Config - EAR Path: " + configuracoesDTO.getDeployConfig().getEarPath());
            System.out.println("Deploy Config - Jython Command: " + configuracoesDTO.getDeployConfig().getJythonCommand());
            System.out.println("Deploy Config - Diretorio: " + configuracoesDTO.getDeployConfig().getDiretorio());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ConfiguracoesDTO getConfiguracoesDTO() {
        return configuracoesDTO;
    }

    public void setConfiguracoesDTO(ConfiguracoesDTO configuracoesDTO) {
        this.configuracoesDTO = configuracoesDTO;
    }
}
