package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vlsu.ispi.DAO.UserDAO;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.IUserDAO;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.logic.abstractions.IUserHandler;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;

public class UserHandler implements IUserHandler {
    private final UserDAO userDAO;

    public UserHandler(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User RegisterUser(RegisterModel model) throws SQLException {
        if (model == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userDAO.FindUserByEmail(model.getEmail());

        if (user == null){
            User newUser = new User();
            newUser.setId(1L);
            newUser.setNickName(model.getNickName());
            newUser.setPassword(model.getPassword());
            newUser.setContactNumber(model.getContactNumber());
            newUser.setEmail(model.getEmail());
            newUser.setRoleId(model.getRoleType());

            int id = userDAO.Create(newUser);
            newUser.setId(Integer.toUnsignedLong(id));

            return newUser;
        }
        else {
            return null;
        }
    }

    @Override
    public User LoginUser(LoginModel model) throws SQLException{
        if (model == null){
            throw new IllegalArgumentException("Null login model was provided");
        }

        return userDAO.FindUserByEmail(model.getEmail());
    }

    @Override
    public User FindUserById(Long id) throws SQLException{
        if (id == null){
            throw new IllegalArgumentException("Null id was provided");
        }
        return userDAO.FindUser(id);
    }
}
