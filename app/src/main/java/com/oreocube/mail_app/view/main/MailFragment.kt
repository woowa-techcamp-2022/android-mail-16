package com.oreocube.mail_app.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oreocube.mail_app.MailAdapter
import com.oreocube.mail_app.databinding.FragmentMailBinding
import com.oreocube.mail_app.model.MailType
import com.oreocube.mail_app.model.getMailList

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding
    private val adapter by lazy { MailAdapter() }
    private val mailType by lazy { arguments?.getSerializable(MAIL_TYPE_KEY) }

    companion object {
        const val MAIL_TYPE_KEY = "MAIL_TYPE_KEY"

        fun newInstance(type: MailType): MailFragment {
            return MailFragment().apply {
                arguments = bundleOf(
                    MAIL_TYPE_KEY to type
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMailBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() = with(binding) {
        titleTextView.text = mailType.toString()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.submitList(
            getMailList().filter { it.type == mailType }
        )
    }
}