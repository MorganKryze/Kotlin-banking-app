package fr.kodelab.banking.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.kodelab.banking.R
import fr.kodelab.banking.model.FinancialData

class FinancialDataAdapter(private val financialDataList: List<FinancialData>) : RecyclerView.Adapter<FinancialDataAdapter.FinancialDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_financial_data, parent, false)
        return FinancialDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinancialDataViewHolder, position: Int) {
        val financialData = financialDataList[position]
        holder.bind(financialData)
    }

    override fun getItemCount(): Int {
        return financialDataList.size
    }

    inner class FinancialDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView: TextView = itemView.findViewById(R.id.textViewAmount)
        private val tagTextView: TextView = itemView.findViewById(R.id.textViewTag)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        private val locationTextView: TextView = itemView.findViewById(R.id.textViewLocation)

        fun bind(financialData: FinancialData) {
            amountTextView.text = financialData.amount.toString()
            tagTextView.text = financialData.tag
            dateTextView.text = financialData.date
            locationTextView.text = financialData.location
        }
    }
}
