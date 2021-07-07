package com.avatar_v2.dao;

import com.avatar_v2.dto.FarmDto;
import com.avatar_v2.dto.InFarm;
import com.avatar_v2.entity.Farm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmDaoImpl implements FarmDao {
    private final DaoFactory daoFactory;

    public FarmDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Farm findById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Farm farm = new Farm();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_BY_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                farm.setFarmId(resultSet.getLong("farm_id"));
                farm.setFreeFarmingLand(resultSet.getLong("free_farming_land"));
                farm.setFreeFarmingPond(resultSet.getLong("free_farming_pond"));
                farm.setFreePlantingLand(resultSet.getLong("free_planting_land"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return farm;
    }
    private static final String SQL_FIND_BY_ID = "select * from farm where farm_id = ?";


    @Override
    public void addAnimal(Long farmId, Long animalId, Long quantity) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_ADD_ANIMAL,
                    false, farmId, animalId);
            for (int i = 0; i < quantity; i++) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_ADD_ANIMAL = "insert into in_farm(farm_id, avatar_obj_id)" +
                                                    "values(?, ?)";


    @Override
    public void addSeed(Long farmId, Long seedId, Long quantity) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_ADD_SEED,
                    false, farmId, seedId);
            for (int i = 0; i < quantity; i++) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_ADD_SEED = "insert into in_farm(farm_id, avatar_obj_id)" +
                                                    "values(?, ?)";


    @Override
    public void update(Long id, Farm farm) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_UPDATE,
                    false, farm.getFreeFarmingLand(),
                    farm.getFreeFarmingPond(), farm.getFreePlantingLand(), id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_UPDATE = "update farm " +
            "set free_farming_land = ?, free_farming_pond = ?, free_planting_land = ? " +
            "where farm_id = ?";


    @Override
    public List<InFarm> farmView(Long farmID) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<InFarm> inFarmList = new ArrayList<InFarm>();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FARM_VIEW,
                    false, farmID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InFarm inFarm = new InFarm();
                inFarm.setAvatarObjName(resultSet.getString("name"));
                if (resultSet.getDouble("status") < 100) {
                    inFarm.setStatus(resultSet.getDouble("status"));
                }
                inFarmList.add(inFarm);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return inFarmList.isEmpty() ? null : inFarmList;
    }
    private static final String SQL_FARM_VIEW = "select * from farm_view(?)"; // farm_view is a function


    @Override
    public FarmDto findDtoById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        FarmDto farmDto = new FarmDto();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_DTO_BY_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return farmDto;
    }
    private static final String SQL_FIND_DTO_BY_ID = "select * from in_farm_view where farm_id = ?";


    @Override
    public List<Long> harvest(int farmId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Long> avatarObjIdList = new ArrayList<Long>();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_HARVEST,
                    false, farmId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                avatarObjIdList.add(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return avatarObjIdList.isEmpty() ? null : avatarObjIdList;
    }
    private static final String SQL_HARVEST = "select harvest(?)";
}
