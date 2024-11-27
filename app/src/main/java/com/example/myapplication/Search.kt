package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivitySearchBinding

class Search : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: Search_Rv_Adapter // Use Search_Rv_Adapter
    private lateinit var data: ArrayList<Modal>
    private lateinit var filteredData: ArrayList<Modal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from Intent
        data = intent.getSerializableExtra("data") as? ArrayList<Modal> ?: ArrayList()
        filteredData = ArrayList(data)

        // Set up RecyclerView
        searchAdapter = Search_Rv_Adapter(this, filteredData)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = searchAdapter

        // Toggle visibility of RecyclerView and "No Data" message
        toggleVisibility(filteredData.isEmpty())

        // Implement search functionality
        binding.searchEditText.addTextChangedListener { text ->
            filterData(text.toString())
        }
    }

    private fun toggleVisibility(isEmpty: Boolean) {
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        binding.noDataText.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    // Filter data based on the search query
    private fun filterData(query: String) {
        filteredData.clear()

        if (query.isNotEmpty()) {
            val lowerCaseQuery = query.lowercase()
            for (item in data) {
                if (item.name.lowercase().contains(lowerCaseQuery) ||
                    item.symbol.lowercase().contains(lowerCaseQuery)) {
                    filteredData.add(item)
                }
            }
        } else {
            // If query is empty, show all data
            filteredData.addAll(data)
        }

        // Update the adapter and toggle visibility
        searchAdapter.changeData(filteredData)
        toggleVisibility(filteredData.isEmpty())
    }
}
