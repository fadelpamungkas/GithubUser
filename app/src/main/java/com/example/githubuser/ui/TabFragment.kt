package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.viewmodel.MainViewModel
import com.example.githubuser.R
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.ViewModelFactory
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentTabBinding

class TabFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_PARCEL = "section_parcel"
        @JvmStatic
        fun newInstance(index: Int, data: User) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putParcelable(ARG_SECTION_PARCEL, data)
                }
            }
    }

    private lateinit var binding : FragmentTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTabBinding.bind(view)
        binding.rvFragment.layoutManager = LinearLayoutManager(this.context)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable<User>(ARG_SECTION_PARCEL)

        val mainViewModel: MainViewModel = ViewModelProvider(this@TabFragment, ViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)

        val adapter = UserAdapter()
        binding.rvFragment.adapter = adapter

        when (index) {
            1 -> {
                if (user != null) {
                    showLoading(true)
                    mainViewModel.getUserFollowing(user)
                }
                mainViewModel.loadUserFollowing().observe(viewLifecycleOwner, { loadFollowing ->
                    if (loadFollowing != null) {
                        adapter.setData(loadFollowing)
                        showLoading(false)
                    }
                })

            }

            2 -> {
                if (user != null) {
                    showLoading(true)
                    mainViewModel.getUserFollowers(user)
                }
                mainViewModel.loadUserFollowers().observe(viewLifecycleOwner, { loadFollowers ->
                    if (loadFollowers != null) {
                        adapter.setData(loadFollowers)
                        showLoading(false)
                    }
                })

            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFragment.visibility = View.VISIBLE
        } else {
            binding.progressBarFragment.visibility = View.GONE
        }
    }


}