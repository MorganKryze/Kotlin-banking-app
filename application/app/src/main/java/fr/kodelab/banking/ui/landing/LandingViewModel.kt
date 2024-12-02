package fr.kodelab.banking.ui.landing

import androidx.lifecycle.LiveData
import fr.kodelab.banking.R
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LandingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to our app! Discover amazing features and start your journey with us."
    }
    val text: LiveData<String> = _text

    // If you need to provide an image resource, you can add a method here
    fun getImageResource(): Int {
        return R.drawable.landing
    }
}
