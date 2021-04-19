package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.viewmodel.MainViewModel
import com.example.githubuser.R
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.ViewModelFactory
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerview.adapter = adapter

        mainViewModel = ViewModelProvider(this@MainActivity, ViewModelFactory(application)).get(MainViewModel::class.java)
        mainViewModel.loadSearchUser().observe(this@MainActivity, { loadUsers ->
            if (loadUsers != null) {
                adapter.setData(loadUsers)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }

        })

        binding.btnSearch.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etSearch.text)){
                showLoading(true)
                mainViewModel.searchUser(binding.etSearch.text.toString())
            } else {
                Toast.makeText(this@MainActivity, getString(R.string.text_to_search), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite ->
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))

            R.id.setting ->
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))

            else ->
                super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }
}