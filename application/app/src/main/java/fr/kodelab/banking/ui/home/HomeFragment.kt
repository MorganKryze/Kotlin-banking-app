package fr.kodelab.banking.ui.home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.kodelab.banking.databinding.FragmentHomeBinding
import fr.kodelab.banking.db.UserDAO

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(requireContext())

        // Get the user and set the welcome message
        val user = userDAO.getAllUsers().firstOrNull()
        if (user != null) {
            val welcomeText = "Welcome back, "
            val usernameText = user.name
            val spannable = SpannableString("$welcomeText$usernameText")
            spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), welcomeText.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.textViewWelcome.text = spannable
        }

        // Add any additional setup for the placeholder square if needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
