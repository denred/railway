package com.epam.redkin.model.connectionpool;

import com.epam.redkin.model.exception.DataBaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection implements ConnectionCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConnection.class);
    private static final DataSource dataSource;
    private static final String PROPERTIES_FILE = "/testdb.properties";

    static {
        HikariConfig config = new HikariConfig(PROPERTIES_FILE);
        //config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new DataBaseException("Connection failed.");
        }
        return connection;
    }
}
