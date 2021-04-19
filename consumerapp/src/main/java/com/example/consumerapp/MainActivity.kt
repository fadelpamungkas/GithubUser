package com.example.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        val viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter

            binding.progressBar.visibility = View.VISIBLE

        }

        viewModel.setFavoriteUser(this)

        viewModel.getFavoriteUser().observe(this, { users ->
            adapter.setData(users)
            Log.d("ConsumerApp", "Viewmodel Observer")
            binding.progressBar.visibility = View.GONE
        })

    }
}