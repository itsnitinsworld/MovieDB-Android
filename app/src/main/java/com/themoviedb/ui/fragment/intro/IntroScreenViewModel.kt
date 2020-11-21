package com.themoviedb.ui.fragment.intro

import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel
import javax.inject.Inject

class IntroScreenViewModel @Inject constructor() : BaseViewModel() {
    var currentTab = MutableLiveData<Int>().apply { value = 0 }

}
