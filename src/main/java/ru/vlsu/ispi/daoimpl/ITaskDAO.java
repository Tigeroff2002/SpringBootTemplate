package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ITaskDAO {
    public void Create(Task task, Connection conn) throws SQLException;
    public void Update(Task task, Connection conn) throws SQLException;
    public void Delete(Long id, Connection conn) throws SQLException;
    public Task FindTask(Long id, Connection conn) throws SQLException;
    public User GetExecutorByTaskId(Long id, Connection conn) throws SQLException;
    public List<Task> GetAllTasks(Connection conn) throws SQLException;
    public List<Event> getAllEventsByTaskId(Connection conn) throws SQLException;
}