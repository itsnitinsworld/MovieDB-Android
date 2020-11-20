package com.themoviedb.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.themoviedb.R
import com.themoviedb.ui.fragment.intro.IntroViewPagerFragment
import com.themoviedb.ui.fragment.intro.model.IntroPagerModel

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */

class IntroScreeViewPagerAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!) {


    fun addItems(context: Context?) {
        if (context == null) return
        addFragment(
            IntroPagerModel(
                R.drawable.ic_intro_1,
                context.getString(R.string.lbl_intro_1),
                context.getString(R.string.des_intro_1)
            )
        )
        addFragment(
            IntroPagerModel(
                R.drawable.ic_intro_2,
                context.getString(R.string.lbl_intro_2),
                context.getString(R.string.des_intro_2)
            )
        )
        addFragment(
            IntroPagerModel(
                R.drawable.ic_intro_3,
                context.getString(R.string.lbl_intro_3),
                context.getString(R.string.des_intro_3)
            )
        )
    }

    private var mFragmentList: ArrayList<Fragment> = ArrayList()

    private fun addFragment(introPagerModel: IntroPagerModel?) {
        mFragmentList.add(IntroViewPagerFragment.newInstance(introPagerModel))
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}