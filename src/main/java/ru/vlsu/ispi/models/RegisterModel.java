package ru.vlsu.ispi.models;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class RegisterModel {

    public String Email;

    public String NickName;

    public boolean IsRoleChosen;

    public int RoleType;

    public String Password;

    public String ConfirmPassword;

    public String ContactNumber;

    public RegisterModel(){
        if (!IsRoleChosen){
            RoleType = 1;
        }
    }

    public RegisterModel(
            String email,
            String nickName,
            int roleType,
            String password,
            String confirmPassword,
            String contactNumber){
        if (email == null || email == ""){
            throw new IllegalArgumentException("Wrong email string provided");
        }
        Email = email;

        if (nickName == null || nickName == ""){
            NickName = Email;
        }
        else {
            NickName = nickName;
        }

        if (password == null || password == ""){
            throw new IllegalArgumentException("Wrong password string provided");
        }
        Password = password;

        if (confirmPassword == null || confirmPassword == ""){
            throw new IllegalArgumentException("Wrong confirmation password string provided");
        }
        ConfirmPassword = confirmPassword;

        if (!Password.equals(ConfirmPassword)){
            throw new IllegalArgumentException("Confirmation does not equals password!");
        }

        if (contactNumber == null || contactNumber == ""){
            ContactNumber = "None";
        }
        else {
            ContactNumber = contactNumber;
        }

        RoleType = roleType;
    }
}
