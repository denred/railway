package com.epam.redkin.model.connectionpool;

import java.sql.Connection;

public interface ConnectionCreator {

    Connection getConnection();
}
