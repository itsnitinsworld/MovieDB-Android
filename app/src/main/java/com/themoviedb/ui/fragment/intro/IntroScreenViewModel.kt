package com.themoviedb.ui.fragment.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel

class IntroScreenViewModel : BaseViewModel() {
    var _currentTab = MutableLiveData<Int>().apply { value = 0 }
    var currentTab:LiveData<Int> = _currentTab

}
