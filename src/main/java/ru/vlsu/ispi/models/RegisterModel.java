package ru.vlsu.ispi.models;

import ru.vlsu.ispi.enums.Gender;
import ru.vlsu.ispi.enums.RoleType;

import javax.management.relation.Role;
import javax.validation.constraints.NotEmpty;

public class RegisterModel {
    @NotEmpty(message = "Provide a not empty email address")
    private String email;

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Provide a not empty nickname")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private RoleType role;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    private Gender gender;

    public Gender getGender() { return gender; };

    public void setGender(Gender gender){
        this.gender = gender;
    }

    @NotEmpty(message = "Provide a not empty password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Provide a not empty password confirmation")
    private String confirmpassword;

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    @NotEmpty(message = "Provide a not empty contact number")
    private String contactnumber;

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactNumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public RegisterModel(){
    }

    public RegisterModel(
            String email,
            String nickName,
            String contactNumber,
            RoleType role,
            Gender gender,
            String password,
            String confirmPassword){
        if (email == null || email == ""){
            throw new IllegalArgumentException("Wrong email string provided");
        }
        this.email = email;

        if (nickName == null || nickName == "") {
            throw new IllegalArgumentException("Wrong nick name string provided");
        }

        if (password == null || password == ""){
            throw new IllegalArgumentException("Wrong password string provided");
        }
        this.password = password;

        if (confirmPassword == null || confirmPassword == ""){
            throw new IllegalArgumentException("Wrong confirmation password string provided");
        }
        this.confirmpassword = confirmPassword;

        if (!password.equals(confirmpassword)){
            throw new IllegalArgumentException("Confirmation does not equals password!");
        }

        if (contactNumber == null || contactNumber == ""){
            this.contactnumber = "None";
        }
        else {
            this.contactnumber = contactNumber;
        }

        this.role = role;
        this.gender = gender;
    }
}
