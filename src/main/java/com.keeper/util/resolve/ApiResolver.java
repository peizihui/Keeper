package com.keeper.util.resolve;

/*
 * Created by @GoodforGod on 28.03.2017.
 */

/**
 * Default Comment
 */
public class ApiResolver {
    private static final String API_PRIVATE  = WebResolver.SECURED + "/api";
    private static final String API_PUBLIC   = "/api";
    private static final String API_VERSION  = "/v1.0";

    public static final String PROFILE  = API_PRIVATE + "/profile";
    public static final String TASK     = API_PRIVATE + "/tasks";
    public static final String ZONE     = API_PRIVATE + "/zone";
    public static final String ROUTE    = API_PRIVATE + "/routes";
    public static final String GEOPOINT = API_PRIVATE + "/points";
    public static final String GEO_USER = API_PRIVATE + "/geouser";
    public static final String SEARCH_TASK = API_PRIVATE + "/search";
}
