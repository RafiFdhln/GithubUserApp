package com.example.myfundamental.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myfundamental.Adapter.SectionsPagerAdapter
import com.example.myfundamental.DetailUser
import com.example.myfundamental.Helper.ViewModelFactory
import com.example.myfundamental.R
import com.example.myfundamental.UserResult
import com.example.myfundamental.ViewModel.DetailViewModel
import com.example.myfundamental.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class   DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
        const val USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val myData = bundle?.getParcelable<UserResult>("USER")
        val username = myData?.login
        val sectionsPagerAdapter = username?.let { SectionsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.apply {
            title = username
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }

        if (username != null) {

            detailViewModel.detailUser.observe(this) {
                setUserData(it)
            }
            detailViewModel.isLoading.observe(this, {
                showLoading(it)
            })
            detailViewModel.findDetail(username)
            detailViewModel.getFavoriteUserByUsername(username).observe(this, {
                if (it == null){
                    binding.fabFavorite.apply {
                        setOnClickListener {
                            detailViewModel.insert(myData)
                        }
                        setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }else{
                    binding.fabFavorite.apply {
                        setOnClickListener {
                            detailViewModel.delete(myData)
                        }
                        setImageResource(R.drawable.baseline_favorite_24)
                    }
                }
            })
            binding.Share.setOnClickListener {
                val text = "${myData.login}\n" + "${myData.htmlUrl}"

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(user: DetailUser) {
        binding.tvDetailName.text = user.name
        binding.tvLocation.text = user.location
        binding.tvFollowers.text = user.followers.toString()
        binding.tvFollowing.text = user.following.toString()
        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(binding.imgDetail)
    }
}