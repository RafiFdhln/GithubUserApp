package com.example.myfundamental.UI

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamental.Adapter.UserAdapter
import com.example.myfundamental.Helper.ViewModelFactory
import com.example.myfundamental.UserResult
import com.example.myfundamental.ViewModel.DetailViewModel
import com.example.myfundamental.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(Application())
    }
    private var position: Int = 0
    private var username: String = ""
    companion object {
        const val ARG_POSITION = "extra_position"
        const val ARG_USERNAME = "extra_username"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
        viewModel.getUser(username)
        if (position == 0){
            viewModel.listFollowers.observe(viewLifecycleOwner) {
                getFollow(it)
            }
        } else {
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                getFollow(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

   private fun getFollow(userResult: List<UserResult>) {
       val adapter = UserAdapter(userResult){
           moveDetailActivity(it)
       }
       binding.rvFollowing.adapter = adapter
   }

    fun moveDetailActivity(data: UserResult) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.USERNAME, data.login)
        startActivity(intent)
    }

}