package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Event;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.ITaskDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaskDAO implements ITaskDAO {
    private final DBContext context;

    public TaskDAO(DBContext context){
        if (context == null){
            throw new IllegalArgumentException("Context should not be null!");
        }
        this.context = context;
    }

    @Override
    public void Create(Task task, Connection conn) throws SQLException {

    }

    @Override
    public void Update(Task task, Connection conn) throws SQLException {

    }

    @Override
    public void Delete(Long id, Connection conn) throws SQLException {

    }

    @Override
    public Task FindTask(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public User GetExecutorByTaskId(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public List<Task> GetAllTasks(Connection conn) throws SQLException {
        return null;
    }

    @Override
    public List<Event> getAllEventsByTaskId(Connection conn) throws SQLException {
        return null;
    }
}
