package br.com.taskregister.exceptionhandler;

public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}
