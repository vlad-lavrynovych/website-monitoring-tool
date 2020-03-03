package com.demo.data;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.demo.utils.DatabasePropertiesLoader.*;

public class ConnectionPool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(getDatabaseUrl());
        ds.setUsername(getDatabaseUser());
        ds.setPassword(getDatabasePassword());
        ds.setMinIdle(getMinDatabasePoolSize());
        ds.setMaxIdle(getMaxDatabasePoolSize());
        ds.setMaxOpenPreparedStatements(getMaxNumberOfPreparedStatements());
        ds.setDriverClassName(getDatabaseDriver());
    }

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    public static BasicDataSource getDs() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static class DataSourceHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

}
