package ru.vlsu.ispi.models;

import javax.validation.constraints.NotEmpty;

public class RegisterModel {
    @NotEmpty(message = "Provide a not empty email address")
    private String Email;

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @NotEmpty(message = "Provide a not empty nickname")
    private String NickName;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    private boolean IsRoleChosen;

    public boolean isRoleChosen() {
        return IsRoleChosen;
    }

    public void setRoleChosen(boolean roleChosen) {
        IsRoleChosen = roleChosen;
    }

    private int RoleType;

    public int getRoleType() {
        return RoleType;
    }

    public void setRoleType(int roleType) {
        RoleType = roleType;
    }

    @NotEmpty(message = "Provide a not empty password")
    private String Password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @NotEmpty(message = "Provide a not empty password confirmation")
    private String ConfirmPassword;

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    @NotEmpty(message = "Provide a not empty contact number")
    private String ContactNumber;

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

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

        if (nickName == null || nickName == "") {
            throw new IllegalArgumentException("Wrong nick name string provided");
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
