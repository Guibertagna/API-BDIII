package com.example.meusgastos.domain.dto.usuario;

public class LoginRequestDTO {
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private String senha;
    
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}