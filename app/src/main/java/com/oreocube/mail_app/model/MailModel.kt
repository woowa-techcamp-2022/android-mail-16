package com.oreocube.mail_app.model

data class MailModel(
    val id: Int,
    val sender: String,
    val title: String,
    val content: String,
    val type: MailType
)
