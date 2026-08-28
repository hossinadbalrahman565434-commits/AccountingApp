package com.example.accountingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val category: String,
    val date: Date,
    val type: TransactionType
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
