package ru.vlsu.ispi.models;

import javax.validation.constraints.NotEmpty;

public class LoginModel {

    @NotEmpty(message = "Provide a not empty email adress")
    private String email;

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @NotEmpty(message = "Provide a not empty password")
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean RememberLogin;

    public boolean isRememberLogin() {
        return RememberLogin;
    }

    public void setRememberLogin(boolean rememberLogin) {
        RememberLogin = rememberLogin;
    }

    public LoginModel(){

    }

    public LoginModel(String email, String password){
        this.email = email;
        this.password = password;
        RememberLogin = true;
    }
}

