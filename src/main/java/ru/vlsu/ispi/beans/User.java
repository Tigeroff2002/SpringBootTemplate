package ru.vlsu.ispi.beans;

import ru.vlsu.ispi.enums.RoleType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User extends BaseBean{
    public RoleType RoleId;
    public int getRoleId(){
        return RoleId.getValue();
    }
    public void setRoleId(int roleId){
        switch (roleId)
        {
            case 1:
                RoleId = RoleType.User;
                break;
            case 2:
                RoleId = RoleType.Manager;
                break;
            case 3:
                RoleId = RoleType.Admin;
                break;
        }
    }

    public String Email;
    public String getEmail(){
        return Email;
    }
    public void setEmail(String email){
        Email = email;
    }

    public String NickName;
    public String getNickName(){
        return NickName;
    }
    public void setNickName(String nickName){
        NickName = nickName;
    }

    public String Password;
    public String getPassword(){
        return Password;
    }
    public void setPassword(String password){
        Password = password;
    }

    public String Gender;
    public String getGender(){
        return Gender;
    }
    public void setGender(String gender){
        Gender = gender;
    }

    public String ContactNumber;
    public String getContactNumber(){
        return ContactNumber;
    }
    public void setContactNumber(String contactNumber){
        ContactNumber = contactNumber;
    }

    public Date RegisterDate;
    public Date getRegisterDate(){
        return RegisterDate;
    }
    public void setRegisterDate(Date registerDate){
        RegisterDate = registerDate;
    }


    public Date BirthdayDate;
    public Date getBirthdayDate(){
        return BirthdayDate;
    }
    public void setBirthdayDate(Date birthdayDate){
        BirthdayDate = birthdayDate;
    }

    public float Rating;
    public float getRating(){
        return Rating;
    }
    public void setRating(float rating){
        Rating = rating;
    }

    public String Resume;
    public String getResume(){
        return Resume;
    }
    public void setResume(String resume){
        Resume = resume;
    }

    public float Balance;
    public float getBalance(){
        return Balance;
    }
    public void setBalance(float balance){
        Balance = balance;
    }

    public float Bonus;
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