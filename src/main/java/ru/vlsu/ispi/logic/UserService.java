package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import ru.vlsu.ispi.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User RegisterUser(RegisterModel model) throws SQLException {
        if (model == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userRepository.findUserByEmail(model.getEmail());

        if (user == null){
            User newUser = new User();
            newUser.setId(1L);
            newUser.setNickname(model.getNickname());
            newUser.setPassword(model.getPassword());
            newUser.setContactnumber(model.getContactnumber());
            newUser.setEmail(model.getEmail());
            newUser.setRole(model.getRole());

            userRepository.save(newUser);

            int id = userRepository.calculateCountUsers();
            newUser.setId(Integer.toUnsignedLong(id));

            return newUser;
        }
        else {
            return null;
        }
    }

    public User LoginUser(LoginModel model) throws SQLException{
        if (model == null){
            throw new IllegalArgumentException("Null login model was provided");
        }

        return userRepository.findUserByEmail(model.getEmail());
    }

    public User FindUserById(Long id) throws SQLException{
        if (id == null){
            throw new IllegalArgumentException("Null id was provided");
        }
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
