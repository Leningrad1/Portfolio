package com.example.cinemapark.presentation.recyclerviewadapter.concatadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.ItemCountryBinding
import com.example.cinemapark.databinding.ItemProfileBinding
import com.example.cinemapark.databinding.ItemYearBinding
import com.example.cinemapark.dataclass.genrecountry.Country
import com.example.cinemapark.dataclass.genrecountry.Genre

class FooterConcatFavouriteAdapter(private var onClick: (item: String) -> Unit, private val listIntYear: String) :
    Adapter<FooterConcatFavouriteAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemProfileBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listIntYear[position]
        with(holder.binding) {
            item?.let {
                Glide.with(cardCollectImage.context)
                    .load(R.drawable.icons_clean)
                    .into(cardCollectImage)
                cardCollectText.text = "$listIntYear"
                Log.d("repoItemNameCollect", "${item}")
                root.setOnClickListener {
                    onClick(item.toString())
                    Log.d("repoItemClick", "${item}")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = 1
}