package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;

import java.util.HashMap;
import java.util.Map;

public class DBImplementation {
    public Map<Long, Club> clubs;
    public Map<Long, Sportsman> sportsmen;

    private DBImplementation(){
        this.clubs = new HashMap<Long, Club>();
        this.sportsmen = new HashMap<Long, Sportsman>();
    }
}
