package com.example.cinemapark.presentation.recyclerviewadapter.genrecountry

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemapark.R
import com.example.cinemapark.databinding.ItemCountryBinding
import com.example.cinemapark.dataclass.genrecountry.Country

class GenreCountryAdapters(private var onClick: (item: Int?) -> Unit) :
    PagingDataAdapter<Country, GenreCountryAdapters.ItemViewHolder>(DiffUtilCallbackGenreCountry()) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCountryBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                textSearchCountry.text = "${item.country}"
                Log.d("repoItemNameCollect", "${item.country}")
                root.setOnClickListener {
                    onClick(item.id)
                    Log.d("repoItemCollect", "${item.country}")
                }
            }
        }
        Log.d("repoItemCollect", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackGenreCountry : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
        oldItem == newItem
}