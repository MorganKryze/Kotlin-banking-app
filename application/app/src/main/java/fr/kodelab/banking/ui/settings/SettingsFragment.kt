package fr.kodelab.banking.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.kodelab.banking.R
import fr.kodelab.banking.databinding.FragmentSettingsBinding
import fr.kodelab.banking.db.FinancialDataDAO
import fr.kodelab.banking.db.UserDAO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import fr.kodelab.banking.model.FinancialData

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDAO: UserDAO
    private lateinit var financialDataDAO: FinancialDataDAO
    private var isDeveloperMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(requireContext())
        financialDataDAO = FinancialDataDAO(requireContext())

        // Get the user and set the user information
        val user = userDAO.getAllUsers().firstOrNull()
        if (user != null) {
            binding.textViewUsername.text = user.name
            binding.textViewCreationDate.text = "Created on: ${formatDate(user.creationDate)}"
            binding.textViewLastLogin.text = "Last login: ${formatDate(user.lastLogin)}"
        }

        binding.buttonEraseData.setOnClickListener {
            try {
                financialDataDAO.deleteAllFinancialData()
                userDAO.deleteAllUsers()
                Toast.makeText(context, "All data deleted. Please reauthenticate.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_settingsFragment_to_authFragment)
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to delete data: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        binding.switchDeveloperMode.setOnCheckedChangeListener { _, isChecked ->
            isDeveloperMode = isChecked
            binding.developerModeLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.buttonAddFinancialEntry.setOnClickListener {
            val financialData = FinancialData(
                id = 0,
                userId = 1, // Assuming userId is 1 for the current user
                amount = 100.0,
                tag = "Hardcoded Entry",
                date = Date().time.toString(),
                location = "Hardcoded Location"
            )
            financialDataDAO.addFinancialData(financialData)
            Toast.makeText(context, "Financial data added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatDate(dateString: String?): String {
        if (dateString == null) return "N/A"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.getDefault()).format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
