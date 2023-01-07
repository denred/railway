package com.epam.redkin.model.connectionpool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class TestConnection {
    private static Connection connection;


    @BeforeEach
    public void init() throws SQLException {
        connection = ConnectionPools.getTesting().getConnection();
    }

    @AfterEach
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void shouldGetJDBCConnection() throws SQLException {
        try (Connection connection = ConnectionPools.getTesting().getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(query);
        return result;
    }

    private void createCustomerTable() throws SQLException {
        String customerTableQuery = "CREATE TABLE IF NOT EXISTS customers " +
                "(id INTEGER PRIMARY KEY, name TEXT, age INTEGER)";
        String customerEntryQuery = "INSERT INTO customers " +
                "VALUES (73, 'Brian', 33)";
        executeUpdate(customerTableQuery);
        executeUpdate(customerEntryQuery);
    }

    private void dropTable() throws SQLException{
        executeUpdate("DROP TABLE IF EXISTS customers");
    }


    @Test
    public void shouldCreateCustomerTable() throws SQLException {
        dropTable();
        createCustomerTable();
        connection.createStatement().execute("SELECT * FROM customers");
    }


    @Test
    public void shouldSelectData() throws SQLException {
        String query = "SELECT * FROM customers WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "Brian");
        boolean hasResult = statement.execute();
        assertTrue(hasResult);

        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        int age = resultSet.getInt("age");
        assertEquals(33, age);
    }

    @Test
    public void shouldInsertInResultSet() throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
        resultSet.moveToInsertRow();
        resultSet.updateLong("id", 3L);
        resultSet.updateString("name", "John");
        resultSet.updateInt("age", 18);
        resultSet.insertRow();
        resultSet.moveToCurrentRow();
    }


}