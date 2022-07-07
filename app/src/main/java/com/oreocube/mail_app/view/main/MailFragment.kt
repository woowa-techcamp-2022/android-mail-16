package com.oreocube.mail_app.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.oreocube.mail_app.MailAdapter
import com.oreocube.mail_app.databinding.FragmentMailBinding
import com.oreocube.mail_app.model.getMailList

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding
    private val adapter by lazy { MailAdapter() }
    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        const val TAG = "MailFragment"

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeData() {
        viewModel.mailTypeLiveData.observe(viewLifecycleOwner) { mailType ->
            binding.titleTextView.text = mailType.toString()
            adapter.submitList(
                getMailList().filter { it.type == mailType }
            )
        }
    }
}