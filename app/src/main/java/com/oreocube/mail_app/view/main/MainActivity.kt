package com.oreocube.mail_app.view.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
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

    private var currentScreenState = R.id.nav_primary

    private val userNickname by lazy { intent.getStringExtra(EXTRA_NICKNAME) }
    private val userEmail by lazy { intent.getStringExtra(EXTRA_EMAIL) }

    private val primaryMailFragment = MailFragment.newInstance(MailType.Primary)
    private val socialMailFragment = MailFragment.newInstance(MailType.Social)
    private val promotionMailFragment = MailFragment.newInstance(MailType.Promotions)
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

        const val SCREEN_STATE_KEY = "SCREEN_STATE"
        const val TAG_PRIMARY = "PRIMARY"
        const val TAG_SOCIAL = "SOCIAL"
        const val TAG_PROMOTIONS = "PROMOTIONS"
        const val TAG_SETTINGS = "SETTINGS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkScreenWidth()
        initViews()

        showFragment(primaryMailFragment, TAG_PRIMARY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCREEN_STATE_KEY, currentScreenState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        when (val id = savedInstanceState.getInt(SCREEN_STATE_KEY)) {
            R.id.nav_settings -> {
                setNavigationSelectedItem(id)
            }
            R.id.nav_social -> {
                showFragment(socialMailFragment, TAG_SOCIAL)
            }
            R.id.nav_promotions -> {
                showFragment(promotionMailFragment, TAG_PROMOTIONS)
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun checkScreenWidth() {
        val density = resources.displayMetrics.density
        val width = resources.displayMetrics.widthPixels
        val dpWidth = width / density

        // 가로가 600dp 이상이거나 화면이 가로 모드이면 RailView 사용
        // ORIENTATION_LANDSCAPE : 가로 모드, ORIENTATION_PORTRAIT : 세로 모드
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
            if (currentScreenState == R.id.nav_settings) {
                setNavigationSelectedItem(R.id.nav_mail)
            }
            when (item.itemId) {
                R.id.nav_primary -> {
                    currentScreenState = R.id.nav_primary
                    showFragment(primaryMailFragment, TAG_PRIMARY)
                }
                R.id.nav_social -> {
                    currentScreenState = R.id.nav_social
                    showFragment(socialMailFragment, TAG_SOCIAL)
                }
                R.id.nav_promotions -> {
                    currentScreenState = R.id.nav_promotions
                    showFragment(promotionMailFragment, TAG_PROMOTIONS)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // BottomNavigationView, RailView 이벤트 처리
    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.nav_mail -> {
                currentScreenState = R.id.nav_primary
                showFragment(primaryMailFragment, TAG_PRIMARY)
                true
            }
            R.id.nav_settings -> {
                currentScreenState = R.id.nav_settings
                showFragment(settingFragment, TAG_SETTINGS)
                true
            }
            else -> false
        }

    // 뒤로가기를 눌렀을 때
    override fun onBackPressed() {
        // drawer 열려있으면 닫기
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            if (currentScreenState != R.id.nav_primary) {
                currentScreenState = R.id.nav_primary
                setNavigationSelectedItem(R.id.nav_mail)
                binding.navigationView.setCheckedItem(R.id.nav_primary)
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun setNavigationSelectedItem(id: Int) {
        binding.bottomNavigationView.selectedItemId = id
        binding.navigationRailView.selectedItemId = id
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        // 프래그먼트가 교체되기 전에 모든 프래그먼트 숨기기
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        findFragment?.let {
            // 해당 프래그먼트가 있으면 보여주기
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: run {
            // 없으면 새 프래그먼트 추가
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss() // 손실 허용
        }
    }

}