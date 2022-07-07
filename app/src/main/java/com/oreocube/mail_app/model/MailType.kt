package com.oreocube.mail_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MailType : Parcelable {
    Primary,
    Social,
    Promotions
}