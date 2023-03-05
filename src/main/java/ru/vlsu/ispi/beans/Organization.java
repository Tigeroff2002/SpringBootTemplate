package ru.vlsu.ispi.beans;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Organization extends BaseBean{
    @NotEmpty(message = "Provide a not empty org name")
    private String Name;
    public String getOrgName(){
        return Name;
    }
    public void setOrgName(String orgName){
        Name = orgName;
    }

    private String City;
    public String getCity() {
        return City;
    }
    public void setCity(String city){
        City = city;
    }

    private String OfficialSite;
    public String getOfficialSite(){
        return OfficialSite;
    }
    public void setOfficialSite(String officialSite){
        OfficialSite = officialSite;
    }
}