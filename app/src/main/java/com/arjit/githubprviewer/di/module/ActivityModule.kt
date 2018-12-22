package com.arjit.githubprviewer.di.module

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.arjit.githubprviewer.di.qualifier.ActivityContext
import com.arjit.githubprviewer.di.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity = activity

    @Provides
    @ActivityContext
    @PerActivity
    internal fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    internal fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager
}
