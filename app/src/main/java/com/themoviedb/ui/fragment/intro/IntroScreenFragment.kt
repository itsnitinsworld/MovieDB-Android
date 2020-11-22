package com.themoviedb.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.themoviedb.R
import com.themoviedb.adapter.IntroScreeViewPagerAdapter
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.IntroScreenFragmentBinding
import com.themoviedb.ui.MainActivity
import com.themoviedb.utils.AppConstants
import com.themoviedb.utils.PreferenceUtils
import com.themoviedb.utils.extensions.debounce
import com.themoviedb.utils.extensions.makeVisibleWithAnimation
import com.themoviedb.utils.view.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroScreenFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: IntroScreenViewModel by viewModels()
    private lateinit var binding: IntroScreenFragmentBinding
    private lateinit var pagerAdapter: IntroScreeViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.intro_screen_fragment, container, false)
        return binding.root
    }


    override fun initObservers() {
        super.initObservers()

        viewModel.currentTab.debounce(100).observe(viewLifecycleOwner, Observer {
            if (it + 1 == pagerAdapter.itemCount) {
                binding.ivLeft.makeVisibleWithAnimation(isVisible = false)
                binding.ivRight.makeVisibleWithAnimation(isVisible = false)
                binding.btnGetStarted.makeVisibleWithAnimation(isVisible = it + 1 == pagerAdapter.itemCount)
                return@Observer
            }

            binding.ivRight.makeVisibleWithAnimation(isVisible = it + 1 != pagerAdapter.itemCount)
            binding.ivLeft.makeVisibleWithAnimation(isVisible = it != 0)
            binding.btnGetStarted.makeVisibleWithAnimation(isVisible = false)
        })

    }


    override fun onPageRefreshListener(data: Bundle?) {
        (mContext as MainActivity).setToolbar()
    }

    override fun initListeners() {
        pagerAdapter =
            IntroScreeViewPagerAdapter(requireActivity())
        pagerAdapter.addItems(mContext)

        binding.ivLeft.setOnClickListener(this)
        binding.ivRight.setOnClickListener(this)
        binding.btnGetStarted.setOnClickListener(this)


        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentTab.value = position
            }
        })

        // Set an Adapter on the ViewPager
        binding.viewPager.adapter = pagerAdapter
    }

    override fun onClick(p0: View?) {
        when (p0) {
            null -> return
            binding.ivLeft -> {
                if (binding.viewPager.currentItem - 1 >= 0) {
                    val position = binding.viewPager.currentItem - 1
                    binding.viewPager.setCurrentItem(
                        position,
                        true
                    )
                    viewModel.currentTab.value = position
                }
            }

            binding.ivRight -> {
                var position = 0
                if (binding.viewPager.currentItem + 1 < pagerAdapter.itemCount) {
                    position =
                        binding.viewPager.currentItem + 1
                    binding.viewPager.currentItem = position
                } else { // end presentation
                    moveToMain()
                }

                viewModel.currentTab.value = position
            }

            binding.btnGetStarted -> {
                moveToMain()
            }

        }

    }

    private fun moveToMain() {
        PreferenceUtils.savePref(AppConstants.Preference.IS_FIRST_TIME_USER, false)
        findNavController().navigate(IntroScreenFragmentDirections.actionToMovieList())
    }


}
