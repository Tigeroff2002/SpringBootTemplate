package ru.vlsu.ispi.beans;

import ru.vlsu.ispi.enums.RoleType;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User extends BaseBean{
    private int RoleId;
    public int getRoleId(){
        return RoleId;
    }
    public void setRoleId(int roleId){
        RoleId = roleId;
    }

    private RoleType Role;

    public RoleType getRole() {
        return Role;
    }

    public void setRole(RoleType role) {
        Role = role;
    }

    private String Email;
    public String getEmail(){
        return Email;
    }
    public void setEmail(String email){
        Email = email;
    }

    @NotEmpty(message = "Provide a not empty nickname")
    private String NickName;
    public String getNickName(){
        return NickName;
    }
    public void setNickName(String nickName){
        NickName = nickName;
    }

    private String Password;
    public String getPassword(){
        return Password;
    }
    public void setPassword(String password){
        Password = password;
    }

    private String Gender;
    public String getGender(){
        return Gender;
    }
    public void setGender(String gender){
        Gender = gender;
    }

    private String ContactNumber;
    public String getContactNumber(){
        return ContactNumber;
    }
    public void setContactNumber(String contactNumber){
        ContactNumber = contactNumber;
    }

    private Date RegisterDate;
    public Date getRegisterDate(){
        return RegisterDate;
    }
    public void setRegisterDate(Date registerDate){
        RegisterDate = registerDate;
    }

    private Date BirthdayDate;
    public Date getBirthdayDate(){
        return BirthdayDate;
    }
    public void setBirthdayDate(Date birthdayDate){
        BirthdayDate = birthdayDate;
    }

    private float Rating;
    public float getRating(){
        return Rating;
    }
    public void setRating(float rating){
        Rating = rating;
    }

    private String Resume;
    public String getResume(){
        return Resume;
    }
    public void setResume(String resume){
        Resume = resume;
    }

    private float Balance;
    public float getBalance(){
        return Balance;
    }
    public void setBalance(float balance){
        Balance = balance;
    }

    private float Bonus;
    public float getBonus(){
        return Bonus;
    }
    public void setBonus(float bonus){
        Bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Email, user.Email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Email);
    }
}