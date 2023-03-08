package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Organization;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.daoimpl.IOrganizationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizationDAO implements IOrganizationDAO {
    private final DBContext context;

    public OrganizationDAO(DBContext context){
        if (context == null){
            throw new IllegalArgumentException("Context should not be null!");
        }
        this.context = context;
    }

    @Override
    public void Create(Organization organization, Connection conn) throws SQLException {
        String query = "INSERT INTO Organizations (Id, Name, City, OfficialSite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setLong(1, organization.getId());
            statement.setString(2, organization.getOrgName());
            statement.setString(3, organization.getCity());
            statement.setString(4, organization.getOfficialSite());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("A new organization was inserted successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed1");
        }
    }

    @Override
    public void Update(Organization organization, Connection conn) throws SQLException {
        String query = "UPDATE Organizations SET Name=?, City=?, OfficialSite=? WHERE Id=?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, organization.getOrgName());
            statement.setString(2, organization.getCity());
            statement.setString(3, organization.getOfficialSite());
            statement.setLong(4, organization.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("A organization was updated successfully");
        }
    }

    @Override
    public void Delete(Long id, Connection conn) throws SQLException {
        String query = "DELETE FROM Organizations WHERE Id=?";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("A organization was successfully deleted");
        }
        catch (SQLException ex){
            System.out.println("Query was not successfully executed2");
        }
    }

    @Override
    public Organization FindOrganization(Long id, Connection conn) throws SQLException {
        String query = "SELECT * FROM Organizations WHERE Id=?";
        Organization currOrganization = new Organization();
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, id);;
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                currOrganization.setId(rs.getLong("Id"));
                currOrganization.setOrgName(rs.getString("Name"));
                currOrganization.setCity(rs.getString("City"));
                currOrganization.setOfficialSite(rs.getString("OfficialSite"));
            }
        }
        return currOrganization;
    }

    @Override
    public List<User> GetUsersByOrganizationId(Long id, Connection conn) throws SQLException {
        String query = "SELECT * FROM Users WHERE OrganizationId = "
                + Long.toString(id);
        context.Users = new HashMap<Long, User>();
        try (Statement statement = conn.createStatement()){
            ResultSet rs = statement.executeQuery(query);
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
                context.Users.put(currUser.getId(), currUser);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed3");
        }
        return new ArrayList<>(context.Users.values());
    }

    @Override
    public List<Organization> GetAllOrganizations(Connection conn) throws SQLException {
        String query = "SELECT * FROM Clubs";
        context.Organizations = new HashMap<Long, Organization>();
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Organization currOrganization = new Organization();
                currOrganization.setId(rs.getLong("Id"));
                currOrganization.setOrgName(rs.getString("Name"));
                currOrganization.setCity(rs.getString("City"));
                currOrganization.setOfficialSite(rs.getString("OfficialSite"));
                context.Organizations.put(currOrganization.getId(), currOrganization);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed4");
        }
        return new ArrayList<>(context.Organizations.values());
    }

    private boolean ifOrganizationExists(Organization organization, Connection conn) throws SQLException {
        String query = "SELECT * FROM Organizations WHERE Id=?";
        Organization currOrganization = null;
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, organization.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }
}