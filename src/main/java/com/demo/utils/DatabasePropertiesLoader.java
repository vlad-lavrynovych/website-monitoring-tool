package com.demo.utils;

import org.apache.log4j.BasicConfigurator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabasePropertiesLoader {
    private static Properties prop = new Properties();

    static {
        BasicConfigurator.configure();
        try (InputStream input = DatabasePropertiesLoader.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find db.properties");
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getDatabaseUrl() {
        return prop.getProperty("database.url");
    }

    public static String getDatabaseUser() {
        return prop.getProperty("database.user");
    }

    public static String getDatabasePassword() {
        return prop.getProperty("database.password");
    }

    public static String getDatabaseDriver() {
        return prop.getProperty("database.driver");
    }

    public static int getMinDatabasePoolSize() {
        return Integer.parseInt(prop.getProperty("database.minConnectionPoolSize"));
    }

    public static int getMaxDatabasePoolSize() {
        return Integer.parseInt(prop.getProperty("database.maxConnectionPoolSize"));
    }

    public static int getMaxNumberOfPreparedStatements() {
        return Integer.parseInt(prop.getProperty("database.maxNumberOfPreparedStatements"));
    }

}
