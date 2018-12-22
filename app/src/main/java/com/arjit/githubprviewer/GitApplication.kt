package com.arjit.githubprviewer

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.arjit.githubprviewer.di.component.ApplicationComponent
import com.arjit.githubprviewer.di.component.DaggerApplicationComponent
import com.arjit.githubprviewer.di.module.ApplicationModule
import com.arjit.githubprviewer.di.module.NetModule

class GitApplication : MultiDexApplication() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @JvmStatic
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationModule(this).run {
            applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(this)
                .netModule(NetModule(this@GitApplication))
                .build()
        }
    }
}
