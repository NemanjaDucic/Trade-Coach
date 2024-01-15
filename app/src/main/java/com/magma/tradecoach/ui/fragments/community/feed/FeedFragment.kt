package com.magma.tradecoach.ui.fragments.community.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magma.tradecoach.databinding.FragmentFeedBinding
import com.magma.tradecoach.model.TopicModel
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment:Fragment() {
    private lateinit var adapter :FeedAdapter
    private lateinit var binding: FragmentFeedBinding
    private val viewModel: MainViewModel by viewModels()

    private val holderArray = arrayListOf<TopicModel>(TopicModel("title","longer subtitle"),TopicModel("title","longer subtitle"),TopicModel("title","longer subtitle"))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)

        init()
        return binding.root
    }

    private fun init() {
        adapter = FeedAdapter(holderArray)
        Utils.setRecycler(binding.feedRV,adapter)
        viewModel.getUserData()
        listeners()
    }
    private fun listeners(){
        binding.createPostButton.setOnClickListener {
            viewModel.currentUser.observe(this){
                if (Utils.isPremiumUser(it!!)){
                    Toast.makeText(context, "Your message", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Only Premium Users Can Create Posts", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}