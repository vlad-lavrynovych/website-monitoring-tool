package com.demo.data.repository;

import com.demo.data.domain.ConfigEntity;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.demo.data.ConnectionPool.getConnection;
import static com.demo.data.enums.QueriesEnum.*;

public class ConfigRepositoryImpl implements ConfigRepository {
    private static final Logger logger = LoggerFactory.getLogger(ConfigRepositoryImpl.class);

    static {
        BasicConfigurator.configure();
    }

    @Override
    public Optional<ConfigEntity> save(ConfigEntity configEntity) {
        Optional<ConfigEntity> result = Optional.empty();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONFIG.getQuery(), new String[]{"id"})) {
            getPSUpdateConfig(preparedStatement, configEntity).executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                configEntity.setId(preparedStatement.getGeneratedKeys().getLong(1));
                result = Optional.of(configEntity);
            }
            logger.info("Config saved successfully, id :: {}", configEntity.getId());
        } catch (SQLException ex) {
            logger.error("Error during inserting new config", ex);
        }
        return result;
    }

    @Override
    public void updateMonitoringStatus(Long id, boolean monitoringStatus) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MONITORING_STATUS.getQuery())) {

            getPSUpdateMonitoringStatus(preparedStatement, id, monitoringStatus).executeUpdate();
            logger.info("Monitoring status updated successfully, id :: {}", id);
        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONFIG_BY_ID.getQuery())) {

            getPSDelete(preparedStatement, id).executeUpdate();
            logger.info("Config deleted successfully, id :: {}", id);
        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public Optional<ConfigEntity> findById(long id) {
        ConfigEntity configEntity = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONFIG__BY_ID.getQuery())) {
            ResultSet rs = getPSFindById(preparedStatement, id).executeQuery();
            configEntity = mapResultSetToConfigEntity(rs);
        } catch (SQLException ex) {
            logger.error("Error during selecting config", ex);
        }
        return Optional.ofNullable(configEntity);
    }

    private PreparedStatement getPSFindById(PreparedStatement preparedStatement, long id) throws SQLException {
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getPSDelete(PreparedStatement preparedStatement, Long id) throws SQLException {
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getPSUpdateConfig(PreparedStatement preparedStatement, ConfigEntity config) throws SQLException {
        preparedStatement.setString(1, config.getUrl());
        preparedStatement.setLong(2, config.getQueryingInterval());
        preparedStatement.setLong(3, config.getResponseTimeOk());
        preparedStatement.setLong(4, config.getResponseTimeWarning());
        preparedStatement.setLong(5, config.getResponseTimeCritical());
        preparedStatement.setInt(6, config.getExpectedHttpResponseCode());
        preparedStatement.setInt(7, config.getMinExpectedResponseSize());
        preparedStatement.setInt(8, config.getMaxExpectedResponseSize());
        preparedStatement.setBoolean(9, config.isMonitored());
        return preparedStatement;
    }

    private PreparedStatement getPSUpdateMonitoringStatus(PreparedStatement preparedStatement, Long id, boolean monitoringStatus) throws SQLException {
        preparedStatement.setBoolean(1, monitoringStatus);
        preparedStatement.setLong(2, id);
        return preparedStatement;
    }

    private ConfigEntity mapResultSetToConfigEntity(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new ConfigEntity()
                    .setId(rs.getLong(1))
                    .setUrl(rs.getString(2))
                    .setQueryingInterval(rs.getInt(3))
                    .setResponseTimeOk(rs.getInt(4))
                    .setResponseTimeWarning(rs.getInt(5))
                    .setResponseTimeCritical(rs.getInt(6))
                    .setExpectedHttpResponseCode(rs.getInt(7))
                    .setMinExpectedResponseSize(rs.getInt(8))
                    .setMaxExpectedResponseSize(rs.getInt(9))
                    .setMonitored(rs.getBoolean(10));
        }
        return null;
    }
}
