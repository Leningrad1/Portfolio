package com.example.cinemapark.presentation.recyclerviewadapter.searchkeyword

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardPremierBinding
import com.example.cinemapark.dataclass.searchkeyword.Film

class SearchKeyWordAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<Film, SearchKeyWordAdapter.ItemViewHolder>(DiffUtilCallbackSearchKeyWord()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardPremierBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.nameRu}"
                moveGenre.text = "${item.genres[0].genre}"
                Glide
                    .with(post.context)
                    .load(it.posterUrlPreview)
                    .into(post)
                root.setOnClickListener {
                    onClick(item.filmId)
                }
            }

        }
        Log.d("repoItemSearchKey", "$item")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card_premier, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackSearchKeyWord : DiffUtil.ItemCallback<Film>() {

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem
}
