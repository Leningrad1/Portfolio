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
import com.example.cinemapark.dataclass.genrecountry.Genre

class GenreAdapters(private var onClick: (item: Int?) -> Unit) :
    PagingDataAdapter<Genre, GenreAdapters.ItemViewHolder>(DiffUtilCallbackGenre()) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCountryBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                textSearchCountry.text = "${item.genre}"
                Log.d("repoItemNameCollect", "${item.genre}")
                root.setOnClickListener {
                    onClick(item.id)
                    Log.d("repoItemCollect", "${item.genre}")
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

class DiffUtilCallbackGenre : DiffUtil.ItemCallback<Genre>() {

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem == newItem
}