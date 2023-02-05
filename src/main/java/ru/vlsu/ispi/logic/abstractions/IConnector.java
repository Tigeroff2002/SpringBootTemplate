package ru.vlsu.ispi.logic.abstractions;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnector {
    public Connection getConnection() throws SQLException;
}
