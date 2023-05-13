package ru.vlsu.ispi.logic;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraUser;
import ru.vlsu.ispi.enums.*;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import ru.vlsu.ispi.repositories.UserRepository;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(){

    }

    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public User RegisterUser(RegisterModel model) throws SQLException {
        if (model == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userRepository.findUserByEmail(model.getEmail());

        if (user == null) {

            if (!Objects.equals(model.getPassword(), model.getConfirmpassword())){
                User testUser = new User();
                testUser.setId(-1L);
                return testUser;
            }

            //model.setPassword(passwordEncoder.encode(model.getPassword()));

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

        return null;
    }

    public User LoginUser(LoginModel model) throws SQLException{
        if (model == null){
            throw new IllegalArgumentException("Null login model was provided");
        }

        User user = userRepository.findUserByEmail(model.getEmail());

        if (user != null){
            if (Objects.equals(user.getPassword(), model.getPassword())){
                return user;
            }
            else {
                return null;
            }
        }
        return user;
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

    public ExtraUser nameRoleUser(Long userId){
        User user = userRepository.findUserById(userId);

        if (user != null){
            ExtraUser extraUser = new ExtraUser();
            extraUser.setId(user.getId());
            extraUser.setRole(user.getRole());
            extraUser.setGender(user.getGender());
            extraUser.setEmail(user.getEmail());
            extraUser.setNickname(user.getNickname());
            extraUser.setPassword(user.getPassword());
            extraUser.setContactnumber(user.getContactnumber());
            extraUser.isModerator = false;
            extraUser.isAdmin = false;
            extraUser.isUser = false;
            if (extraUser.getRole() == RoleType.Admin){
                extraUser.isAdmin = true;
            }
            else if (extraUser.getRole() == RoleType.Moderator){
                extraUser.isModerator = true;
            }
            else {
                extraUser.isUser = true;
            }

            return extraUser;
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByName(username);
    }
}
