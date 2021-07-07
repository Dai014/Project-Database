package com.avatar_v2.dao;

import com.avatar_v2.dto.AnimalDto;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalDaoImpl implements AnimalDao {
    private final DaoFactory daoFactory;

    public AnimalDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public AnimalDto findDtoById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        AnimalDto animalDto = new AnimalDto();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_DTO_BY_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PGInterval interval;

                animalDto.setAnimal_id(resultSet.getLong("animal_id"));
                animalDto.setName(resultSet.getString("name"));
                animalDto.setHabitat(resultSet.getString("habitat"));
                interval = ((PGInterval) resultSet.getObject("grow_time"));
                animalDto.setGrowTime(interval.getHours() + "h" +
                        interval.getMinutes() + "m" + ((int) interval.getSeconds()) + "s");
                animalDto.setUnit(resultSet.getLong("unit"));
                animalDto.setCost(resultSet.getDouble("cost"));
                animalDto.setSaleprice(resultSet.getDouble("saleprice"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return animalDto;
    }
    private static final String SQL_FIND_DTO_BY_ID = "select * from animal_view where animal_id = ?";


    @Override
    public void save(AnimalDto animalDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_SAVE, false,
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
    private static final String SQL_SAVE = "insert into animal_view(name, habitat, " +
            "grow_time, unit, cost, saleprice)" +
            "values (?, ?, ?::interval, ?, ?, ?)";
}
