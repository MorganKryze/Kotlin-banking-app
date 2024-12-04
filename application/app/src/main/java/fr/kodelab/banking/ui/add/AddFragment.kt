package fr.kodelab.banking.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import fr.kodelab.banking.R
import fr.kodelab.banking.databinding.FragmentAddBinding
import fr.kodelab.banking.db.FinancialDataDAO
import fr.kodelab.banking.model.FinancialData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var financialDataDAO: FinancialDataDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        financialDataDAO = FinancialDataDAO(requireContext())

        // Set up the spinner for tags
        val tags = listOf("Groceries", "Utilities", "Transport", "Entertainment", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tags)
        binding.spinnerTag.adapter = adapter

        binding.buttonAddData.setOnClickListener {
            try {
                val amountText = binding.editTextAmount.text.toString()
                val tag = binding.spinnerTag.selectedItem.toString()
                val dateText = binding.editTextDate.text.toString()
                val locationText = binding.editTextLocation.text.toString()

                if (validateInput(amountText, tag, dateText, locationText)) {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    val date = parseDate(dateText)
                    val financialData = FinancialData(
                        id = 0,
                        userId = 1, // Assuming userId is 1 for the current user
                        amount = amount,
                        tag = tag,
                        date = date?.time.toString(),
                        location = locationText
                    )
                    financialDataDAO.addFinancialData(financialData)
                    Toast.makeText(context, "Financial data added successfully", Toast.LENGTH_SHORT).show()
                    // Clear the form fields
                    clearFormFields()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to add financial data: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun validateInput(amount: String, tag: String, date: String, location: String): Boolean {
        if (amount.isEmpty() || tag.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(dateString)
    }

    private fun clearFormFields() {
        binding.editTextAmount.text.clear()
        binding.spinnerTag.setSelection(0)
        binding.editTextDate.text.clear()
        binding.editTextLocation.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
