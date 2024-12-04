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
import fr.kodelab.banking.db.UserDAO

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDAO: UserDAO

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

        binding.buttonEraseData.setOnClickListener {
            userDAO.deleteAllUsers()
            Toast.makeText(context, "All data deleted. Please reauthenticate.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsFragment_to_authFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
