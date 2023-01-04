package ru.vlsu.ispi.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

public class Sportsman extends BaseBean{
    private long clubId;
    @NotEmpty(message = "Provide a not empty name")
    private String name;
    private String gender;
    @NotNull(message = "Age should not be null")
    private Long age;
    public void setClubId(long _clubId){
        clubId = _clubId;
    }
    public long getClubId(){
        return clubId;
    }
    public String getName(){
        return name;
    }
    public void setName(String _name){
        name = _name;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String _gender){
        gender = _gender;
    }
    public Long getAge(){
        return age;
    }
    public void setAge(Long _age){
        age = _age;
    }
    public String toString(){
        return " Номер спортсмена: " + super.getId() + ", номер клуба:  " + this.clubId + ", имя: " + this.name + ", возраст " + this.age + " лет";
    }
}