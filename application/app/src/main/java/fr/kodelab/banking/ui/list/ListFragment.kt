package fr.kodelab.banking.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.kodelab.banking.R
import fr.kodelab.banking.databinding.FragmentListBinding
import fr.kodelab.banking.db.FinancialDataDAO
import fr.kodelab.banking.model.FinancialData

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var financialDataDAO: FinancialDataDAO
    private lateinit var financialDataList: List<FinancialData>
    private lateinit var adapter: FinancialDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        financialDataDAO = FinancialDataDAO(requireContext())
        financialDataList = financialDataDAO.getFinancialDataByUserId(1) // Assuming userId is 1 for the current user

        adapter = FinancialDataAdapter(financialDataList)
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
