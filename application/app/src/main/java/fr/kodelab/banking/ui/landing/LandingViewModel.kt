package fr.kodelab.banking.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LandingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the Landing Fragment"
    }
    val text: LiveData<String> = _text
}
