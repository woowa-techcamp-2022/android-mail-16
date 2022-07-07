package com.oreocube.mail_app.model

import java.util.regex.Pattern

data class MailModel(
    val id: Int,
    val sender: String,
    val title: String,
    val content: String,
    val type: MailType
) {
    val firstLetter: String? =
        if (Pattern.matches("^[a-zA-Z].*", sender)) sender.first().uppercase()
        else null

}
