package com.example.myfundamental.Network

import com.example.myfundamental.DetailUser
import com.example.myfundamental.UserResponse
import com.example.myfundamental.UserResult
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResult>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResult>>
}
