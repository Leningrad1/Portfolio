package com.example.cinemapark.presentation.recyclerviewadapter.gallerypost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardGalleryBinding
import com.example.cinemapark.dataclass.gallerypost.ItemPost

class FilmPagingGalleryPostListAdapter(private var onClick: (String) -> Unit
) :
    PagingDataAdapter<ItemPost, FilmPagingGalleryPostListAdapter.ItemViewHolder>(
        DiffUtilCallbackGalleryPost()
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

class DiffUtilCallbackGalleryPost : DiffUtil.ItemCallback<ItemPost>() {

    override fun areItemsTheSame(oldItem: ItemPost, newItem: ItemPost): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: ItemPost, newItem: ItemPost): Boolean =
        oldItem == newItem
}