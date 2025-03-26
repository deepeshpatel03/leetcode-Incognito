package com.example.leetincoginto.hiltmodule

import com.example.leetincoginto.Apisetup.LeetCodeApiService
import com.example.leetincoginto.googleauth.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepository()
    }
    private const val BASE_URL_LEETCODE = "https://alfa-leetcode-api.onrender.com/"
    private const val BASE_URL_CLIST = "https://clist.by:443/api/v4/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("LeetCodeApi")
    fun provideLeetCodeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LEETCODE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("ClistApi")
    fun provideClistRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CLIST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("LeetCodeApi")
    fun provideLeetCodeApiService(@Named("LeetCodeApi") retrofit: Retrofit): LeetCodeApiService {
        return retrofit.create(LeetCodeApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("ClistApi")
    fun provideClistApiService(@Named("ClistApi") retrofit: Retrofit): LeetCodeApiService {
        return retrofit.create(LeetCodeApiService::class.java)
    }
}