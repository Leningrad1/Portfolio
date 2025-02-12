package com.example.cinemapark.presentation.recyclerviewadapter.galleryframe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardGalleryBinding
import com.example.cinemapark.dataclass.galleryframe.ItemFrame

class FilmPagingGalleryFrameListAdapter(private var onClick: (String) -> Unit
) :
    PagingDataAdapter<ItemFrame, FilmPagingGalleryFrameListAdapter.ItemViewHolder>(
        DiffUtilCallbackGalleryFrame()
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

class DiffUtilCallbackGalleryFrame : DiffUtil.ItemCallback<ItemFrame>() {

    override fun areItemsTheSame(oldItem: ItemFrame, newItem: ItemFrame): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: ItemFrame, newItem: ItemFrame): Boolean =
        oldItem == newItem
}