package com.talabaty.swever.admin.Mabi3at.RejectedReports;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://www.sellsapi.sweverteam.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
