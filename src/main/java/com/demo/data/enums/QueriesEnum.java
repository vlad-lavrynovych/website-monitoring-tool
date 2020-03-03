package com.demo.data.enums;

public enum QueriesEnum {
    //CONFIG QUERIES
    INSERT_CONFIG("INSERT INTO configs (\"url\", \"querying_interval\"," +
            "\"response_time_ok\", \"response_time_warning\", \"response_time_critical\", " +
            "\"expected_http_code\", \"min_expected_response_size\", \"max_expected_response_size\"," +
            " \"monitored\") " +
            "VALUES (" +
            "?, ?, ?, ?, ?, ?, ?, ?, ?);"),
    UPDATE_MONITORING_STATUS("update configs set monitored = ? where id = ?;"),
    DELETE_CONFIG_BY_ID("DELETE FROM configs WHERE id = ?;"),
    SELECT_ALL_CONFIGS("select id, url, querying_interval," +
            "response_time_ok, response_time_warning, response_time_critical, " +
            "expected_http_code, min_expected_response_size, max_expected_response_size," +
            " monitored from configs;"),
    SELECT_CONFIG__BY_ID("select id ,url, querying_interval," +
            "response_time_ok, response_time_warning, response_time_critical, " +
            "expected_http_code, min_expected_response_size, max_expected_response_size," +
            " monitored from configs where id = ?;"),
    //RESULTS QUERIES
    INSERT_RESULT("INSERT INTO check_results (\"id\", \"url\"," +
            " \"status\", \"response_code\", \"response_size\", " +
            "\"details\", \"monitored\", \"last_check\", \"duration\") " +
            "VALUES (" +
            "?, ?, ?, ?, ?, ?, ?, ?, ?);"),
    UPDATE_MONITORING_STATUS_RESULT("update check_results set monitored = ? where id = ?;"),
    DELETE_RESULT_BY_ID("DELETE FROM check_results WHERE id = ?;"),
    SELECT_ALL_RESULTS("select id ,url, status," +
            "response_code, response_size, " +
            "details, monitored, last_check, duration, " +
            " monitored from check_results order by check_results.id;"),
    SELECT_RESULT__BY_ID("select id ,url, status," +
            "response_code, response_size, " +
            "details, monitored, last_check, duration, " +
            " monitored from check_results where id = ?;"),
    UPDATE_RESULT("update check_results " +
            "set  url=?," +
            " status=?, response_code=?, response_size=?, " +
            "details=?, monitored=?, last_check=?, duration=? " +
            "where id = ?");
    private String query;

    QueriesEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
