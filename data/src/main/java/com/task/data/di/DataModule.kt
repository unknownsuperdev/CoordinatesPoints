package com.task.data.di

import com.task.data.BuildConfig
import com.task.data.dataservice.apiservice.PointService
import com.task.data.util.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@ComponentScan("com.task.data")
class DataModule {

    @Single
    fun providesInterceptor(): HeaderInterceptor =
        HeaderInterceptor()

    @Single
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Single
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(headerInterceptor)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    httpLoggingInterceptor
                )
            }
        }
        .build()

    @Single
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory .create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Single
    fun providePointService(retrofit: Retrofit): PointService =
        retrofit.create(PointService::class.java)

}