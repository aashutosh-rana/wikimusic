package com.mobile.musicwiki.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mobile.musicwiki.network.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(ChuckerInterceptor(context)).build())
            .baseUrl(BASE_URL).build()

    }

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

}