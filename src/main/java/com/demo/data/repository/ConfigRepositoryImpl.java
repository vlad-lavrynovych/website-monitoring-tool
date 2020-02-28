package com.demo.data.repository;

import com.demo.data.domain.ConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.demo.data.ConnectionPool.getConnection;
import static com.demo.data.queries.QueriesEnum.*;

public class ConfigRepositoryImpl implements ConfigRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID.getQuery())) {

            getPSDelete(preparedStatement, id).executeUpdate();

        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public Optional<ConfigEntity> findById(long id) {
        ConfigEntity configEntity = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID.getQuery())) {
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
        preparedStatement.setLong(1, config.getQueryingInterval());
        preparedStatement.setLong(2, config.getResponseTimeOk());
        preparedStatement.setLong(3, config.getResponseTimeWarning());
        preparedStatement.setLong(4, config.getResponseTimeCritical());
        preparedStatement.setInt(5, config.getExpectedHttpResponseCode());
        preparedStatement.setInt(6, config.getMinExpectedResponseSize());
        preparedStatement.setInt(7, config.getMaxExpectedResponseSize());
        preparedStatement.setBoolean(8, config.isMonitored());
        return preparedStatement;
    }

    private PreparedStatement getPSUpdateMonitoringStatus(PreparedStatement preparedStatement, Long id, boolean monitoringStatus) throws SQLException {
        preparedStatement.setBoolean(1, monitoringStatus);
        preparedStatement.setLong(2, id);
        return preparedStatement;
    }

    private ConfigEntity mapResultSetToConfigEntity(ResultSet rs) throws SQLException {
        if (rs.next()) {
            ConfigEntity configEntity = new ConfigEntity();
            configEntity.setId(rs.getLong(1));
            configEntity.setQueryingInterval(rs.getInt(2));
            configEntity.setResponseTimeOk(rs.getInt(3));
            configEntity.setResponseTimeWarning(rs.getInt(4));
            configEntity.setResponseTimeCritical(rs.getInt(5));
            configEntity.setExpectedHttpResponseCode(rs.getInt(6));
            configEntity.setMinExpectedResponseSize(rs.getInt(7));
            configEntity.setMaxExpectedResponseSize(rs.getInt(8));
            configEntity.setMonitored(rs.getBoolean(9));
            return configEntity;
        }
        return null;
    }
}
