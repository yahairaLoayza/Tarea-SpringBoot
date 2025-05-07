package com.codigo.retrofit.api;

import com.codigo.retrofit.aggregates.response.ReniecResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReniecApi {
    @GET("v2/reniec/dni")
    Call<ReniecResponse> getPersona(@Query("numero") String dni);
}
