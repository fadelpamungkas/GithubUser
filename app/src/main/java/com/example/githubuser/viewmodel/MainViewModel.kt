package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.githubuser.remotesource.DataRepository
import com.example.githubuser.model.Result
import com.example.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val users = MutableLiveData<ArrayList<User>>()
    private val userFollowing = MutableLiveData<ArrayList<User>>()
    private val userFollowers = MutableLiveData<ArrayList<User>>()
    private val userDetail = MutableLiveData<User>()

    fun searchUser(query : String){
        val request = DataRepository.create()
        request.searchUser(query).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                users.postValue(response.body()?.result)
                Log.d(
                        "onResponse",
                        "searchUser: ${response.body()?.result?.size} - ${users.value?.size}"
                )
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Toast.makeText(getApplication(), "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("onFailure", t.message.toString())
            }

        })

    }

    fun loadSearchUser() : LiveData<ArrayList<User>>{
        Log.d("loadSearchUser", "users:${users.value?.size}")
        return users
    }

    fun getUserDetail(user : User) {
        val request = DataRepository.create()
        request.getUser(user.username).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                userDetail.postValue(response.body())
                Log.d("onResponse", "getUserDetail: ${response.body()!!.name} - ${userDetail.value?.name}")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(getApplication(), "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    fun loadUserDetail() : LiveData<User> {
        Log.d("userDetail", "userDetail:${userDetail.value}")
        return userDetail
    }

    fun getUserFollowing(user: User){
        val request = DataRepository.create()
        request.getFollowing(user.username).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
            ) {
                userFollowing.postValue(response.body() as ArrayList<User>)
                Log.d("onResponse", "getUserFollowing: ${response.body()!!.size} - ${userFollowing.value?.size}")
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(getApplication(), "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    fun loadUserFollowing() : LiveData<ArrayList<User>> {
        Log.d("loadUserFollowing", "loadUserFollowing:${userFollowing.value?.size}")
        return userFollowing
    }

    fun getUserFollowers(user: User){
        val request = DataRepository.create()
        request.getFollowers(user.username).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
            ) {
                userFollowers.postValue(response.body() as ArrayList<User>)
                Log.d("onResponse", "getUserFollowers: ${response.body()!!.size} - ${userFollowers.value?.size}")
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(getApplication(), "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    fun loadUserFollowers() : LiveData<ArrayList<User>> {
        Log.d("loadUserFollowers", "loadUserFollowers:${userFollowers.value?.size}")
        return userFollowers
    }
}
