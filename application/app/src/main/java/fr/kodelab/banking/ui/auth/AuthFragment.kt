package fr.kodelab.banking.ui.auth

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.kodelab.banking.R
import fr.kodelab.banking.databinding.FragmentAuthBinding
import fr.kodelab.banking.db.UserDAO
import fr.kodelab.banking.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDAO: UserDAO
    private var isAccountCreated: Boolean = false
    private var user: User? = null
    private var isSignOut: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(requireContext())

        // Check if an account already exists
        user = userDAO.getAllUsers().firstOrNull()
        if (user != null) {
            isAccountCreated = true
            val welcomeText = "Welcome back, "
            val usernameText = user!!.name
            val spannable = SpannableString("$welcomeText$usernameText")
            spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), welcomeText.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.textViewWelcome.text = spannable
            binding.textViewWelcome.textSize = 30f // Larger than 24sp
            binding.textViewWelcome.visibility = View.VISIBLE
            binding.editTextUsername.visibility = View.GONE
            binding.buttonCreateAccount.visibility = View.GONE
            binding.buttonLogin.visibility = View.VISIBLE
            binding.buttonLogin.text = if (isSignOut) "Confirm Sign Out" else "Confirm"
        } else {
            binding.textViewWelcome.visibility = View.GONE
            binding.editTextUsername.visibility = View.VISIBLE
            binding.buttonCreateAccount.visibility = View.VISIBLE
            binding.buttonLogin.visibility = View.GONE
        }

        binding.buttonCreateAccount.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val pin = binding.editTextPin.text.toString()

            if (validateUsername(username) && validateInput(pin)) {
                val newUser = User(0, username, pin, null, getCurrentDateTime())
                userDAO.addUser(newUser)
                Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                // Navigate to the home page or perform any other action
                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
            }
        }

        binding.buttonLogin.setOnClickListener {
            val pin = binding.editTextPin.text.toString()
            if (validateInput(pin)) {
                if (validatePin(pin)) {
                    if (isSignOut) {
                        userDAO.updateLastLogin(user!!.id)
                        findNavController().navigate(R.id.action_authFragment_to_landingFragment)
                    } else {
                        userDAO.updateLastLogin(user!!.id)
                        // Navigate to the home page
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    private fun validateUsername(username: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateInput(pin: String): Boolean {
        if (pin.isEmpty() || pin.length != 6) {
            Toast.makeText(context, "PIN must be 6 digits", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validatePin(pin: String): Boolean {
        if (user != null && user!!.pin == pin) {
            return true
        } else {
            Toast.makeText(context, "Wrong PIN", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
