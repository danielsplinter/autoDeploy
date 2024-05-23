package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ProjectPerfil {
    @JsonProperty("buildConfig")
    private BuildConfigDTO buildConfigDTO;
    @JsonProperty("deployConfig")
    private DeployConfigDTO deployConfigDTO;

    public BuildConfigDTO getBuildConfigDTO() {
        return buildConfigDTO;
    }

    public void setBuildConfigDTO(BuildConfigDTO buildConfigDTO) {
        this.buildConfigDTO = buildConfigDTO;
    }

    public DeployConfigDTO getDeployConfigDTO() {
        return deployConfigDTO;
    }

    public void setDeployConfigDTO(DeployConfigDTO deployConfigDTO) {
        this.deployConfigDTO = deployConfigDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPerfil that = (ProjectPerfil) o;
        return buildConfigDTO.equals(that.buildConfigDTO) && deployConfigDTO.equals(that.deployConfigDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildConfigDTO, deployConfigDTO);
    }
}
