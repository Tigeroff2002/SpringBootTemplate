package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.DAOConnector;
import ru.vlsu.ispi.daoimpl.IClubDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClubDAO extends DAOConnector implements IClubDAO{

    private DBImplementation impl;

    public void setImpl(DBImplementation impl){
        this.impl = impl;
    }
    public void create(Club club) throws SQLException{
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "INSERT INTO Clubs (Id, Name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, club.getId());
                statement.setString(2, club.getName());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0)
                    System.out.println("A new club was inserted successfully");
            } catch (SQLException ex){
                System.out.println("Query was not successfully executed1");
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    public void update(Club club) throws SQLException{
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "UPDATE Clubs SET Name=? WHERE Id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, club.getName());
                statement.setLong(2, club.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0)
                    System.out.println("A club was updated successfully");
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    public void delete(long id) throws SQLException{
        Connection connection = null;

        try {
            connection = getConnection();
            String query = "DELETE FROM Clubs WHERE Id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setLong(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0)
                    System.out.println("A club was successfully deleted");
            }
            catch (SQLException ex){
                System.out.println("Query was not successfully executed2");
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    public Club getById(long id) throws SQLException{
        Connection connection = null;
        Club currClub = new Club();
        try {
            connection = getConnection();
            String query = "SELECT * FROM Clubs WHERE Id=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)){
                stmt.setLong(1, id);;
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    currClub.setId(rs.getLong("Id"));
                    currClub.setName(rs.getString("Name"));
                }
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return currClub;
    }

    @Override
    public boolean ifClubExists(Club club) throws SQLException {
        Connection connection = null;
        boolean existense = false;
        try {
            connection = getConnection();
            String query = "SELECT * FROM Clubs WHERE Id=?";
            Club currClub = null;
            try (PreparedStatement stmt = connection.prepareStatement(query)){
                stmt.setLong(1, club.getId());
                ResultSet rs = stmt.executeQuery();
                existense = rs.next();
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return existense;
    }

    public List<Sportsman> getSportsmenByClubId(long id) throws SQLException{
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "SELECT * FROM Sportsmen WHERE ClubId = " + Long.toString(id);
            impl.sportsmen = new HashMap<Long, Sportsman>();
            try (Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()){
                    Sportsman currSportsman = new Sportsman();
                    currSportsman.setId(rs.getLong("Id"));
                    currSportsman.setClubId(rs.getLong("ClubId"));
                    currSportsman.setName(rs.getString("Name"));
                    currSportsman.setAge(rs.getLong("Age"));
                    impl.sportsmen.put(currSportsman.getId(), currSportsman);
                }
            } catch (SQLException ex){
                System.out.println("Query was not successfully executed3");
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return new ArrayList<>(impl.sportsmen.values());
    }

    public List<Club> getAllClubs() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "SELECT Id, Name FROM Clubs";
            impl.clubs = new HashMap<Long, Club>();
            try (Statement stmt = connection.createStatement()){
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Club currClub = new Club();
                    currClub.setId(rs.getLong("Id"));
                    currClub.setName(rs.getString("Name"));
                    impl.clubs.put(currClub.getId(), currClub);
                }
            } catch (SQLException ex){
                System.out.println("Query was not successfully executed4");
            }
        }
        catch (SQLException ex) {}
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return new ArrayList<>(impl.clubs.values());
    }
}