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
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.beans.extrabeans.ExtraUser;
import ru.vlsu.ispi.beans.extrabeans.FilterParameter;
import ru.vlsu.ispi.enums.*;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import ru.vlsu.ispi.repositories.UserRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<ExtraTask> filterByAllParameters(List<ExtraTask> currentTaskList, String rowToFind, List<FilterParameter> parameters, SortBy sorter){

        var taskListAfterSearch = findByString(currentTaskList, rowToFind);

        var taskListAfterFiltering = filterBySetParameters(taskListAfterSearch, parameters);

        return sortByParameter(taskListAfterFiltering, sorter);
    }

    public List<ExtraTask> findByString(List<ExtraTask> currentTaskList, String row){
        if (!row.equals("")){
            var rowToFind = row.toLowerCase();
            List<String> subWords = Arrays.asList(rowToFind.split("\\s*,\\s*"));

            var obtainedTaskList = new ArrayList<ExtraTask>();

            for(var task:currentTaskList){
                var caption = task.getCaption();
                var description = task.getDescription();

                if (containsAtLeastElementOfArray(caption, subWords)
                        || containsAtLeastElementOfArray(description, subWords)){
                    obtainedTaskList.add(task);
                }
            }

            return obtainedTaskList;
        }
        return currentTaskList;
    }

    private boolean containsAtLeastElementOfArray(String string, List<String> subWords){
        for(var item: subWords){
            if (string.contains(item)){
                return true;
            }
        }
        return false;
    }

    public List<ExtraTask> sortByParameter(List<ExtraTask> currentTaskList, SortBy direction){

        if (direction == SortBy.CheapFirst){
            currentTaskList.sort((o1, o2) -> Math.round(o1.getPrice() - o2.getPrice()));
        }
        else if (direction == SortBy.DearFirst){
            currentTaskList.sort((o1, o2) -> Math.round(o2.getPrice() - o1.getPrice()));
        }

        return currentTaskList;
    }

    public List<ExtraTask> filterBySetParameters(List<ExtraTask> currentTaskList, List<FilterParameter> parameters){
        for (var parameter:parameters) {
            var filter = parameter.Filter;
            var param = parameter.parameter;
            currentTaskList = filterByParameter(currentTaskList, filter, param);
        }
        return currentTaskList;
    }

    private List<ExtraTask> filterByParameter(List<ExtraTask> currentTaskList, FilterBy filter, String parameter){
        if (filter == FilterBy.Type){
            var param = TaskType.valueOf(parameter);
            currentTaskList = currentTaskList.stream().filter(x -> x.getType() == param).collect(Collectors.toList());
        }
        else if (filter == FilterBy.Status){
            var param = TaskStatus.valueOf(parameter);
            currentTaskList = currentTaskList.stream().filter(x -> x.getStatus() == param).collect(Collectors.toList());
        }
        else if (filter == FilterBy.Mine){
            currentTaskList = currentTaskList.stream().filter(x -> x.IsMine).collect(Collectors.toList());
        }
        else if (filter == FilterBy.NotMine){
            currentTaskList = currentTaskList.stream().filter(x -> !x.IsMine).collect(Collectors.toList());
        }
        else if (filter == FilterBy.Liked){
            currentTaskList = currentTaskList.stream().filter(x -> Objects.equals(x.Liked, "Liked")).collect(Collectors.toList());
        }
        else if (filter == FilterBy.Unliked){
            currentTaskList = currentTaskList.stream().filter(x -> Objects.equals(x.Liked, "Unliked")).collect(Collectors.toList());
        }
        else if (filter == FilterBy.Unviewed){
            currentTaskList = currentTaskList.stream().filter(x -> !x.IsViewed).collect(Collectors.toList());
        }
        return currentTaskList;
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
