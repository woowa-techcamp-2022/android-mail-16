package com.oreocube.mail_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oreocube.mail_app.databinding.ViewholderMailBinding
import com.oreocube.mail_app.model.MailModel
import java.util.regex.Pattern

class MailAdapter : ListAdapter<MailModel, MailAdapter.ViewHolder>(DiffUtil) {

    class ViewHolder(private val binding: ViewholderMailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mailModel: MailModel) = with(binding) {

            if (Pattern.matches("^[a-zA-Z].*", mailModel.sender)) {
                firstLetterTextView.text = mailModel.sender.first().uppercase()
                firstLetterTextView.isVisible = true
            } else {
                firstLetterTextView.isVisible = false
            }

            senderTextView.text = mailModel.sender
            titleTextView.text = mailModel.title
            contentTextView.text = mailModel.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderMailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<MailModel>() {
            override fun areItemsTheSame(oldItem: MailModel, newItem: MailModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MailModel, newItem: MailModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}