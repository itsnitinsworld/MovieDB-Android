package com.themoviedb.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.themoviedb.R
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.ActivityMainBinding
import com.themoviedb.utils.AppConstants
import com.themoviedb.utils.PreferenceUtils
import dagger.android.AndroidInjection


/**
 * @author Nitin Khanna
 * @date 19-11-2020
 */

class MainActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // initializing navigation menu
        setupNavigationController()
    }

    private fun setupNavigationController() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.frame) as NavHostFragment? ?: return


        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_main)


        graph.startDestination =
            if (PreferenceUtils.getPref<Boolean>(AppConstants.Preference.IS_FIRST_TIME_USER) == false
            ) R.id.nav_movie_list
            else R.id.nav_intro

        navHostFragment.navController.graph = graph
    }


    override fun onBackPressed() {
        when (navHostFragment.navController.graph.startDestination) {
            navHostFragment.navController.currentDestination?.id -> showExitConfirmation()
            else -> {
                super.onBackPressed()
                val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()
                if (currentFragment is BaseFragment) currentFragment.onPageRefreshListener()
            }
        }
    }

    private fun showExitConfirmation() {
        if (doubleBackToExitPressedOnce) {
            clearBackStack(this)
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


    private fun clearBackStack(appCompatActivity: AppCompatActivity) {
        val manager = appCompatActivity.supportFragmentManager
        if (manager.backStackEntryCount > 1) {
            val first = manager.getBackStackEntryAt(1)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setToolbar(toolbar: Toolbar? = null) {
        if (toolbar == null) {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
            setSupportActionBar(toolbar)
        }
    }

}
