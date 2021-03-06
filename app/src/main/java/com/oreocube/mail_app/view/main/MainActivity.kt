package com.oreocube.mail_app.view.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.oreocube.mail_app.R
import com.oreocube.mail_app.databinding.ActivityMainBinding
import com.oreocube.mail_app.model.MailType

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val userNickname by lazy { intent.getStringExtra(EXTRA_NICKNAME) }
    private val userEmail by lazy { intent.getStringExtra(EXTRA_EMAIL) }

    private val mailFragment = MailFragment.newInstance()
    private val settingFragment by lazy {
        SettingFragment.newInstance().apply {
            arguments = bundleOf(
                EXTRA_NICKNAME to userNickname,
                EXTRA_EMAIL to userEmail
            )
        }
    }

    companion object {
        const val EXTRA_NICKNAME = "NICKNAME"
        const val EXTRA_EMAIL = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkScreenWidth()
        initViews()

        viewModel.screenStateLiveData.observe(this) {
            when (it) {
                is ScreenState.MailScreen -> showFragment(mailFragment)
                is ScreenState.SettingScreen -> showFragment(settingFragment)
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun checkScreenWidth() {
        val density = resources.displayMetrics.density
        val width = resources.displayMetrics.widthPixels
        val dpWidth = width / density

        // ????????? 600dp ??????????????? ????????? ?????? ???????????? RailView ??????
        // ORIENTATION_LANDSCAPE : ?????? ??????, ORIENTATION_PORTRAIT : ?????? ??????
        if (dpWidth > 600 || resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.bottomNavigationView.isGone = true
            binding.navigationRailView.isVisible = true
        } else {
            binding.navigationRailView.isGone = true
            binding.bottomNavigationView.isVisible = true
        }
    }

    private fun initViews() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationRailView.setOnItemSelectedListener(this@MainActivity)
        bottomNavigationView.setOnItemSelectedListener(this@MainActivity)
        initNavigationView()
    }

    private fun initNavigationView() = with(binding) {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_primary -> {
                    setSelectedMailType(MailType.Primary)
                }
                R.id.nav_social -> {
                    setSelectedMailType(MailType.Social)
                }
                R.id.nav_promotions -> {
                    setSelectedMailType(MailType.Promotions)
                }
            }
            setScreenState(ScreenState.MailScreen)
            setNavigationSelectedItem(R.id.nav_mail)
            drawerLayout.closeDrawers()
            true
        }
    }

    // BottomNavigationView, RailView ????????? ??????
    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.nav_mail -> {
                setScreenState(ScreenState.MailScreen)
                true
            }
            R.id.nav_settings -> {
                setScreenState(ScreenState.SettingScreen)
                true
            }
            else -> false
        }

    // ??????????????? ????????? ???
    override fun onBackPressed() {
        // drawer ??????????????? ??????
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            when (viewModel.screenStateLiveData.value) {
                is ScreenState.MailScreen -> {
                    // ?????? ????????? ????????? Primary Mail ???????????? ??????
                    if (viewModel.mailTypeLiveData.value == MailType.Primary) {
                        super.onBackPressed()
                    }
                    // Social, Promotions Mail ???????????? Primary ????????????
                    else {
                        setSelectedMailType(MailType.Primary)
                    }
                }
                is ScreenState.SettingScreen -> {
                    setNavigationSelectedItem(R.id.nav_mail)
                    setSelectedMailType(MailType.Primary)
                }
                else -> {}
            }
        }
    }

    private fun setNavigationSelectedItem(id: Int) {
        binding.bottomNavigationView.selectedItemId = id
        binding.navigationRailView.selectedItemId = id
    }

    private fun setSelectedMailType(type: MailType) {
        viewModel.mailTypeLiveData.value = type
    }

    private fun setScreenState(state: ScreenState) {
        viewModel.screenStateLiveData.value = state
    }
}