package com.oreocube.mail_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oreocube.mail_app.databinding.FragmentMailBinding
import com.oreocube.mail_app.model.getMailList

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding
    private val adapter by lazy { MailAdapter() }

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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.submitList(getMailList())
        return binding.root
    }
}