package com.themoviedb.ui.fragment.intro

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel

class IntroScreenViewModel @ViewModelInject constructor() : BaseViewModel() {
    var currentTab = MutableLiveData<Int>().apply { value = 0 }

}
