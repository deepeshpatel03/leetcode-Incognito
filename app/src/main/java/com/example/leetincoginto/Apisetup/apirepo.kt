package com.example.leetincoginto.Apisetup

import android.util.Log
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Named

class LeetCodeRepository @Inject constructor(
    @Named("LeetCodeApi") private val leetCodeApi: LeetCodeApiService,
    @Named("ClistApi") private val clistApi: LeetCodeApiService
) {

    suspend fun fetchUserProfile(username: String): LeetCodeProfile? {
        return try {
            leetCodeApi.getUserProfile(username)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchContestHistory(username: String): ContestData? {
        return try {
            leetCodeApi.getUserContestHistory(username)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchLeetCodeUserProfile(username: String): LeetCodeUserProfile? {
        return try {
            leetCodeApi.getLeetCodeUserProfile(username)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchUserStats(username: String): UserStatsResponse? {
        return try {
            leetCodeApi.getLeetCodeUserStates(username)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchProblemOfTheDay(): LeetCodeProblem? {

     return  try {
            leetCodeApi.getproblemoftheday()
        } catch (e: Exception) {
            Log.d("FETCH_ERROR",  e.toString()  )
            null
        }
    }

    suspend fun fetchUpcomingContests(): ContestResponse? {
        return try {
            clistApi.getupcomingcontest()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAccountDetail(username: String, a: Int, b: Int, c: Int): ApiResponse? {
        return try {
            clistApi.getAccountDetail(username, a, b, c)
        } catch (e: Exception) {
            null
        }
    }
}
