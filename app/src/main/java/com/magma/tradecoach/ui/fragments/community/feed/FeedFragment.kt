package com.magma.tradecoach.ui.fragments.community.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentFeedBinding
import com.magma.tradecoach.model.TopicModel
import com.magma.tradecoach.utilities.Utils

class FeedFragment:Fragment() {
    private lateinit var adapter :FeedAdapter
    private lateinit var binding: FragmentFeedBinding
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
    }

}