package com.example.githubuser

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val users = MutableLiveData<ArrayList<User>>()
    private val userFollowing = MutableLiveData<ArrayList<User>>()
    private val userFollowers = MutableLiveData<ArrayList<User>>()
    private val userDetail = MutableLiveData<User>()
    private val databaseUser: LiveData<List<User>>
    private val repository: ApplicationRepository
    private var findUserDB: LiveData<User>? = null

    init {
        val dao: UserDAO = AppDatabase.getDatabase(application.applicationContext).userDao()
        repository = ApplicationRepository(dao)
        databaseUser = repository.users
    }

    fun insert(user: User) = viewModelScope.launch { repository.insert(user) }

    fun delete(user: User) = viewModelScope.launch { repository.delete(user.username) }

    fun findUser(username: String) : LiveData<User>? {
        findUserDB = repository.findUser(username)
        return findUserDB
    }

    fun getAllusers() : LiveData<List<User>> = databaseUser

    fun searchUser(query : String){
        val request = DataRepository.create()
        request.searchUser(query).enqueue(object : Callback<Result> {

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                users.postValue(response.body()!!.result)
                Log.d("onResponse", "searchUser: ${response.body()!!.result.size} - ${users.value?.size}")
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
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
                Log.e("onFailure", t.message.toString())
//                Toast.makeText(this@DetailActivity, "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
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
                Log.e("onFailure", t.message.toString())
//                Toast.makeText(context, "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
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
                Log.e("onFailure", t.message.toString())
//                Toast.makeText(context, "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadUserFollowers() : LiveData<ArrayList<User>> {
        Log.d("loadUserFollowers", "loadUserFollowers:${userFollowers.value?.size}")
        return userFollowers
    }
}
