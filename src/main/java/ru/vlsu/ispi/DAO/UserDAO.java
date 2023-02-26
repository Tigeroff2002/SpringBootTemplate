package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.DAOConnector;
import ru.vlsu.ispi.daoimpl.IUserDAO;
import ru.vlsu.ispi.enums.RoleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAO extends DAOConnector implements IUserDAO {
    private final DBContext _context;

    public UserDAO(DBContext context){
        if (context == null){
            throw new IllegalArgumentException("Context should not be null!");
        }
        _context = context;
    }
    @Override
    public void Create(User user) throws SQLException {
        Connection connection = getConnection();
        String query = "INSERT INTO Users " +
                "(Id, RoleId, Email, NickName, Password, Gender, ContactNumber, RegisterDate, BirthdayDate, Rating, Resume, Balance, Bonus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.setInt(2, user.getRoleId());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getNickName());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getContactNumber());
            statement.setDate(8, (java.sql.Date) user.getRegisterDate());
            statement.setDate(9, (java.sql.Date) user.getBirthdayDate());
            statement.setFloat(10, user.getRating());
            statement.setString(11, user.getResume());
            statement.setFloat(12, user.getBalance());
            statement.setFloat(13, user.getBonus());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("A new user was inserted successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }


    @Override
    public void Update(User user) throws SQLException {
        Connection connection = getConnection();
        String query = "UPDATE Users " +
                "SET RoleId=?, Email=?, NickName=?, Password=?, Gender=?, ContactNumber=?, RegisterDate=?, BirthdayDate=?, Rating=?, Resume=?, Balance=?, Bonus=?" +
                " WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getRoleId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getNickName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setString(6, user.getContactNumber());
            statement.setDate(7, (java.sql.Date) user.getRegisterDate());
            statement.setDate(8, (java.sql.Date) user.getBirthdayDate());
            statement.setFloat(9, user.getRating());
            statement.setString(10, user.getResume());
            statement.setFloat(11, user.getBalance());
            statement.setFloat(12, user.getBonus());
            statement.setLong(13, user.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("A sportsman was updated successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }

    @Override
    public void Delete(Long id) throws SQLException {
        Connection connection = getConnection();
        String query = "DELETE FROM Users WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("A user was successfully deleted");
        } catch(SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }

    @Override
    public User FindUser(Long id) throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM Users WHERE Id = " + Long.toString(id);
        User currUser = new User();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                currUser.setId(rs.getLong("Id"));
                currUser.setRoleId(rs.getInt("RoleId"));
                currUser.setEmail(rs.getString("Email"));
                currUser.setNickName(rs.getString("NickName"));
                currUser.setPassword(rs.getString("Password"));
                currUser.setContactNumber(rs.getString("ContactNumber"));
                currUser.setRegisterDate(rs.getDate("RegisterDate"));
                currUser.setBirthdayDate(rs.getDate("BirthdayDate"));
                currUser.setRating(rs.getFloat("Rating"));
                currUser.setResume(rs.getString("Resume"));
                currUser.setBalance(rs.getFloat("Balance"));
                currUser.setBonus(rs.getFloat("Bonus"));
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return currUser;
    }

    @Override
    public User FindUserByName(String nickName) throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM Users WHERE NickName = " + nickName;
        User currUser = new User();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                currUser.setId(rs.getLong("Id"));
                currUser.setRoleId(rs.getInt("RoleId"));
                currUser.setEmail(rs.getString("Email"));
                currUser.setNickName(rs.getString("NickName"));
                currUser.setPassword(rs.getString("Password"));
                currUser.setContactNumber(rs.getString("ContactNumber"));
                currUser.setRegisterDate(rs.getDate("RegisterDate"));
                currUser.setBirthdayDate(rs.getDate("BirthdayDate"));
                currUser.setRating(rs.getFloat("Rating"));
                currUser.setResume(rs.getString("Resume"));
                currUser.setBalance(rs.getFloat("Balance"));
                currUser.setBonus(rs.getFloat("Bonus"));
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return currUser;
    }

    @Override
    public List<User> GetUsersByRole(RoleType roleType) throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM Users WHERE RoleId = " + Integer.toString(roleType.getValue());
        _context.Users = new HashMap<Long, User>();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                User currUser = new User();
                currUser.setId(rs.getLong("Id"));
                currUser.setRoleId(rs.getInt("RoleId"));
                currUser.setEmail(rs.getString("Email"));
                currUser.setNickName(rs.getString("NickName"));
                currUser.setPassword(rs.getString("Password"));
                currUser.setContactNumber(rs.getString("ContactNumber"));
                currUser.setRegisterDate(rs.getDate("RegisterDate"));
                currUser.setBirthdayDate(rs.getDate("BirthdayDate"));
                currUser.setRating(rs.getFloat("Rating"));
                currUser.setResume(rs.getString("Resume"));
                currUser.setBalance(rs.getFloat("Balance"));
                currUser.setBonus(rs.getFloat("Bonus"));
                _context.Users.put(currUser.getId(), currUser);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return new ArrayList<>(_context.Users.values());
    }

    @Override
    public List<User> GetAllUsers() throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM Users";
        _context.Users = new HashMap<Long, User>();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                User currUser = new User();
                currUser.setId(rs.getLong("Id"));
                currUser.setRoleId(rs.getInt("RoleId"));
                currUser.setEmail(rs.getString("Email"));
                currUser.setNickName(rs.getString("NickName"));
                currUser.setPassword(rs.getString("Password"));
                currUser.setContactNumber(rs.getString("ContactNumber"));
                currUser.setRegisterDate(rs.getDate("RegisterDate"));
                currUser.setBirthdayDate(rs.getDate("BirthdayDate"));
                currUser.setRating(rs.getFloat("Rating"));
                currUser.setResume(rs.getString("Resume"));
                currUser.setBalance(rs.getFloat("Balance"));
                currUser.setBonus(rs.getFloat("Bonus"));
                _context.Users.put(currUser.getId(), currUser);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return new ArrayList<>(_context.Users.values());
    }
}
