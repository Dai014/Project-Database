package com.avatar_v2.dao;

import com.avatar_v2.dto.*;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDaoImpl implements GameDao {
    private DaoFactory daoFactory;

    public GameDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addAnimal(AnimalDto animalDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_ADD_ANIMAL, true,
                    animalDto.getName(), animalDto.getHabitat(),
                    animalDto.getGrowTime(), animalDto.getUnit(),
                    animalDto.getCost(), animalDto.getSaleprice());
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DaoException("failed to insert new animal");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }
    }
    private static final String SQL_ADD_ANIMAL = "insert into animal_view(name, habitat, " +
            "grow_time, unit, cost, saleprice)" +
            "values (?, ?, ?::interval, ?, ?, ?)";


    @Override
    public void addSeed(SeedDto seedDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_ADD_SEED, false,
                    seedDto.getName(),
                    seedDto.getGrowTime(), seedDto.getUnit(),
                    seedDto.getCost(), seedDto.getSaleprice());
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DaoException("failed to insert new seed");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_ADD_SEED = "insert into seed_view(name, grow_time," +
                                                                        "unit, cost, saleprice)" +
                                                "values(?, ?::interval, ?, ?, ?)";


    @Override
    public AnimalDto getAnimalById(Long animalId) throws DaoException {
        return null;
    }

    @Override
    public SeedDto getSeedById(Long seedId) throws DaoException {
        return null;
    }

    @Override
    public List<AnimalDto> getAllAnimal() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<AnimalDto> animalDtoList = new ArrayList<AnimalDto>();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    this.SQL_GET_ALL_ANIMAL, false);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                org.postgresql.util.PGInterval interval;
                AnimalDto animalDto = new AnimalDto();

                animalDto.setAnimal_id(resultSet.getLong("animal_id"));
                animalDto.setName(resultSet.getString("name"));
                animalDto.setHabitat(resultSet.getString("habitat"));
                interval = ((PGInterval) resultSet.getObject("grow_time"));
                animalDto.setGrowTime(interval.getHours() + ":" +
                        interval.getMinutes() + ":" + ((int) interval.getSeconds()));
                animalDto.setUnit(resultSet.getLong("unit"));
                animalDto.setCost(resultSet.getDouble("cost"));
                animalDto.setSaleprice(resultSet.getDouble("saleprice"));

                animalDtoList.add(animalDto);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return animalDtoList;
    }
    private static final String SQL_GET_ALL_ANIMAL = "select * from animal_view";


    @Override
    public List<SeedDto> getAllSeed() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SeedDto> seedDtoList = new ArrayList<SeedDto>();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_GET_ALL_SEED, false);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                org.postgresql.util.PGInterval interval;
                SeedDto seedDto = new SeedDto();

                seedDto.setSeed_id(resultSet.getLong("seed_id"));
                seedDto.setName(resultSet.getString("name"));
                interval = ((PGInterval) resultSet.getObject("grow_time"));
                seedDto.setGrowTime(interval.getHours() + ":" +
                        interval.getMinutes() + ":" + ((int) interval.getSeconds()));
                seedDto.setUnit(resultSet.getLong("unit"));
                seedDto.setCost(resultSet.getDouble("cost"));
                seedDto.setSaleprice(resultSet.getDouble("saleprice"));

                seedDtoList.add(seedDto);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return seedDtoList;
    }
    private static final String SQL_GET_ALL_SEED = "select * from seed_view";


    @Override
    public void deleteAnimal(Long animalId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_DELETE_ANIMAL, false, animalId);
            int status = preparedStatement.executeUpdate();

            if (status == 0) {
                throw new DaoException("failed to delete animal");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_DELETE_ANIMAL = "delete from animal_view where animal_id = ?";


    @Override
    public Long addNewLevel(LevelDto levelDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long levelId = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_ADD_NEW_LEVEL, true,
                    levelDto.getLevelId(), levelDto.getFarmingLand(),
                    levelDto.getPlantingLand(), levelDto.getFarmingPond(),
                    levelDto.getCostToUp());
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DaoException("failed to add new level");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                levelId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }

        return levelId;
    }
    private static final String SQL_ADD_NEW_LEVEL = "insert into level values(?, ?, ?, ?, ?)";


    @Override
    public void updateDateTransactional(Long id, String date) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_UPDATE_DATE_TRANSACTIONAL, false,
                    date, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(preparedStatement, connection);
        }
    }
    private static final String SQL_UPDATE_DATE_TRANSACTIONAL = "update transactional " +
            "set date = ?::date where id = ?";


    @Override
    public LevelDto getLevelDto(Long levelId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LevelDto levelDto = new LevelDto();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_GET_LEVEL_DTO,
                    false, levelId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                levelDto.setLevelId(resultSet.getLong("level_id"));
                levelDto.setFarmingPond(resultSet.getLong("farming_pond"));
                levelDto.setFarmingLand(resultSet.getLong("farming_land"));
                levelDto.setPlantingLand(resultSet.getLong("planting_land"));
                levelDto.setCostToUp(resultSet.getDouble("cost_to_up"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return levelDto;
    }
    private static final String SQL_GET_LEVEL_DTO = "select * from level where level_id = ?";
}
