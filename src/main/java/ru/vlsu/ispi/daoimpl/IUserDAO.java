package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.RoleType;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;


public interface IUserDAO {
    public int Create(User user) throws SQLException;
    public void Update(User user) throws SQLException;
    public void Delete(Long id) throws SQLException;
    public User FindUser(Long id) throws SQLException;
    public User FindUserByEmail(String email) throws SQLException;
    public List<User> GetUsersByRole(RoleType roleType) throws SQLException;
    public List<User> GetAllUsers() throws SQLException;
}