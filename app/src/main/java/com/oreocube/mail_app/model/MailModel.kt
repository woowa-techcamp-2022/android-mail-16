package com.oreocube.mail_app.model

import android.graphics.Color
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

    val backgroundColor = colors[(colors.indices).random()]

    companion object {
        val colors = listOf(
            Color.rgb(165, 59, 63),
            Color.rgb(234, 161, 48),
            Color.rgb(40, 114, 123),
            Color.rgb(87, 176, 170),
            Color.rgb(255, 139, 139),
            Color.rgb(136, 99, 72),
            Color.rgb(250, 205, 82),
            Color.rgb(38, 55, 85)
        )
    }
}
