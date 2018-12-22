package com.arjit.githubprviewer.service

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.arjit.githubprviewer.ui.viewmodel.PullRequestItemViewModel
import io.reactivex.disposables.CompositeDisposable

class PullRequestDataSource(
    private val pullRequestService: PullRequestService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, PullRequestItemViewModel>() {

    private var sourceIndex: Int = 1
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PullRequestItemViewModel>) {
        Log.d("Loading Page: ", "$sourceIndex")
        pullRequestService.getPullRequests(1)
            .map { pullRequest -> PullRequestItemViewModel(pullRequest) }
            .toList()
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                isLoading.postValue(true)
            }
            .subscribe(
                { pullRequestItems ->
                    Log.d("Loaded Page: ", "$sourceIndex")
                    isLoading.postValue(false)
                    sourceIndex += 1
                    callback.onResult(pullRequestItems, null, sourceIndex)
                },
                { throwable ->
                    Log.d("Failed to Load Page: ", "$throwable")
                    isLoading.postValue(false)
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequestItemViewModel>) {
        Log.d("Loading Page: ", "$sourceIndex")
        pullRequestService.getPullRequests(sourceIndex)
            .map { pullRequest -> PullRequestItemViewModel(pullRequest) }
            .toList()
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                isLoading.postValue(true)
            }
            .subscribe(
                { pullRequestItems ->
                    Log.d("Loaded Page: ", "$sourceIndex")
                    isLoading.postValue(false)
                    sourceIndex += 1
                    callback.onResult(pullRequestItems, sourceIndex)
                },
                { throwable ->
                    Log.d("Failed to Load Page: ", "$throwable")
                    isLoading.postValue(false)
                }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequestItemViewModel>) {
        // not going to do anything here
    }
}
