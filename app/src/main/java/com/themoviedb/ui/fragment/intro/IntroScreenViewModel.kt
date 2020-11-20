package com.themoviedb.ui.fragment.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel

class IntroScreenViewModel : BaseViewModel() {
    var currentTab = MutableLiveData<Int>().apply { value = 0 }

}
