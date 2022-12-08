package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.IClubDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClubDAO implements IClubDAO{

    private DBImplementation impl;

    public void setImpl(DBImplementation impl){
        this.impl = impl;
    }
    public void create(Club club, Connection conn) throws SQLException{
        String query = "INSERT INTO Clubs (Id, Name) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setLong(1, club.getId());
            statement.setString(2, club.getName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("A new club was inserted successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed1");
        }
    }
    public void update(Club club, Connection conn) throws SQLException{
        String query = "UPDATE Clubs SET Name=? WHERE Id=?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, club.getName());
            statement.setLong(2, club.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("A club was updated successfully");
        }
    }
    public void delete(long id, Connection conn) throws SQLException{
        String query = "DELETE FROM Clubs WHERE Id=?";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("A club was successfully deleted");
        }
        catch (SQLException ex){
            System.out.println("Query was not successfully executed2");
        }
    }
    public Club getById(long id, Connection conn) throws SQLException{
        String query = "SELECT * FROM Clubs WHERE Id=?";
        Club currClub = new Club();
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, id);;
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                currClub.setId(rs.getLong("Id"));
                currClub.setName(rs.getString("Name"));
            }
        }
        return currClub;
    }
    public List<Sportsman> getSportsmenByClubId(long id, Connection conn) throws SQLException{
        String query = "SELECT * FROM Sportsmen WHERE ClubId = " + Long.toString(id);
        impl.sportsmen = new HashMap<Long, Sportsman>();
        try (Statement statement = conn.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Sportsman currSportsman = new Sportsman();
                currSportsman.setId(rs.getLong("Id"));
                currSportsman.setClubId(rs.getLong("ClubId"));
                currSportsman.setName(rs.getString("Name"));
                currSportsman.setAge(rs.getInt("Age"));
                impl.sportsmen.put(currSportsman.getId(), currSportsman);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed3");
        }
        return new ArrayList<>(impl.sportsmen.values());
    }
    public List<Club> getAllClubs(Connection conn) throws SQLException {
        String query = "SELECT Id, Name FROM Clubs";
        impl.clubs = new HashMap<Long, Club>();
        try (Statement stmt = conn.createStatement()){
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
        return new ArrayList<>(impl.clubs.values());
    }
}