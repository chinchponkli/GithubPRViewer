package com.arjit.githubprviewer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.arjit.githubprviewer.R
import com.arjit.githubprviewer.databinding.ItemPullRequestBinding
import com.arjit.githubprviewer.di.scope.PerActivity
import com.arjit.githubprviewer.model.PullRequest
import com.arjit.githubprviewer.service.PullRequestDataSource
import com.arjit.githubprviewer.service.PullRequestDataSourceFactory
import com.arjit.githubprviewer.ui.adapter.BindingViewHolder
import com.arjit.githubprviewer.ui.adapter.GenericPagedAdapter
import javax.inject.Inject

@PerActivity
class PullRequestListViewModel @Inject internal constructor(
    pullRequestDataSourceFactory: PullRequestDataSourceFactory
) : ListViewModel<PullRequestItemViewModel, PullRequest>() {

    companion object {
        private const val PAGE_SIZE = 10
        private const val ENABLE_PAGE_PACEHOLDERS = false
    }

    var livePullRequests: LiveData<PagedList<PullRequestItemViewModel>>

    init {
        val pagedListConfig =
            PagedList.Config.Builder()
                .setEnablePlaceholders(ENABLE_PAGE_PACEHOLDERS)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        livePullRequests = LivePagedListBuilder(pullRequestDataSourceFactory, pagedListConfig).build()

        isLoading = Transformations
            .switchMap(pullRequestDataSourceFactory.livePullRequestDataSource, PullRequestDataSource::isLoading)
    }

    val adapter = Adapter(diffUtilItemCallback)

    class Adapter(itemCallback: DiffUtil.ItemCallback<PullRequestItemViewModel>) :
        GenericPagedAdapter<PullRequestItemViewModel, BindingViewHolder<ItemPullRequestBinding>,
                ItemPullRequestBinding, PullRequest>(itemCallback, R.layout.item_pull_request)
}
