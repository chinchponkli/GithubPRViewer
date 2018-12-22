package com.arjit.githubprviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.arjit.githubprviewer.GitApplication
import com.arjit.githubprviewer.R
import com.arjit.githubprviewer.databinding.FragmentPullRequestBinding
import com.arjit.githubprviewer.di.module.ActivityModule

class PullRequestFragment : Fragment() {

    companion object {
        val TAG = PullRequestFragment::class.java.simpleName

        fun newInstance() = PullRequestFragment()
    }

    private lateinit var binding: FragmentPullRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        GitApplication.applicationComponent!!.plus(ActivityModule(activity!!)).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_pull_request, container, false)
        return binding.root
    }
}
