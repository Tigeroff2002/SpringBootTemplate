package ru.vlsu.ispi.logic.abstractions;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;

public interface IUserHandler {
    public User RegisterUser(RegisterModel model) throws SQLException;

    public User LoginUser(LoginModel model) throws SQLException;

    public User FindUserById(Long id) throws SQLException;
}
