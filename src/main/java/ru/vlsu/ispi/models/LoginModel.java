package ru.vlsu.ispi.models;

import javax.validation.constraints.NotEmpty;

public class LoginModel {

    @NotEmpty(message = "Provide a not empty email adress")
    private String Email;

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        this.Email = email;
    }

    @NotEmpty(message = "Provide a not empty nickname")
    private String NickName;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    @NotEmpty(message = "Provide a not empty password")
    public String Password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
        Email = email;
        Password = password;
        RememberLogin = true;
    }
}

