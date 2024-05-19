package br.com.automate.deploy.exceptions;

public class ProcessStopException extends RuntimeException{
    public ProcessStopException(String mensagem){
        super(mensagem);
    }
}
