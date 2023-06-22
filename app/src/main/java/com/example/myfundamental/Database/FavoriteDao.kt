package com.example.myfundamental.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myfundamental.UserResult

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: UserResult)

    @Delete
    fun delete(favorite: UserResult)

    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UserResult>

    @Query("SELECT * from Favorite")
    fun getAllFavorites(): LiveData<List<UserResult>>
}