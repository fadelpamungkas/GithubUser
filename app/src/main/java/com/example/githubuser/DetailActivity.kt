package com.example.githubuser

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, user)
        binding.viewpager.adapter = sectionsPagerAdapter

        val mainViewModel : MainViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        showLoading(true)
        mainViewModel.getUserDetail(user)
        mainViewModel.loadUserDetail().observe(this@DetailActivity, { user ->
            if (user != null) {
                binding.nameDetail.text = user.name
                binding.usernameDetail.text = user.username
                Glide.with(this@DetailActivity)
                        .load(user.avatar)
                        .apply(RequestOptions().override(350, 350))
                        .into(binding.imageDetail)
                binding.repositoryDetail.text = user.repository
                binding.locationDetail.text = user.location
                binding.companyDetail.text = user.company
                showLoading(false)
            }
        })

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = resources.getString(MainActivity.TAB_TITLES[position])
        }.attach()


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}