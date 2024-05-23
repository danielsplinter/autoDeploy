package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.List;

public class ConfiguracoesDTO {

    @JsonProperty("pathMaven")
    private String pathMaven;

    @JsonProperty("wsadminPath")
    private String wsadminPath;// = "C:\\WebSphere\\AppServer\\bin\\wsadmin.bat";

    List<ProjectPerfil> projectPerfils;

    public ConfiguracoesDTO() {
        super();
    }

    public String getPathMaven() {
        return pathMaven;
    }

    public void setPathMaven(String pathMaven) {
        this.pathMaven = pathMaven;
    }

    public String getWsadminPath() {
        return wsadminPath;
    }

    public void setWsadminPath(String wsadminPath) {
        this.wsadminPath = wsadminPath;
    }

    public List<ProjectPerfil> getProjectPerfils() {
        return projectPerfils;
    }

    public void setProjectPerfils(List<ProjectPerfil> projectPerfils) {
        this.projectPerfils = projectPerfils;
    }
}
