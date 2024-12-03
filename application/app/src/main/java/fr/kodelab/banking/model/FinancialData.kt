package fr.kodelab.banking.model

data class FinancialData(
    val id: Long,
    val userId: Long,
    val amount: Double,
    val tag: String,
    val date: String,
    val location: String
)
