package com.epam.redkin.model.connectionpool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class ConnectionPools {
    private static final String PROPERTIES_FILE = "/db.properties";
    private static final HikariDataSource dataSource;
    static {
        HikariConfig config = new HikariConfig(PROPERTIES_FILE);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    private enum Processing {
        INSTANCE(ConnectionPools.dataSource);
        private final HikariDataSource dataSource;
        Processing(HikariDataSource dataSource) {
            this.dataSource = dataSource;
        }
        public HikariDataSource getDataSource() {
            return dataSource;
        }
    }

    public static HikariDataSource getProcessing() {
        return Processing.INSTANCE.getDataSource();
    }
}
