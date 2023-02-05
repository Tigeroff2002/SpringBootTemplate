package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.RoleType;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;


public interface IUserDAO {
    public void Create(User user, Connection conn) throws SQLException;
    public void Update(User user, Connection conn) throws SQLException;
    public void Delete(Long id, Connection conn) throws SQLException;
    public User FindUser(Long id, Connection conn) throws SQLException;
    public List<User> GetUsersByRole(RoleType roleType, Connection conn) throws SQLException;
    public List<User> GetAllUsers(Connection conn) throws SQLException;
}