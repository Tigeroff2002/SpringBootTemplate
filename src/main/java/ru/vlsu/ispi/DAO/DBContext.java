package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.beans.User;

import java.util.HashMap;
import java.util.Map;

public class DBContext {
    public Map<Long, User> Users;
    public Map<Long, Sportsman> Sportsmen;
    public Map<Long, Club> Clubs;

    private DBContext(){
        this.Users = new HashMap<Long, User>();
        this.Sportsmen = new HashMap<>();
        this.Clubs = new HashMap<>();
    }
}