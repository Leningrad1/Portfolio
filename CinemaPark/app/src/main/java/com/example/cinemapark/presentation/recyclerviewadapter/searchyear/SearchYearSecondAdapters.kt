package com.example.cinemapark.presentation.recyclerviewadapter.searchyear

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.cinemapark.R
import com.example.cinemapark.databinding.ItemCountryBinding
import com.example.cinemapark.databinding.ItemYearBinding
import com.example.cinemapark.dataclass.genrecountry.Country
import com.example.cinemapark.dataclass.genrecountry.Genre

class SearchYearSecondAdapters(
    private var onClick: (item: String?) -> Unit,
    private val listIntYear: List<String>
) : Adapter<SearchYearSecondAdapters.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemYearBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listIntYear[position]
        with(holder.binding) {
            item?.let {
                textYear.text = "$item"
                Log.d("repoItemNameCollect", "${item}")
                root.setOnClickListener {
                    onClick(item)
                    Log.d("repoItemClick", "${item}")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_year, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = listIntYear.size
}