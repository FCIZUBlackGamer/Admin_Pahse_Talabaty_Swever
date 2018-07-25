package com.talabaty.swever.admin.Mabi3at.RejectedReports;
import com.talabaty.swever.admin.Mabi3at.SearchModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/order/RefusedReport")
    Call<SearchModel> Search(@Body SearchModel Search);
}
