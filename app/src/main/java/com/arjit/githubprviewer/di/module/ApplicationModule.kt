package com.arjit.githubprviewer.di.module

import android.app.Application
import android.content.Context
import com.arjit.githubprviewer.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(): Context = application
}
