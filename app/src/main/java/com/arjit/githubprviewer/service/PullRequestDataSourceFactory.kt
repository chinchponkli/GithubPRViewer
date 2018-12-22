package com.arjit.githubprviewer.service

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arjit.githubprviewer.ui.viewmodel.PullRequestItemViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PullRequestDataSourceFactory @Inject internal constructor(
    private val pullRequestService: PullRequestService
) : DataSource.Factory<Int, PullRequestItemViewModel>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var livePullRequestDataSource = MutableLiveData<PullRequestDataSource>()

    override fun create(): DataSource<Int, PullRequestItemViewModel> {
        val pullRequestDataSource = PullRequestDataSource(pullRequestService, compositeDisposable)
        livePullRequestDataSource.postValue(pullRequestDataSource)
        return pullRequestDataSource
    }
}
