package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemModalBinding

class Search_Rv_Adapter(
    private val context: Context,
    private var data: ArrayList<Modal>
) : RecyclerView.Adapter<Search_Rv_Adapter.ViewHolder>() {

    // Update data dynamically
    fun changeData(filterData: ArrayList<Modal>) {
        data = filterData
        notifyDataSetChanged()
    }

    // ViewHolder class using ViewBinding
    inner class ViewHolder(val binding: ItemModalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout using ViewBinding for item_modal.xml
        val binding = ItemModalBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Animation for item appearance
        setAnimation(holder.itemView)

        // Bind data to views
        val modal = data[position]
        holder.binding.name.text = modal.name
        holder.binding.symbol.text = modal.symbol
        holder.binding.price.text = modal.price
    }

    // Animation method for smooth fade-in
    private fun setAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        view.startAnimation(anim)
    }
}
