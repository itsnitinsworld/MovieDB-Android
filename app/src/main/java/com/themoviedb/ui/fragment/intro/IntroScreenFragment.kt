package com.themoviedb.ui.fragment.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.themoviedb.R
import com.themoviedb.adapter.IntroScreeViewPagerAdapter
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.IntroScreenFragmentBinding
import com.themoviedb.ui.MainActivity
import com.themoviedb.utils.AppConstants
import com.themoviedb.utils.PreferenceUtils
import com.themoviedb.utils.extensions.debounce
import com.themoviedb.utils.extensions.makeVisibleWithAnimation
import javax.inject.Inject

class IntroScreenFragment : BaseFragment(), View.OnClickListener {

    @Inject
    lateinit var viewModel: IntroScreenViewModel
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
            if (it + 1 == pagerAdapter.count) {
                binding.ivLeft.makeVisibleWithAnimation(isVisible = false)
                binding.ivRight.makeVisibleWithAnimation(isVisible = false)
                binding.btnGetStarted.makeVisibleWithAnimation(isVisible = it + 1 == pagerAdapter.count)
                return@Observer
            }

            binding.ivRight.makeVisibleWithAnimation(isVisible = it + 1 != pagerAdapter.count)
            binding.ivLeft.makeVisibleWithAnimation(isVisible = it != 0)
            binding.btnGetStarted.makeVisibleWithAnimation(isVisible = false)
        })

    }


    override fun onPageRefreshListener(data: Bundle?) {
        (mContext as MainActivity).setToolbar()
    }

    override fun initListeners() {
        pagerAdapter =
            IntroScreeViewPagerAdapter((mContext as MainActivity).supportFragmentManager)
        pagerAdapter.addItems(mContext)

        binding.ivLeft.setOnClickListener(this)
        binding.ivRight.setOnClickListener(this)
        binding.btnGetStarted.setOnClickListener(this)


        binding.viewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewModel.currentTab.value = position

            }

            override fun onPageSelected(position: Int) {
                //
            }

            override fun onPageScrollStateChanged(state: Int) {
                //no use
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
                if (binding.viewPager.currentItem + 1 < pagerAdapter.count) {
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
