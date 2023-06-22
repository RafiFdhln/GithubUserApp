package com.example.myfundamental.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamental.Adapter.UserAdapter
import com.example.myfundamental.Helper.ViewModelFactory
import com.example.myfundamental.UserResult
import com.example.myfundamental.ViewModel.DetailViewModel
import com.example.myfundamental.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.addItemDecoration(itemDecoration)

        detailViewModel.getAllFavorite().observe(this) { UserResult ->
            val items = arrayListOf<UserResult>()
            UserResult.map {
                val item = UserResult(it.login, it.avatarUrl,it.htmlUrl)
                items.add(item)
            }
            val adapter = UserAdapter(items, onClick = ::onItemClick)
            binding.rvFavorite.adapter = adapter
        }
    }
    private fun onItemClick(item: UserResult) {
        moveDetailActivity(item)
    }
    fun moveDetailActivity(data: UserResult) {
        val bundle = Bundle().apply {
            putParcelable("USER", data)
        }
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
}