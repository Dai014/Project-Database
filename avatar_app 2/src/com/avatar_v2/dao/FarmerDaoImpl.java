package com.avatar_v2.dao;

import com.avatar_v2.entity.Farmer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmerDaoImpl implements FarmerDao {
    private final DaoFactory daoFactory;

    public FarmerDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public boolean save(Farmer farmer) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_SAVE, true,
                    farmer.getName(), farmer.getGender(),
                    farmer.getAddress(), farmer.getDateOfBirth());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                return false;
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                farmer.setFarmerId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return true;
    }
    private static final String SQL_SAVE = "insert into farmer(name, gender, address, dob)" +
                                            "values(?, ?, ?, ?::date)";


    @Override
    public Farmer findById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Farmer farmer = new Farmer();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_BY_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                farmer.setFarmerId(resultSet.getLong("farmer_id"));
                farmer.setName(resultSet.getString("name"));
                farmer.setGender(resultSet.getString("gender"));
                farmer.setAddress(resultSet.getString("address"));
                if (resultSet.getDate("dob") != null) {
                    farmer.setDateOfBirth(resultSet.getDate("dob").toString());
                }
                farmer.setFarmId(resultSet.getLong("farm_id"));
                farmer.setWallet(resultSet.getDouble("wallet"));
                farmer.setLevelId(resultSet.getLong("level_id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return farmer;
    }
    private static final String SQL_FIND_BY_ID = "select * from farmer where farmer_id = ?";


    @Override
    public void update(Long id, Farmer farmer) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_UPDATE,
                    false, farmer.getName(),
                    farmer.getGender(), farmer.getAddress(), farmer.getDateOfBirth(),
                    farmer.getWallet(), farmer.getLevelId(), farmer.getFarmId(),
                    id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_UPDATE = "update farmer " +
            "set name = ?, gender = ?, address = ?, dob = ?::date, " +
                "wallet = ?, level_id = ?, farm_id = ? " +
            "where farmer_id = ?";


    @Override
    public void updateLevel(Long id, Long level) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_UPDATE_LEVEL,
                    false, level, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_UPDATE_LEVEL = "update farmer set level_id = ? where farmer_id = ?";
}
