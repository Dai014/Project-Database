package com.avatar_v2.dao;

import com.avatar_v2.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private final DaoFactory daoFactory;

    public AccountDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public boolean save(Account account) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_SAVE,
                    false, account.getUsername(),
                    account.getPassword(), account.getFarmerId());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return true;
    }
    private static final String SQL_SAVE = "insert into account values(?, ?, ?, NOW())";


    @Override
    public Account findByUsername(String username) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_BY_USERNAME,
                    false, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setFarmerId(resultSet.getLong("farmer_id"));
                if (resultSet.getDate("date_create") != null) {
                    account.setDateCreate(resultSet.getDate("date_create").toString());
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return account;
    }
    private static final String SQL_FIND_BY_USERNAME = "select * from account where username = ?";


    @Override
    public Account findByFarmerId(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = new Account();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_BY_FARMER_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setFarmerId(resultSet.getLong("farmer_id"));
                if (resultSet.getDate("date_create") != null) {
                    account.setDateCreate(resultSet.getDate("date_create").toString());
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return account;
    }
    private static final String SQL_FIND_BY_FARMER_ID = "select * from account where farmer_id = ?";


    @Override
    public List<Account> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accountList = new ArrayList<Account>();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_ALL,
                    false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setFarmerId(resultSet.getLong("farmer_id"));
                if (resultSet.getDate("date_create") != null) {
                    account.setDateCreate(resultSet.getDate("date_create").toString());
                }

                accountList.add(account);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return accountList;
    }
    private static final String SQL_FIND_ALL = "select * from account";


    @Override
    public void updateDateCreate(Long farmerId, String dateCreate) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_UPDATE_DATE_CREATE, false,
                    dateCreate, farmerId);
            int status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_UPDATE_DATE_CREATE = "update account " +
            "set date_create = ?::date where farmer_id = ?";
}
