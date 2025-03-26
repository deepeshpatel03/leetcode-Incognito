package com.example.leetincoginto.Apisetup


import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeetCodeApiService {

    @GET("userProfile/{username}")
    suspend fun getUserProfile(@Path("username") username: String): LeetCodeProfile

    @GET("{username}/contest")
    suspend fun getUserContestHistory(@Path("username") username: String): ContestData

    @GET("{username}")
    suspend fun getLeetCodeUserProfile(@Path("username") username: String):  LeetCodeUserProfile

    @GET("daily")
    suspend fun getproblemoftheday(): LeetCodeProblem

    @GET("skillStats/{username}")
    suspend fun getLeetCodeUserStates(@Path("username") username: String):  UserStatsResponse

    @GET("account/?format=json&resource=leetcode.com")
    suspend fun getAccountDetail( @Query("handle__startswith") username: String,
                                  @Query("rating__gt") minRating: Int,
                                  @Query("rating__lt") maxRating: Int,
                                  @Query("limit") limit: Int,
                                  @Query("username") apiUsername: String = "deepesh",
                                  @Query("api_key") apiKey: String = "1ed5daa27fb823b5d46859c8b68f789f1946f615"
    ): ApiResponse

    @GET("contest/?format=json&upcoming=true&resource__iregex=leetcode.com")
    suspend fun getupcomingcontest(
        @Query("username") apiUsername: String = "deepesh",
        @Query("api_key") apiKey: String = "1ed5daa27fb823b5d46859c8b68f789f1946f615"
    ):ContestResponse

}