package com.oreocube.mail_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oreocube.mail_app.databinding.FragmentMailBinding

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding

    companion object {
        fun newInstance(): MailFragment {
            return MailFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMailBinding.inflate(layoutInflater)
        return binding.root
    }
}