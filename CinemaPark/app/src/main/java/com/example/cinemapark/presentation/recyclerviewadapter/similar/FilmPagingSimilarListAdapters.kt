package com.example.cinemapark.presentation.recyclerviewadapter.similar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardBinding
import com.example.cinemapark.dataclass.similar.Item

class FilmPagingSimilarListAdapters(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<Item, FilmPagingSimilarListAdapters.ItemViewHolder>(DiffUtilCallbackRole()) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.nameRu}"
                Log.d("repoItemName", "${item.nameRu}")
                moveGenre.text = "${item.nameEn}"
                Glide
                    .with(post.context)
                    .load(it.posterUrl)
                    .into(post)
                root.setOnClickListener {
                    onClick(item.filmId)
                }
            }
        }
        Log.d("repoItemRole", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackRole : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem
}