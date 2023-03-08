package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Organization;
import ru.vlsu.ispi.beans.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IOrganizationDAO {
    public void Create(Organization organization, Connection conn) throws SQLException;
    public void Update(Organization organization, Connection conn) throws SQLException;
    public void Delete(Long id, Connection conn) throws SQLException;
    public Organization FindOrganization(Long id, Connection conn) throws SQLException;
    public List<User> GetUsersByOrganizationId(Long id, Connection conn) throws SQLException;
    public List<Organization> GetAllOrganizations(Connection conn) throws SQLException;
}
