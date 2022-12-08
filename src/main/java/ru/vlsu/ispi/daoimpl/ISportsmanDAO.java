package ru.vlsu.ispi.daoimpl;

import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.beans.Club;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ISportsmanDAO {
    public void create(Sportsman sportsman, Connection conn) throws SQLException;
    public void update(Sportsman sportsman, Connection conn) throws SQLException;
    public void delete(long id, Connection conn) throws SQLException;
    public Sportsman getById(long Id, Connection conn) throws SQLException;
    public Club getClubBySportsmanId(long Id, Connection conn) throws SQLException;
    public List<Sportsman> getAllSportsmen(Connection conn) throws SQLException;
}