package com.demo.data;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectionPoolTest {

    @Test
    void shouldPassWhenEstablishConnectionSuccessfully() throws SQLException {
        assertNotNull(ConnectionPool.getConnection());
    }
}