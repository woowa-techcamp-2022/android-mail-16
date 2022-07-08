package com.oreocube.mail_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oreocube.mail_app.databinding.ViewholderMailBinding
import com.oreocube.mail_app.model.MailModel

class MailAdapter : ListAdapter<MailModel, MailAdapter.ViewHolder>(DiffUtil) {

    class ViewHolder(private val binding: ViewholderMailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mailModel: MailModel) = with(binding) {

            if (mailModel.firstLetter != null) {
                firstLetterTextView.text = mailModel.firstLetter
                firstLetterTextView.setBackgroundResource(0)
                firstLetterTextView.setBackgroundColor(mailModel.backgroundColor)
            } else {
                firstLetterTextView.text = ""
                firstLetterTextView.setBackgroundColor(Color.TRANSPARENT)
                firstLetterTextView.setBackgroundResource(R.drawable.ic_person)
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