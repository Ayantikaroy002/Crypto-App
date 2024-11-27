package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var data: ArrayList<Modal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Initialize the data list and RecyclerView adapter
        data = ArrayList()
        rvAdapter = RvAdapter(this, data)
        binding.Rv.layoutManager = GridLayoutManager(this, 2)
        binding.Rv.adapter = rvAdapter

        // Fetch data from API
        fetchApiData()

        // Pass data to the Search activity on button click
        binding.searchLayout.setOnClickListener {
            if (data.isEmpty()) {
                Toast.makeText(this, "No data available to search!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Search::class.java)
                intent.putExtra("data", data) // Pass data list as Serializable
                startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchApiData() {
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            try {
                val dataArray = response.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val dataObject = dataArray.getJSONObject(i)
                    val symbol = dataObject.getString("symbol")
                    val name = dataObject.getString("name")
                    val quote = dataObject.getJSONObject("quote")
                    val USD = quote.getJSONObject("USD")
                    val price = String.format("$ %.2f", USD.getDouble("price"))

                    data.add(Modal(name, symbol, price))
                }
                rvAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("MainActivity", "Error parsing API data", e)
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            Log.e("MainActivity", "API error", error)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-CMC_PRO_API_KEY"] = "6ef6a5e2-4186-49bd-89be-88f628a3bd1f"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}
