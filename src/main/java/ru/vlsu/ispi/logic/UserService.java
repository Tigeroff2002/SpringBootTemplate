package ru.vlsu.ispi.logic;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraUser;
import ru.vlsu.ispi.enums.Gender;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import ru.vlsu.ispi.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

    /* Code examples for lab4 with transactions

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, noRollbackFor = { RuntimeException.class })
    public void InsertingUsersWithoutException() throws RuntimeException{
        User test1User = new User();
        test1User.setEmail("1@email.com");
        test1User.setNickname("1first");
        test1User.setPassword("1111");
        userRepository.insertOneUser(test1User.getEmail(), test1User.getNickname(), test1User.getPassword());
        User test2User = new User();
        test2User.setEmail("2@email.com");
        test2User.setNickname("2second");
        test2User.setPassword("2222");
        userRepository.insertOneUser(test2User.getEmail(), test2User.getNickname(), test2User.getPassword());

        //throw new RuntimeException("Exception thrown during insert transaction");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, noRollbackFor = { RuntimeException.class })
    public void DeletingOneUserWithException() throws RuntimeException {
        Long lastId = userRepository.calculateLastCreatedUserId();

        User lastUser = userRepository.findUserById(lastId);

        if (lastUser != null) {
            userRepository.deleteOneUser(lastId);
            //throw new RuntimeException("Exception thrown during delete transaction");
        }
    }

    private void DeleteWithException() throws RuntimeException {
        Long lastId = userRepository.calculateLastCreatedUserId();

        userRepository.deleteOneUser(lastId);

        throw new RuntimeException("Exception throw during delete transaction");
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = { RuntimeException.class })
    public void DeleteNonExistingUserWithRuntimeException() throws RuntimeException {
        try {
            DeleteWithException();
        }
        catch (RuntimeException ex){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    */
}
