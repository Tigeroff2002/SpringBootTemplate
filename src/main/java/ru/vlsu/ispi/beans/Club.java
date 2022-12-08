package ru.vlsu.ispi.beans;

public class Club extends BaseBean{
    private String name;
    public void setName(String _name)
    {
        name = _name;
    }
    public String getName()
    {
        return name;
    }

    public String toString(){
        return  "Номер клуба: " + super.getId() + ", название клуба " + this.name;
    }
}
