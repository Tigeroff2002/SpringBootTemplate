package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.beans.Club;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ISportsmanDAO {
    public void create(Sportsman sportsman) throws SQLException;
    public void update(Sportsman sportsman) throws SQLException;
    public void delete(long id) throws SQLException;
    public Sportsman getById(long Id) throws SQLException;
    public boolean ifSportsmanExists(Sportsman sportsman) throws SQLException;
    public Club getClubBySportsmanId(long Id) throws SQLException;
    public List<Sportsman> getAllSportsmen() throws SQLException;
}