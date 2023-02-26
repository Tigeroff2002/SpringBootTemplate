package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IClubDAO {
    public void create(Club club) throws SQLException;
    public void update(Club club) throws SQLException;
    public void delete(long id) throws SQLException;
    public Club getById(long Id) throws SQLException;
    public boolean ifClubExists(Club club) throws SQLException;
    public List<Sportsman> getSportsmenByClubId(long id) throws SQLException;
    public List<Club> getAllClubs() throws SQLException;
}
