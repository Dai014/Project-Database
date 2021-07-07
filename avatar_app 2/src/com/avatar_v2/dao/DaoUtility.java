package com.avatar_v2.dao;

import java.sql.*;

public final class DaoUtility {

    public static PreparedStatement initPreparedStatement(
            Connection connection, String sql,
                boolean returnGeneratedKey, Object... objects) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                sql, returnGeneratedKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i+1, objects[i]);
        }

        return preparedStatement;
    }

    public static void silentClose(ResultSet resultSet)     {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("failed to close resultSet");
            }
        }
    }
    public static void silentClose(Statement statement)     {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("failed to close statement");
            }
        }
    }
    public static void silentClose(Connection connection)   {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("failed to close connection");
            }
        }
    }
    public static void silentClose(ResultSet resultSet, Statement statement)    {
        silentClose(resultSet);
        silentClose(statement);
    }
    public static void silentClose(Statement statement, Connection connection)  {
        silentClose(statement);
        silentClose(connection);
    }
    public static void silentClose(ResultSet resultSet, Statement statement, Connection connection) {
        silentClose(resultSet, statement);
        silentClose(connection);
    }
}
