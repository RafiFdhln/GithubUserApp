package com.example.myfundamental.Repository

import com.example.myfundamental.Database.FavoriteDao
import com.example.myfundamental.Database.FavoriteRoomDatabase
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myfundamental.UserResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.FavoriteDao()
    }
    fun getFavoriteUserByUsername(username: String): LiveData<UserResult> = mFavoriteDao.getFavoriteUserByUsername(username)
    fun getAllFavorite(): LiveData<List<UserResult>> = mFavoriteDao.getAllFavorites()
    fun insert(favorite: UserResult) {
        Log.e("detail","inserter")
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: UserResult) {
        Log.e("detail","deleter")
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}