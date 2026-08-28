package com.example.accountingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accountingapp.data.AppDatabase
import com.example.accountingapp.data.TransactionDao
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactionDao: TransactionDao
    private lateinit var transactionAdapter: TransactionAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        transactionDao = AppDatabase.getDatabase(this).transactionDao()
        
        setupRecyclerView()
        observeTransactions()
    }
    
    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        transactionAdapter = TransactionAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }
    }
    
    private fun observeTransactions() {
        lifecycleScope.launch {
            transactionDao.getAllTransactions().collect { transactions ->
                transactionAdapter.submitList(transactions)
                updateBalance()
            }
        }
    }
    
    private fun updateBalance() {
        lifecycleScope.launch {
            val income = transactionDao.getTotalIncome().collect { it ?: 0.0 }
            val expenses = transactionDao.getTotalExpenses().collect { it ?: 0.0 }
            val balance = income - expenses
            // تحديث واجهة المستخدم
        }
    }
}
