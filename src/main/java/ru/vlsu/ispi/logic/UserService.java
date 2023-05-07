package ru.vlsu.ispi.logic;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import ru.vlsu.ispi.beans.extrabeans.WholeFilterSet;
import ru.vlsu.ispi.enums.*;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import ru.vlsu.ispi.repositories.UserRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<ExtraTask> filterByRowParameters(List<ExtraTask> currentTaskList, String rowToFind, WholeFilterSet filterSet, String sorter){

        if (stringIsNullOrEmptyOrBlank(rowToFind) || filterSet == null || stringIsNullOrEmptyOrBlank(sorter)){
            throw new IllegalArgumentException("String headers should not be null, empty or blank!");
        }

        rowToFind = rowToFind.toLowerCase();

        sorter = sorter.toLowerCase();

        var sortBy = SortBy.valueOf(sorter);

        var parameters = new ArrayList<FilterParameter>();

        if (filterSet.isAuthUser()){

            var filterAuthCase1 = filterSet.getLiked();

            if (!Objects.equals(filterAuthCase1, "default")){
                var filterAuthParameter1 = new FilterParameter();
                filterAuthParameter1.Filter = FilterBy.liked;
                filterAuthParameter1.parameter = filterAuthCase1;
                parameters.add(filterAuthParameter1);
            }

            var filterAuthCase2 = filterSet.getViewed();

            if (!Objects.equals(filterAuthCase2, "default")){
                var filterAuthParameter2 = new FilterParameter();
                filterAuthParameter2.Filter = FilterBy.viewed;
                filterAuthParameter2.parameter = filterAuthCase2;
                parameters.add(filterAuthParameter2);
            }
        }

        var filterCase1 = filterSet.getType();

        if (!Objects.equals(filterCase1, "default")){
            var filterParameter1 = new FilterParameter();
            filterParameter1.Filter = FilterBy.type;
            filterParameter1.parameter = filterSet.getType();
            parameters.add(filterParameter1);
        }

        var filterCase2 = filterSet.getStatus();

        if (!Objects.equals(filterCase2, "default")){
            var filterParameter2 = new FilterParameter();
            filterParameter2.Filter = FilterBy.status;
            filterParameter2.parameter = filterSet.getStatus();
            parameters.add(filterParameter2);
        }

        return filterByAllParameters(currentTaskList, rowToFind, parameters, sortBy);
    }

    private boolean stringIsNullOrEmptyOrBlank(String row){
        return row == null || row.isEmpty() || row.trim().isEmpty();
    }

    private List<ExtraTask> filterByAllParameters(List<ExtraTask> currentTaskList, String rowToFind, List<FilterParameter> parameters, SortBy sorter){

        var taskListAfterSearch = findByString(currentTaskList, rowToFind);

        if (taskListAfterSearch.size() == 0){
            return taskListAfterSearch;
        }

        var taskListAfterFiltering = filterBySetParameters(taskListAfterSearch, parameters);

        if (taskListAfterFiltering.size() == 0){
            return taskListAfterFiltering;
        }

        return sortByParameter(taskListAfterFiltering, sorter);
    }

    private List<ExtraTask> findByString(List<ExtraTask> currentTaskList, String row){
        if (!row.equals("empty")){

            var rowToFind = row.toLowerCase();

            if (rowToFind.contains("+")){

                List<String> subWords = new ArrayList<String>(Arrays.asList(row.split("\\+")));

                var obtainedTaskList = new ArrayList<ExtraTask>();

                for(var task:currentTaskList){
                    var caption = task.getCaption().toLowerCase();
                    var description = task.getDescription().toLowerCase();

                    if (containsAtLeastElementOfArray(caption, subWords)
                            || containsAtLeastElementOfArray(description, subWords)){
                        obtainedTaskList.add(task);
                    }
                }

                return obtainedTaskList;
            }

            else {
                var newTaskList = new ArrayList<ExtraTask>();

                for(var task:currentTaskList){
                    var caption = task.getCaption().toLowerCase();
                    var description = task.getDescription().toLowerCase();

                    if (caption.contains(rowToFind) || description.contains(rowToFind)){
                        newTaskList.add(task);
                    }
                }

                return newTaskList;
            }
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

    private List<ExtraTask> sortByParameter(List<ExtraTask> currentTaskList, SortBy direction){

        if (direction == SortBy.cheap_first){
            currentTaskList.sort((o1, o2) -> Math.round(o1.getPrice() - o2.getPrice()));
        }
        else if (direction == SortBy.dear_first){
            currentTaskList.sort((o1, o2) -> Math.round(o2.getPrice() - o1.getPrice()));
        }

        return currentTaskList;
    }

    private List<ExtraTask> filterBySetParameters(List<ExtraTask> currentTaskList, List<FilterParameter> parameters){
        for (var parameter:parameters) {
            var filter = parameter.Filter;
            var param = parameter.parameter;
            currentTaskList = filterByParameter(currentTaskList, filter, param);
        }
        return currentTaskList;
    }

    private List<ExtraTask> filterByParameter(List<ExtraTask> currentTaskList, FilterBy filter, String parameter){
        if (filter == FilterBy.type){
            var param = TaskType.valueOf(parameter);
            currentTaskList = currentTaskList.stream().filter(x -> x.getType() == param).collect(Collectors.toList());
        }
        else if (filter == FilterBy.status){
            var param = TaskStatus.valueOf(parameter);
            currentTaskList = currentTaskList.stream().filter(x -> x.getStatus() == param).collect(Collectors.toList());
        }
        else if (filter == FilterBy.mine){
            currentTaskList = currentTaskList.stream().filter(x -> x.IsMine).collect(Collectors.toList());
        }
        else if (filter == FilterBy.liked){
            currentTaskList = currentTaskList.stream().filter(x -> Objects.equals(x.Liked, parameter)).collect(Collectors.toList());
        }
        else if (filter == FilterBy.viewed){
            if (Objects.equals(parameter, "viewed")){
                currentTaskList = currentTaskList.stream().filter(x -> x.IsViewed).collect(Collectors.toList());
            }
            else {
                currentTaskList = currentTaskList.stream().filter(x -> !x.IsViewed).collect(Collectors.toList());
            }
        }
        return currentTaskList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByName(username);
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
