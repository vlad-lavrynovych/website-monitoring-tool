package com.demo.data.repository;

import com.demo.data.domain.CheckResultsEntity;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.demo.data.ConnectionPool.getConnection;
import static com.demo.data.enums.QueriesEnum.*;

public class CheckResultsRepositoryImpl implements CheckResultsRepository {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultsRepositoryImpl.class);

    static {
        BasicConfigurator.configure();
    }

    @Override
    public Optional<CheckResultsEntity> save(CheckResultsEntity resultEntity) {
        Optional<CheckResultsEntity> result = Optional.empty();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESULT.getQuery(), new String[]{"id"})) {
            getPSInsertResult(preparedStatement, resultEntity).executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                resultEntity.setId(preparedStatement.getGeneratedKeys().getLong(1));
                result = Optional.of(resultEntity);
            }
            logger.info("RESULT saved successfully, id :: {}", resultEntity.getId());
        } catch (SQLException ex) {
            logger.error("Error during inserting new result", ex);
        }
        return result;
    }

    @Override
    public void updateMonitoringStatus(Long id, boolean monitoringStatus) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MONITORING_STATUS_RESULT.getQuery())) {

            getPSUpdateMonitoringStatus(preparedStatement, id, monitoringStatus).executeUpdate();
            logger.info("Monitoring status updated successfully, id :: {}", id);
        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESULT_BY_ID.getQuery())) {

            getPSDelete(preparedStatement, id).executeUpdate();
            logger.info("Result deleted successfully, id :: {}", id);
        } catch (SQLException ex) {
            logger.error("Error during updating monitoring status", ex);
        }
    }

    @Override
    public Optional<CheckResultsEntity> findById(long id) {
        CheckResultsEntity resultEntity = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESULT__BY_ID.getQuery())) {
            ResultSet rs = getPSFindById(preparedStatement, id).executeQuery();
            if (rs.next())
                resultEntity = mapResultSetToCheckResultsEntity(rs);
        } catch (SQLException ex) {
            logger.error("Error during selecting result", ex);
        }
        return Optional.ofNullable(resultEntity);
    }

    @Override
    public List<CheckResultsEntity> findAll() {
        List<CheckResultsEntity> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESULTS.getQuery())) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CheckResultsEntity t = mapResultSetToCheckResultsEntity(rs);
                result.add(t);
            }
        } catch (SQLException ex) {
            logger.error("Error during selecting result", ex);
        }
        return result;
    }

    @Override
    public Optional<CheckResultsEntity> updateCheckResult(CheckResultsEntity c) {
        Optional<CheckResultsEntity> result = Optional.empty();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESULT.getQuery())) {
            getPSUpdateResult(preparedStatement, c).executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                result = Optional.of(c);
            }
            logger.info("RESULT updated successfully, id :: {}", c.getId());
        } catch (SQLException ex) {
            logger.error("Error during update of result", ex);
        }
        return result;
    }

    private PreparedStatement getPSUpdateResult(PreparedStatement preparedStatement, CheckResultsEntity result) throws SQLException {
        preparedStatement.setString(1, result.getUrl());
        preparedStatement.setString(2, result.getStatus());
        preparedStatement.setInt(3, result.getResponseCode());
        preparedStatement.setInt(4, result.getResponseSize());
        preparedStatement.setString(5, result.getDetails());
        preparedStatement.setBoolean(6, result.isMonitored());
        preparedStatement.setTimestamp(7, new Timestamp(result.getLastCheck().getTime()));
        preparedStatement.setLong(8, result.getDuration());
        preparedStatement.setLong(9, result.getId());
        return preparedStatement;
    }

    private PreparedStatement getPSFindById(PreparedStatement preparedStatement, long id) throws SQLException {
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getPSDelete(PreparedStatement preparedStatement, Long id) throws SQLException {
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getPSInsertResult(PreparedStatement preparedStatement, CheckResultsEntity result) throws SQLException {
        preparedStatement.setLong(1, result.getId());
        preparedStatement.setString(2, result.getUrl());
        preparedStatement.setString(3, result.getStatus());
        preparedStatement.setInt(4, result.getResponseCode());
        preparedStatement.setInt(5, result.getResponseSize());
        preparedStatement.setString(6, result.getDetails());
        preparedStatement.setBoolean(7, result.isMonitored());
        preparedStatement.setTimestamp(8, new Timestamp(result.getLastCheck().getTime()));
        preparedStatement.setLong(9, result.getDuration());
        return preparedStatement;
    }

    private PreparedStatement getPSUpdateMonitoringStatus(PreparedStatement preparedStatement, Long id, boolean monitoringStatus) throws SQLException {
        preparedStatement.setBoolean(1, monitoringStatus);
        preparedStatement.setLong(2, id);
        return preparedStatement;
    }

    private CheckResultsEntity mapResultSetToCheckResultsEntity(ResultSet rs) throws SQLException {
        return new CheckResultsEntity()
                .setId(rs.getLong(1))
                .setUrl(rs.getString(2))
                .setStatus(rs.getString(3))
                .setResponseCode(rs.getInt(4))
                .setResponseSize(rs.getInt(5))
                .setDetails(rs.getString(6))
                .setMonitored(rs.getBoolean(7))
                .setLastCheck(new Date(rs.getTimestamp(8).getTime()))
                .setDuration(rs.getLong(9));

    }
}
