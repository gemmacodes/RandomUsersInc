package com.gemmacodes.randomusersinc.data

import android.util.Log
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class RandomUserRetrofit : KoinComponent {

    private val BASE_URL = "https://randomuser.me/api/"
    private val retrofit = createRetrofit()
    private val service: RandomUserService = retrofit.create(RandomUserService::class.java)

    private fun createRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRandomUsers(
        amount: Int,
    ): RandomUserResponse? {
        val response = service.getRandomUsers(
            "picture,name,phone,email,gender,location,registered,id",
            amount,
        ).execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }

    interface RandomUserService {

        @GET("/api/")
        fun getRandomUsers(
            @Query("inc") fields: String,
            @Query("results") results: Int
        ): Call<RandomUserResponse>

    }

}





