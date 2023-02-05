package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IClubDAO {
    public void create(Club club, Connection conn) throws SQLException;
    public void update(Club club, Connection conn) throws SQLException;
    public void delete(long id, Connection conn) throws SQLException;
    public Club getById(long Id, Connection conn) throws SQLException;
    public boolean ifClubExists(Club club, Connection connection) throws SQLException;
    public List<Sportsman> getSportsmenByClubId(long id, Connection conn) throws SQLException;
    public List<Club> getAllClubs(Connection conn) throws SQLException;
}
