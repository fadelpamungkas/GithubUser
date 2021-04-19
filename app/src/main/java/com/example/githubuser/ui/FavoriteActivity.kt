package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.ViewModelFactory
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerview.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.recyclerview.adapter = adapter

        favViewModel = ViewModelProvider(this@FavoriteActivity, ViewModelFactory(application)).get(FavoriteViewModel::class.java)

        favViewModel.getAllUsers().observe(this@FavoriteActivity, { allUsers ->
            if (allUsers != null) {
                adapter.setData(allUsers as ArrayList<User>)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER_DB, data)
                startActivity(intent)
            }

        })
    }
}