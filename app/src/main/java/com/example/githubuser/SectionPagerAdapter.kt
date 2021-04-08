package com.example.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, val data: User) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return TabFragment.newInstance(position + 1, data)
    }

    override fun getItemCount(): Int {
        return 2
    }


}