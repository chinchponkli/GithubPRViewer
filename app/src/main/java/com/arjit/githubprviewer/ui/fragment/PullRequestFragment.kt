package com.arjit.githubprviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjit.githubprviewer.GitApplication
import com.arjit.githubprviewer.R
import com.arjit.githubprviewer.databinding.FragmentPullRequestBinding
import com.arjit.githubprviewer.di.module.ActivityModule
import com.arjit.githubprviewer.ui.viewmodel.PullRequestListViewModel
import javax.inject.Inject

class PullRequestFragment : Fragment() {

    companion object {
        val TAG: String = PullRequestFragment::class.java.simpleName

        fun newInstance(): PullRequestFragment = PullRequestFragment()
    }

    @Inject
    internal lateinit var pullRequestListViewModel: PullRequestListViewModel
    private lateinit var binding: FragmentPullRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        GitApplication.applicationComponent!!.plus(ActivityModule(activity!!)).inject(this)
        lifecycle.addObserver(pullRequestListViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_pull_request, container, false)
        binding.viewModel = pullRequestListViewModel
        setupRecyclerView()
        observeData()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.pullRequestListView.layoutManager = LinearLayoutManager(binding.pullRequestListView.context)
        binding.pullRequestListView.adapter = pullRequestListViewModel.adapter
    }

    private fun observeData() {
        pullRequestListViewModel.livePullRequests.observe(this, Observer { newList ->
            pullRequestListViewModel.adapter.submitList(newList)
        })
    }
}
