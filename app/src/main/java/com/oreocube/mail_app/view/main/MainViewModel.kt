package com.oreocube.mail_app.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oreocube.mail_app.model.MailType

class MainViewModel : ViewModel() {

    val screenStateLiveData = MutableLiveData<ScreenState>(ScreenState.MailScreen)

    val mailTypeLiveData = MutableLiveData(MailType.Primary)

}