package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.entity.Carriage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CarriageRepositoryImplTest {
    /**
     * Method under test: {@link CarriageRepositoryImpl#create(Carriage)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate() {
        // TODO: Complete this test.
        //   Reason: R006 Static initializer failed.
        //   The static initializer of
        //   com.epam.redkin.model.connectionpool.ConnectionPools
        //   threw com.zaxxer.hikari.pool.HikariPool$PoolInitializationException while trying to load it.
        //   Make sure the static initializer of ConnectionPools
        //   can be executed without throwing exceptions.
        //   Exception: com.zaxxer.hikari.pool.HikariPool$PoolInitializationException: Failed to initialize pool: Could not create connection to database server.
        //       at com.zaxxer.hikari.pool.HikariPool.throwPoolInitializationException(HikariPool.java:596)
        //       at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:582)
        //       at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:100)
        //       at com.zaxxer.hikari.HikariDataSource.<init>(HikariDataSource.java:81)
        //       at com.epam.redkin.model.connectionpool.ConnectionPools.<clinit>(ConnectionPools.java:13)
        //       at com.epam.redkin.model.repository.impl.CarriageRepositoryImpl.<clinit>(CarriageRepositoryImpl.java:21)
        //       at java.lang.Class.forName0(Native Method)
        //       at java.lang.Class.forName(Class.java:467)

        // Arrange
        // TODO: Populate arranged inputs
        CarriageRepositoryImpl carriageRepositoryImpl = null;
        Carriage carriage = null;

        // Act
        int actualCreateResult = carriageRepositoryImpl.create(carriage);

        // Assert
        // TODO: Add assertions on result
    }
}

