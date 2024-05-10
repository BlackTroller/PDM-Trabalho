package com.example.projecto;

import model.CallMrData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface F1API {
    @GET("/api/f1/2020/drivers.json")
    Call<CallMrData> getDriversList();

    @GET("/api/f1/2020/constructors.json")
    Call<CallMrData> getConstructorsList();

    @GET("/api/f1/2020/circuits.json")
    Call<CallMrData> getCircuitsList();

    @GET("/api/f1/2020.json")
    Call<CallMrData> getSchedulesList();
}
