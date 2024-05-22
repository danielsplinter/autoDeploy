package br.com.automate.deploy.dto.configuracoes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Objects;

public class DeployConfigDTO {

    @JsonProperty("wsadminPath")
    private String wsadminPath;// = "C:\\WebSphere\\AppServer\\bin\\wsadmin.bat";

    // Nome do aplicativo e caminho para o arquivo EAR
    @JsonProperty("appName")
    private String appName;// = "analise-ejb-ear";

    @JsonProperty("earPath")
    private String earPath;// = "C:\\path\\to\\your\\analise-ejb-ear.ear";

    // Script Jython embutido no comando
    @JsonProperty("jythonCommand")
    private String jythonCommand = String.format("AdminApp.update('%s', 'app', '[ -operation update -contents %s ]'); AdminConfig.save();", appName, earPath);

    @JsonProperty("diretorio")
    private File diretorio;// = new File("C:\\WebSphere\\AppServer\\bin");

    public DeployConfigDTO() {
        super();
    }

    public DeployConfigDTO(String wsadminPath, String appName, String earPath, String jythonCommand, File diretorio) {
        this.wsadminPath = wsadminPath;
        this.appName = appName;
        this.earPath = earPath;
        this.jythonCommand = jythonCommand;
        this.diretorio = diretorio;
    }

    public String getWsadminPath() {
        return wsadminPath;
    }

    public void setWsadminPath(String wsadminPath) {
        this.wsadminPath = wsadminPath;
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

    public File getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(File diretorio) {
        this.diretorio = diretorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeployConfigDTO that = (DeployConfigDTO) o;
        return wsadminPath.equals(that.wsadminPath) && appName.equals(that.appName) && earPath.equals(that.earPath) && jythonCommand.equals(that.jythonCommand) && diretorio.equals(that.diretorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wsadminPath, appName, earPath, jythonCommand, diretorio);
    }
}
