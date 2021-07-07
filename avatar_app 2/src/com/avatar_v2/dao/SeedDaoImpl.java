package com.avatar_v2.dao;

import com.avatar_v2.dto.AnimalDto;
import com.avatar_v2.dto.SeedDto;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeedDaoImpl implements SeedDao {
    private DaoFactory daoFactory;

    public SeedDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public SeedDto findDtoById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SeedDto seedDto = new SeedDto();

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection, SQL_FIND_DTO_BY_ID,
                    false, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PGInterval interval;
                seedDto.setSeed_id(resultSet.getLong("seed_id"));
                seedDto.setName(resultSet.getString("name"));
                interval = ((PGInterval) resultSet.getObject("grow_time"));
                seedDto.setGrowTime(interval.getHours() + "h" +
                        interval.getMinutes() + "m" + ((int) interval.getSeconds()) + "s");
                seedDto.setUnit(resultSet.getLong("unit"));
                seedDto.setCost(resultSet.getDouble("cost"));
                seedDto.setSaleprice(resultSet.getDouble("saleprice"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoUtility.silentClose(resultSet, preparedStatement, connection);
        }

        return seedDto;
    }
    private static final String SQL_FIND_DTO_BY_ID = "select * from seed_view where seed_id = ?";


    @Override
    public void save(SeedDto seedDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.daoFactory.getConnection();
            preparedStatement = DaoUtility.initPreparedStatement(connection,
                    SQL_SAVE, false,
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
    private static final String SQL_SAVE = "insert into seed_view(name, " +
            "grow_time, unit, cost, saleprice) " +
            "values (?, ?::interval, ?, ?, ?)";


}
