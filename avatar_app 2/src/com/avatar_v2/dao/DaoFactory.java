package com.avatar_v2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private final String url;
    private final String username;
    private final String password;

    public DaoFactory(String url, String username, String password) {
        this.url        = url;
        this.username   = username;
        this.password   = password;
    }


    public static DaoFactory getInstance() throws DaoConfigurationException {
        String url      = "jdbc:postgresql://localhost/avatar";
        String username = "postgres";
        String password = "pgadmin";
        String driver   = "org.postgresql.Driver";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DaoConfigurationException("load driver failed", e);
        }

        return new DaoFactory(url, username, password);
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

    public GameDao      getGameDao      () {
        return new GameDaoImpl(this);
    }
    public FarmerDao    getFarmerDao    () {
        return new FarmerDaoImpl(this);
    }
    public AccountDao   getAccountDao   () {
        return new AccountDaoImpl(this);
    }
    public AnimalDao    getAnimalDao    () {
        return new AnimalDaoImpl(this);
    }
    public SeedDao      getSeedDao      () {
        return new SeedDaoImpl(this);
    }
    public FarmDao      getFarmDao      () {
        return new FarmDaoImpl(this);
    }
}
