package com.arjit.githubprviewer.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arjit.githubprviewer.databinding.ActivityPullRequestBinding
import com.arjit.githubprviewer.GitApplication
import com.arjit.githubprviewer.R
import com.arjit.githubprviewer.di.module.ActivityModule
import com.arjit.githubprviewer.ui.fragment.PullRequestFragment

class PullRequestActivity : AppCompatActivity() {

    private var pullRequestFragment: PullRequestFragment? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitApplication.applicationComponent!!
            .plus(ActivityModule(this)).inject(this)

        val binding = DataBindingUtil.setContentView<ActivityPullRequestBinding>(this, R.layout.activity_pull_request)
        setSupportActionBar(binding.toolbar)

        pullRequestFragment = supportFragmentManager.findFragmentByTag(PullRequestFragment.TAG) as? PullRequestFragment

        if (pullRequestFragment == null) {
            pullRequestFragment = PullRequestFragment.newInstance()
            supportFragmentManager.beginTransaction().run {
                add(binding.fragmentContainer.id, pullRequestFragment!!, PullRequestFragment.TAG)
                commit()
            }
        }
    }
}
