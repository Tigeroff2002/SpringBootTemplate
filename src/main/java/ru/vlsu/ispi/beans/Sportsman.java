package ru.vlsu.ispi.beans;

public class Sportsman extends BaseBean{
    private long clubId;
    private String name;
    private int age;
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
    public int getAge(){
        return age;
    }
    public void setAge(int _age){
        age = _age;
    }
    public String toString(){
        return " Номер спортсмена: " + super.getId() + ", номер клуба:  " + this.clubId + ", имя: " + this.name + ", возраст " + this.age + " лет";
    }
}