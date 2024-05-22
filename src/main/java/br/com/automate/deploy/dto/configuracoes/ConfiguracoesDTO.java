package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfiguracoesDTO {

    @JsonProperty("pathMaven")
    private String pathMaven;

    @JsonProperty("buildConfig")
    private BuildConfigDTO buildConfigDTO;
    @JsonProperty("deployConfig")
    private DeployConfigDTO deployConfigDTO;

    public ConfiguracoesDTO() {
        super();
    }



    public DeployConfigDTO getDeployConfig() {
        return deployConfigDTO;
    }

    public void setDeployConfig(DeployConfigDTO deployConfigDTO) {
        this.deployConfigDTO = deployConfigDTO;
    }

    public String getPathMaven() {
        return pathMaven;
    }

    public void setPathMaven(String pathMaven) {
        this.pathMaven = pathMaven;
    }

    public BuildConfigDTO getBuildConfig() {
        return buildConfigDTO;
    }

    public void setBuildConfig(BuildConfigDTO buildConfigDTO) {
        this.buildConfigDTO = buildConfigDTO;
    }
}
