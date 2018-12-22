package com.arjit.githubprviewer.di.module

import android.app.Application
import android.content.Context
import com.arjit.githubprviewer.BuildConfig
import com.arjit.githubprviewer.di.qualifier.ApplicationContext
import com.arjit.githubprviewer.di.qualifier.Owner
import com.arjit.githubprviewer.di.qualifier.Repo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    @Owner
    fun provideOwner(): String = BuildConfig.OWNER

    @Provides
    @Singleton
    @Repo
    fun provideRepo(): String = BuildConfig.REPO

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}
