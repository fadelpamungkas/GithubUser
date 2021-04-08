package com.example.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.databinding.FragmentTabBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTabBinding.bind(view)
        binding.rvFragment.layoutManager = LinearLayoutManager(this.context)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable<User>(ARG_SECTION_PARCEL)

        val mainViewModel: MainViewModel = ViewModelProvider(this@TabFragment, MainViewModelFactory(activity!!.application)).get(MainViewModel::class.java)

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