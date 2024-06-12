package br.com.automate.deploy.processos;

import java.util.List;
import java.util.Set;

public interface ProcessoExterno {
    int execute(List<String> comandos);
}
