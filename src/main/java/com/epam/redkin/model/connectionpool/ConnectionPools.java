package com.epam.redkin.model.connectionpool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class ConnectionPools {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPools.class);
    private static final String PROPERTIES_FILE_TEST = "/testdb.properties";
    private static final String PROPERTIES_FILE = "/db.properties";
    private static final HikariDataSource dataSourceTest;
    private static final HikariDataSource dataSource;
    static {
        HikariConfig testConfig = new HikariConfig(PROPERTIES_FILE_TEST);
        HikariConfig config = new HikariConfig(PROPERTIES_FILE);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        testConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceTest = new HikariDataSource(testConfig);
        dataSource = new HikariDataSource(config);
    }


    private enum Transactional {
        INSTANCE(ConnectionPools.dataSourceTest);
        private final HikariDataSource dataSource;
        private Transactional(HikariDataSource dataSource) {
            this.dataSource = dataSource;
        }
        public HikariDataSource getDataSource() {
            return dataSource;
        }
    }
    public static HikariDataSource getTransactional() {
        return Transactional.INSTANCE.getDataSource();
    }

    private enum Processing {
        INSTANCE(ConnectionPools.dataSource);
        private final HikariDataSource dataSource;
        private Processing(HikariDataSource dataSource) {
            this.dataSource = dataSource;
        }
        public HikariDataSource getDataSource() {
            return dataSource;
        }
    }

    public static HikariDataSource getProcessing() {
        return Processing.INSTANCE.getDataSource();
    }

    public static void main(String[] args) {
        logger.debug("starting");
        DataSource processing = ConnectionPools.getProcessing();
        logger.debug("processing started");
        DataSource transactional = ConnectionPools.getTransactional();
        logger.debug("transactional started");
        logger.debug("done");
    }
}
