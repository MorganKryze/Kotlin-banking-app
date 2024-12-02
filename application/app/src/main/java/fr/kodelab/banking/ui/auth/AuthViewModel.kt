package fr.kodelab.banking.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the Authentication Fragment"
    }
    val text: LiveData<String> = _text
}
