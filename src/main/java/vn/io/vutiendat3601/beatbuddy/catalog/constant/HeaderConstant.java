package vn.io.vutiendat3601.beatbuddy.catalog.constant;

public interface HeaderConstant {
  String CORRELATION_ID = "X-Correlation-Id";
  String AUTH_ID = "X-Auth-Id";
  String USERNAME = "X-Username";

  String[] HEADERS = {CORRELATION_ID, AUTH_ID, USERNAME};
}
