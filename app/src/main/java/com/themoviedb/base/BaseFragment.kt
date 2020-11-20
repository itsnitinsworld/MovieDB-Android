package com.themoviedb.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservers()
        onPageRefreshListener()
        initListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    abstract fun onPageRefreshListener(data: Bundle? = null)
    abstract fun initViewModel()
    abstract fun initListeners()
    open fun initObservers() {}



}