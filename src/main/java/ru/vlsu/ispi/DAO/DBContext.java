package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.*;

import java.util.HashMap;
import java.util.Map;

public class DBContext {
    public Map<Long, User> Users;
    public Map<Long, Organization> Organizations;
    public Map<Long, Task> Tasks;
    public Map<Long, Event> Events;
    public Map<Long, Review> Reviews;

    private DBContext(){
        this.Users = new HashMap<Long, User>();
        this.Organizations = new HashMap<Long, Organization>();
        this.Tasks = new HashMap<Long, Task>();
        this.Events = new HashMap<Long, Event>();
        this.Reviews = new HashMap<Long, Review>();
    }
}