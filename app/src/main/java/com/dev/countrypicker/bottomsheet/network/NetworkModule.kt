package com.dev.countrypicker.bottomsheet.network

import com.dev.countrypicker.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        responseInterceptor: ResponseInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        with(builder)
        {
            connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
        }
        val loggingInterceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(responseInterceptor)
        return builder.build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://www.restcountries.com/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}