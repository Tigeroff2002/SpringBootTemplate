package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Event;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.IEventDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventDAO implements IEventDAO {
    private final DBContext context;

    public EventDAO(DBContext context){
        if (context == null){
            throw new IllegalArgumentException("Context should not be null!");
        }
        this.context = context;
    }

    @Override
    public void Create(Event event, Connection conn) throws SQLException {

    }

    @Override
    public void Update(Event event, Connection conn) throws SQLException {

    }

    @Override
    public void Delete(Long id, Connection conn) throws SQLException {

    }

    @Override
    public Event FindEvent(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public Task GetTaskByEventId(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public User GetExecutorByEventId(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public User GetEmployerByEventId(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public List<Task> getAllEvents(Connection conn) throws SQLException {
        return null;
    }
}