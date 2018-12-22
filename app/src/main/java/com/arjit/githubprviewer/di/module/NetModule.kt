package com.arjit.githubprviewer.di.module

import android.app.Application
import com.arjit.githubprviewer.BuildConfig
import com.arjit.githubprviewer.di.qualifier.BaseUrl
import com.arjit.githubprviewer.di.qualifier.HeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule(private val application: Application) {

    companion object {
        private val commonHeaderMap = mutableMapOf<String, String>().apply {
            put("Accept", "application/vnd.github.v3+json")
        }
    }

    @Provides
    @BaseUrl
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Named("cacheSize")
    @Singleton
    fun getCacheSize(): Long = 10 * 1024 * 1024L

    @Provides
    @HeaderInterceptor
    @Singleton
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
        commonHeaderMap.forEach { (header, value) -> request.addHeader(header, value) }
        chain.proceed(request.build())
    }

    @Provides
    @Singleton
    fun provideHttpCache(@Named("cacheSize") cacheSize: Long): Cache = Cache(application.cacheDir, cacheSize)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, httpCache: Cache,
        @HeaderInterceptor headerInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(httpCache)
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrl baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}
