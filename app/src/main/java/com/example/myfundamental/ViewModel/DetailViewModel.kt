package com.example.myfundamental.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfundamental.DetailUser
import com.example.myfundamental.Network.ApiConfig
import com.example.myfundamental.Repository.FavoriteRepository
import com.example.myfundamental.UserResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel(){
    private val _detailUser = MutableLiveData<DetailUser>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _listFollowers = MutableLiveData<List<UserResult>>()
    private val _listFollowing = MutableLiveData<List<UserResult>>()
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    val detailUser: LiveData<DetailUser> = _detailUser
    val isLoading: LiveData<Boolean> = _isLoading
    val listFollowers: LiveData<List<UserResult>> = _listFollowers
    val listFollowing: LiveData<List<UserResult>> = _listFollowing

    companion object{
        const val TAG = "DetailViewModel"
    }

    fun getUser(username: String){
        findDetail(username)
        findFollowers(username)
        findFollowing(username)
    }

    fun findDetail(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: detail1${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: Detail2${t.message.toString()}")
            }
        })
    }

    fun findFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserResult>> {
            override fun onResponse(call: Call<List<UserResult>>, response: Response<List<UserResult>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<UserResult>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
    fun findFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserResult>> {
            override fun onResponse(call: Call<List<UserResult>>, response: Response<List<UserResult>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<UserResult>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
    fun getFavoriteUserByUsername(username: String): LiveData<UserResult>{
        return mFavoriteRepository.getFavoriteUserByUsername(username)
    }
    fun insert(favorite: UserResult) {
        mFavoriteRepository.insert(favorite)
    }
    fun delete(favorite: UserResult) {
        mFavoriteRepository.delete(favorite)
    }
    fun getAllFavorite(): LiveData<List<UserResult>> = mFavoriteRepository.getAllFavorite()
}