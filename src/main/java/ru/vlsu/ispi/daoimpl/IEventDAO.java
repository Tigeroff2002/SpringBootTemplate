package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IEventDAO {
    public void Create(Event event, Connection conn) throws SQLException;
    public void Update(Event event, Connection conn) throws SQLException;
    public void Delete(Long id, Connection conn) throws SQLException;
    public Event FindEvent(Long id, Connection conn) throws SQLException;
    public Task GetTaskByEventId(Long id, Connection conn) throws SQLException;
    public User GetExecutorByEventId(Long id, Connection conn) throws SQLException;
    public User GetEmployerByEventId(Long id, Connection conn) throws SQLException;
    public List<Task> getAllEvents(Connection conn) throws SQLException;
}