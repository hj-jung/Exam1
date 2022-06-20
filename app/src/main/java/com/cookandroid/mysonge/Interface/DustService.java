package com.cookandroid.mysonge.Interface;

import com.cookandroid.mysonge.DTO.Dust.PmInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DustService {
    @GET("getMsrstnAcctoRltmMesureDnsty")
    Call<PmInfo> getDust(@Query("stationName") String stationName,
                         @Query("returnType") String returnType,
                         @Query("dataTerm") String dataTerm,
                         @Query("pageNo") int pageNo,
                         @Query("numOfRows") int numOfRows,
                         @Query("serviceKey") String serviceKey);
}
