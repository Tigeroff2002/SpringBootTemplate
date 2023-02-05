package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.IUserDAO;
import ru.vlsu.ispi.enums.RoleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final DBContext _context;

    public UserDAO(DBContext context){
        if (context == null){
            throw new IllegalArgumentException("Context should not be null!");
        }
        _context = context;
    }
    @Override
    public void Create(User user, Connection conn) throws SQLException {
        String query = "INSERT INTO Users " +
                "(Id, RoleId, Email, NickName, Password, Gender, ContactNumber, RegisterDate, BirthdayDate, Rating, Resume, Balance, Bonus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
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
    public void Update(User user, Connection conn) throws SQLException {

    }

    @Override
    public void Delete(Long id, Connection conn) throws SQLException {

    }

    @Override
    public User FindUser(Long id, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public List<User> GetUsersByRole(RoleType roleType, Connection conn) throws SQLException {
        return null;
    }

    @Override
    public List<User> GetAllUsers(Connection conn) throws SQLException {
        return null;
    }
}
