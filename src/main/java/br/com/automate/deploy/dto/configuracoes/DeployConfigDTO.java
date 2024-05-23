package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Objects;

public class DeployConfigDTO {
    // Nome do aplicativo e caminho para o arquivo EAR
    @JsonProperty("appName")
    private String appName;// = "analise-ejb-ear";

    @JsonProperty("earPath")
    private String earPath;// = "C:\\path\\to\\your\\analise-ejb-ear.ear";

    // Script Jython embutido no comando
    @JsonProperty("jythonCommand")
    private String jythonCommand = String.format("AdminApp.update('%s', 'app', '[ -operation update -contents %s ]'); AdminConfig.save();", appName, earPath);


    public DeployConfigDTO() {
        super();
    }

    public DeployConfigDTO(String wsadminPath, String appName, String earPath, String jythonCommand, File diretorio) {
        this.appName = appName;
        this.earPath = earPath;
        this.jythonCommand = jythonCommand;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEarPath() {
        return earPath;
    }

    public void setEarPath(String earPath) {
        this.earPath = earPath;
    }

    public String getJythonCommand() {
        return jythonCommand;
    }

    public void setJythonCommand(String jythonCommand) {
        this.jythonCommand = jythonCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeployConfigDTO that = (DeployConfigDTO) o;
        return appName.equals(that.appName) && earPath.equals(that.earPath) && jythonCommand.equals(that.jythonCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appName, earPath, jythonCommand);
    }
}
