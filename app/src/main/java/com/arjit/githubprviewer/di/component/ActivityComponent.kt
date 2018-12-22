package com.arjit.githubprviewer.di.component

import com.arjit.githubprviewer.di.module.ActivityModule
import com.arjit.githubprviewer.di.scope.PerActivity
import com.arjit.githubprviewer.ui.activity.PullRequestActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(pullRequestActivity: PullRequestActivity)
}
