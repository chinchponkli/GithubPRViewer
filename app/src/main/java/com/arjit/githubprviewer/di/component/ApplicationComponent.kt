package com.arjit.githubprviewer.di.component

import com.arjit.githubprviewer.di.module.ActivityModule
import com.arjit.githubprviewer.di.module.ApplicationModule
import com.arjit.githubprviewer.di.module.NetModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class, NetModule::class])
@Singleton
interface ApplicationComponent {

    fun plus(activityModule: ActivityModule): ActivityComponent
}
