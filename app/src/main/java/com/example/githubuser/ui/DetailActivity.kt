package com.example.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.*
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.MainViewModel
import com.example.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_USER_DB = "extra_user_db"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val mainViewModel : MainViewModel = ViewModelProvider(this, ViewModelFactory(application)).get(MainViewModel::class.java)
        val favViewModel : FavoriteViewModel = ViewModelProvider(this, ViewModelFactory(application)).get(FavoriteViewModel::class.java)
        var favStatus = false

        if (intent.getParcelableExtra<User>(EXTRA_USER_DB) != null) {
            user = intent.getParcelableExtra<User>(EXTRA_USER_DB) as User
            bindUser(user)
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24)
            favStatus = true
        } else {
            user = intent.getParcelableExtra<User>(EXTRA_USER) as User
            favViewModel.findUser(user.username)!!.observe(this, { findUserDB ->
                if (findUserDB != null && findUserDB.username == user.username) {
                    bindUser(findUserDB)
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24)
                    favStatus = true
                } else {
                    showLoading(true)
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                    favStatus = false
                    mainViewModel.getUserDetail(user)
                    mainViewModel.loadUserDetail().observe(this@DetailActivity, { userDetail ->
                        if (userDetail != null) {
                            user = userDetail
                            bindUser(user)
                            showLoading(false)
                        }
                    })
                }
            })

        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, user)
        binding.viewpager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = resources.getString(MainActivity.TAB_TITLES[position])
        }.attach()

        binding.fabFavorite.setOnClickListener { view ->
            if (!favStatus) {
                favViewModel.insert(user)
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24)
                Snackbar.make(view, getString(R.string.added_to_favorite), Snackbar.LENGTH_SHORT).show()
            } else {
                favViewModel.delete(user)
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                Snackbar.make(view, getString(R.string.removed_from_favorite), Snackbar.LENGTH_SHORT).show()
            }
            favStatus = !favStatus
        }
    }

    private fun bindUser(user: User) {
        binding.nameDetail.text = user.name
        binding.usernameDetail.text = user.username
        Glide.with(this@DetailActivity)
            .load(user.avatar)
            .apply(RequestOptions().override(350, 350))
            .into(binding.imageDetail)
        binding.repositoryDetail.text = user.repository
        binding.locationDetail.text = user.location
        binding.companyDetail.text = user.company
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