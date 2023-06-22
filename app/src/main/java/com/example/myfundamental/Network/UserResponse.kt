package com.example.myfundamental

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(
	@field:SerializedName("items")
	val items: List<UserResult>,
)
@Entity(tableName = "Favorite")
@Parcelize
data class UserResult(
	@PrimaryKey(autoGenerate = false)
	@field:ColumnInfo(name = "username")
	@field:SerializedName("login")
	val login: String,

	@field:ColumnInfo(name = "profile")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:ColumnInfo(name = "url")
	@field:SerializedName("html_url")
	val htmlUrl: String,
):Parcelable

data class DetailUser(
	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("login")
	val login: String
)
