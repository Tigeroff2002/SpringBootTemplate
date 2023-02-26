package ru.vlsu.ispi.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DAOConnector {
    private final String userName = "postgres";
    private final String password = "root";
    private final String dbms = "postgresql";
    private final String serverName = "localhost";
    private final String portNumber = "5432";
    private final String DBName = "SportSpring";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException ex){
            System.err.println("Драйвер PostgreSQL не найден");
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e1);
        }
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        serverName + ":" +
                        portNumber + "/" +
                        DBName,
                connectionProps);
        if (conn != null)
            System.out.println("Connected to database " + DBName);
        else
            throw new SQLException("Database was not found and connected");
        return conn;
    }
}
