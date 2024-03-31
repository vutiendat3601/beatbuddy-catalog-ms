package vn.io.vutiendat3601.beatbuddy.catalog.util;

public class UserContext {
    public static final String ANONYMOUS = "_";
    public static final String CORRELATION_ID_HEADER = "beatbuddy-correlation-id";
    public static final String AUTH_ID_HEADER = "beatbuddy-sub";
    public static final String USERNAME_HEADER = "beatbuddy-username";

    public static final String[] HEADERS = { CORRELATION_ID_HEADER, AUTH_ID_HEADER, USERNAME_HEADER };
    
}
