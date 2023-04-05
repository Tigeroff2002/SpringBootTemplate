package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.Gender;
import ru.vlsu.ispi.enums.RoleType;
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
            newUser.setNickname(model.getNickname());
            newUser.setPassword(model.getPassword());
            newUser.setContactnumber(model.getContactnumber());
            newUser.setEmail(model.getEmail());
            newUser.setRole(model.getRole());
            newUser.setGender(model.getGender());

            userRepository.save(newUser);

            int newId = userRepository.calculateMaxUserId(newUser.getEmail());

            newUser.setId(Integer.toUnsignedLong(newId));

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

    @Transactional
    public void TestFirstTransactionalCase() throws RuntimeException{
        // Insert and Delete Methods in one transaction
        User testUser = new User();
        testUser.setEmail("1@email.com");
        testUser.setNickname("1first");
        testUser.setPassword("1111");
        userRepository.insertOneUser(testUser.getEmail(), testUser.getNickname(), testUser.getPassword());

        //throw new RuntimeException("Runtime exception was thrown after inserting");

        userRepository.deleteOneUser(testUser.getId());
    }

    public void TestSecondTransactionalCase() throws RuntimeException{
        // Insert and Delete Methods in 2 different transactions
        User testUser = new User();
        testUser.setEmail("1@email.com");
        testUser.setNickname("1first");
        testUser.setPassword("1111");
        userRepository.insertOneUser(testUser.getEmail() , testUser.getNickname(), testUser.getPassword());

        userRepository.deleteOneUser(testUser.getId());
    }
}
