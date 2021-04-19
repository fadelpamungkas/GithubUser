package com.example.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.TabFragment
import com.example.githubuser.model.User

class SectionsPagerAdapter(activity: AppCompatActivity, private val data: User) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return TabFragment.newInstance(position + 1, data)
    }

    override fun getItemCount(): Int {
        return 2
    }


}