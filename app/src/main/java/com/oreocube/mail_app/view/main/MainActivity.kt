package com.oreocube.mail_app.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.oreocube.mail_app.R
import com.oreocube.mail_app.databinding.ActivityMainBinding
import com.oreocube.mail_app.model.MailType

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userNickname by lazy { intent.getStringExtra(EXTRA_NICKNAME) }
    private val userEmail by lazy { intent.getStringExtra(EXTRA_EMAIL) }

    companion object {
        const val EXTRA_NICKNAME = "NICKNAME"
        const val EXTRA_EMAIL = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, MailFragment.newInstance(MailType.PRIMARY))
        }
    }

    private fun initViews() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_primary -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, MailFragment.newInstance(MailType.PRIMARY))
                    }
                }
                R.id.nav_social -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, MailFragment.newInstance(MailType.SOCIAL))
                    }
                }
                R.id.nav_promotions -> {
                    supportFragmentManager.commit {
                        replace(
                            R.id.fragmentContainer,
                            MailFragment.newInstance(MailType.PROMOTIONS)
                        )
                    }
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_mail -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, MailFragment.newInstance(MailType.PRIMARY))
                    }
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, SettingFragment.newInstance().apply {
                            arguments = bundleOf(
                                EXTRA_NICKNAME to userNickname,
                                EXTRA_EMAIL to userEmail
                            )
                        })
                    }
                    true
                }
                else -> false
            }
        }
    }
}