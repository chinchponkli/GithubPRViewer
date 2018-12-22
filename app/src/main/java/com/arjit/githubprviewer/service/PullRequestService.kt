package com.arjit.githubprviewer.service

import com.arjit.githubprviewer.di.qualifier.Owner
import com.arjit.githubprviewer.di.qualifier.Repo
import com.arjit.githubprviewer.model.PullRequest
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PullRequestService @Inject internal constructor(
    private val pullRequestApi: PullRequestApi,
    @Owner private val owner: String,
    @Repo private val repo: String
) {

    companion object {
        private val defaultQueryParams = mutableMapOf<String, String>().apply {
            put("sort", "created")
            put("direction", "desc")
            put("per_page", "10")
        }
    }

    fun getPullRequests(page: Int): Observable<PullRequest> = pullRequestApi
        .getPullRequests(owner, repo, defaultQueryParams.apply { put("page", "$page") })
        .subscribeOn(Schedulers.io())
        .flatMapIterable { pullRequest -> pullRequest }

    interface PullRequestApi {
        @GET("/repos/{owner}/{repo}/pulls")
        fun getPullRequests(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @QueryMap params: Map<String, String>
        ): Observable<List<PullRequest>>
    }
}
