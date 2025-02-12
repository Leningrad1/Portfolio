package com.example.cinemapark.presentation.recyclerviewadapter.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardGalleryBinding
import com.example.cinemapark.dataclass.gallery.Item

class FilmPagingGalleryListAdapter(private var onClick: (String) -> Unit
) :
    PagingDataAdapter<Item, FilmPagingGalleryListAdapter.ItemViewHolder>(
        DiffUtilCallbackGallery()
    ) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardGalleryBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(postGallery.context)
                    .load(it.imageUrl)
                    .into(postGallery)
                root.setOnClickListener {
                    onClick(item.imageUrl)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card_gallery, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackGallery : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem
}