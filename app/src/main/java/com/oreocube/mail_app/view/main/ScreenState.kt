package com.oreocube.mail_app.view.main

sealed class ScreenState {
    object MailScreen : ScreenState()
    object SettingScreen : ScreenState()
}