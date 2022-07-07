package com.oreocube.mail_app.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oreocube.mail_app.R
import com.oreocube.mail_app.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val userNickname by lazy { arguments?.getString(MainActivity.EXTRA_NICKNAME) }
    private val userEmail by lazy { arguments?.getString(MainActivity.EXTRA_EMAIL) }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        binding.infoTextView.text = getString(R.string.user_info_text, userNickname, userEmail)
        return binding.root
    }
}