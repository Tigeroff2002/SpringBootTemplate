package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.IUserDAO;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.logic.abstractions.IUserHandler;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;

public class UserHandler implements IUserHandler {

    @Autowired
    private IUserDAO _userDAO;

    @Override
    public User RegisterUser(RegisterModel model) throws SQLException {
        if (model == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = new User();
        user.NickName = model.NickName;
        user.Password = model.Password;
        user.ContactNumber = model.ContactNumber;
        user.Email = model.Email;
        user.RoleId = model.RoleType == 1
                ? RoleType.User
                : model.RoleType == 2
                    ? RoleType.Manager
                    : RoleType.Admin;

        _userDAO.Create(user);

        return user;
    }

    @Override
    public User LoginUser(LoginModel model) throws SQLException{
        if (model == null){
            throw new IllegalArgumentException("Null login model was provided");
        }

        return _userDAO.FindUserByName(model.NickName);
    }

    @Override
    public User FindUserById(Long id) throws SQLException{
        return _userDAO.FindUser(id);
    }
}
