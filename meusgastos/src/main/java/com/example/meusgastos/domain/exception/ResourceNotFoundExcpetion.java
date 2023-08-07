package com.example.meusgastos.domain.exception;

public class ResourceNotFoundExcpetion extends RuntimeException{
    public ResourceNotFoundExcpetion(String mensagem){
        super(mensagem);
    }
}
