package com.epam.redkin.model.connectionpool;

import com.epam.redkin.model.exception.DataBaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBManager.class);
    private static final DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig("/db.properties");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }


    public static Connection getConnection() {
        Connection conn;
     try{
         conn = ConnectionPools.getProcessing().getConnection();
     } catch (SQLException e) {
         throw new RuntimeException(e);
     }
        return conn;
    }

}
