package com.demo.data.enums;

public enum QueriesEnum {
    INSERT_CONFIG("INSERT INTO configs (\"url\", \"querying_interval\"," +
            "\"response_time_ok\", \"response_time_warning\", \"response_time_critical\", " +
            "\"expected_http_code\", \"min_expected_response_size\", \"max_expected_response_size\"," +
            " \"monitored\") " +
            "VALUES (" +
            "?, ?, ?, ?, ?, ?, ?, ?, ?);"),
    UPDATE_MONITORING_STATUS("update configs set monitored = ? where id = ?;"),
    DELETE_BY_ID("DELETE FROM configs WHERE id = ?;"),
    SELECT_ALL("select id, url, querying_interval," +
            "response_time_ok, response_time_warning, response_time_critical, " +
            "expected_http_code, min_expected_response_size, max_expected_response_size," +
            " monitored from configs;"),
    SELECT_BY_ID("select id ,url, querying_interval," +
            "response_time_ok, response_time_warning, response_time_critical, " +
            "expected_http_code, min_expected_response_size, max_expected_response_size," +
            " monitored from configs where id = ?;");
    private String query;

    QueriesEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
