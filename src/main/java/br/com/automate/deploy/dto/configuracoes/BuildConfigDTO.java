package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildConfigDTO {
    @JsonProperty("moduloFinalEAR")
    private String moduloFinalEAR;

    @JsonProperty("projectFolder")
    private String projectFolder;

    public String getModuloFinalEAR() {
        return moduloFinalEAR;
    }

    public void setModuloFinalEAR(String moduloFinalEAR) {
        this.moduloFinalEAR = moduloFinalEAR;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }
}
