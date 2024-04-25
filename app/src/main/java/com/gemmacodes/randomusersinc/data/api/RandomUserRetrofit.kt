package com.gemmacodes.randomusersinc.data.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object RandomUserRetrofit {

    private const val BASE_URL = "https://randomuser.me/api/"
    private val retrofit = createRetrofit()
    val service: RandomUserService = retrofit.create(RandomUserService::class.java)

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface RandomUserService {
        @GET("/api/")
        suspend fun getRandomUsers(
            @Query("inc") fields: String,
            @Query("results") results: Int
        ): Response<RandomUserResponse>
    }

}





