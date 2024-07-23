package com.task.data.dataservice.apiservice

import com.task.entities.responce.PointResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PointService {

    @GET("api/test/points")
    suspend fun getPoints(
        @Query("count") count: Int
    ): PointResponse
}
