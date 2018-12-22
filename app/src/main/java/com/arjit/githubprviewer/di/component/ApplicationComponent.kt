package com.arjit.githubprviewer.di.component

import com.arjit.githubprviewer.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent
