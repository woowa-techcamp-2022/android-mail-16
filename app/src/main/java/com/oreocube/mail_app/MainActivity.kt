package com.oreocube.mail_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.oreocube.mail_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, MailFragment.newInstance())
        }
    }

    private fun initViews() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_primary -> {
                    Toast.makeText(this@MainActivity, "primary clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_social -> {
                    Toast.makeText(this@MainActivity, "social clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_promotions -> {
                    Toast.makeText(this@MainActivity, "promotions clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_mail -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, MailFragment.newInstance())
                    }
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragmentContainer, SettingFragment.newInstance())
                    }
                    true
                }
                else -> false
            }
        }
    }
}