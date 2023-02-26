package ru.vlsu.ispi.models;

public class LoginModel {

    public String Email;

    public String NickName;

    public String Password;

    public boolean RememberLogin;

    public LoginModel(){

    }

    public LoginModel(String email, String password){
        Email = email;
        Password = password;
        RememberLogin = true;
    }
}

