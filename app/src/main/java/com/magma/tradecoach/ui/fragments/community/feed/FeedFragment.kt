package com.magma.tradecoach.ui.fragments.community.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.magma.tradecoach.R
import com.magma.tradecoach.databinding.FragmentFeedBinding
import com.magma.tradecoach.di.observeUserData
import com.magma.tradecoach.interfaces.BlogPostClickedInterface
import com.magma.tradecoach.model.BlogPostModel
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.Constants
import com.magma.tradecoach.ext.animateAppear
import com.magma.tradecoach.ext.animateFromBottom
import com.magma.tradecoach.ext.animateToBottom
import com.magma.tradecoach.ext.getTextTrimmed
import com.magma.tradecoach.ext.setup
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment:BaseFragment(),BlogPostClickedInterface {
    private lateinit var adapter :FeedAdapter
    private lateinit var binding: FragmentFeedBinding
    private val viewModel: MainViewModel by viewModels()

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
        adapter = FeedAdapter(arrayListOf(),this)
        binding.feedRV.setup(requireContext(),adapter)
        viewModel.getUserData()
        viewModel.getPosts()
        with(viewModel){
            observeUserData(currentUser,::onUserDataLoaded)
        }

        viewModel.blogPostsLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }


        listeners()
    }
    private fun listeners(){
        binding.createPostButton.setOnClickListener {
            viewModel.currentUser.observe(viewLifecycleOwner){
                if (it == null) return@observe

                if (viewModel.isUserPremium(it)){
                    showOverlay(true)
                } else {
                    Toast.makeText(context, "Only Premium Users Can Create Posts", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.publishButton.setOnClickListener {
            viewModel.currentUser.value?.emailAddress?.let { emailAddress ->
                viewModel.createBlogPost(
                    binding.titleTV.getTextTrimmed()
                    , binding.contentTV.getTextTrimmed(),
                    emailAddress
                ).let {

                    binding.createPostView.animateToBottom(500)
                    hideOverlay()
                    Toast.makeText(activity, "Post Created Successfully", Toast.LENGTH_SHORT).show()
                    viewModel.getPosts()
                }
            }
        }

        binding.icClose.setOnClickListener {
            hideOverlay()
        }

    }

    private fun showOverlay(toPost: Boolean) {
        val darkOverlay = activity?.findViewById<View>(R.id.darkOverlay)
        binding.createPostView.animateFromBottom(500)

        if (toPost) {
            binding.titleTV.setText("")
            binding.contentTV.setText("")
        }
        darkOverlay?.animateAppear(500)

    }

    private fun hideOverlay(){
        val darkOverlay = activity?.findViewById<View>(R.id.darkOverlay)
        binding.createPostView.animateToBottom(500)
        darkOverlay?.isVisible = false
        binding.createTitle.text = "Create Post"
        binding.publishButton.isVisible = true
        binding.titleTV.isEnabled = true
        binding.contentTV.isEnabled = true
        binding.titleTV.setText("")
        binding.contentTV.setText("")
    }

    @SuppressLint("SetTextI18n")
    override fun postClicked(post: BlogPostModel) {
        showOverlay(false)
        binding.createTitle.text = "Post"
        binding.publishButton.isVisible = false
        binding.titleTV.setText(post.postTitle)
        binding.contentTV.setText(post.postContent)
        binding.titleTV.isEnabled = false
        binding.contentTV.isEnabled = false
    }
    private fun onUserDataLoaded(user:UserDataModel?){

    }
}