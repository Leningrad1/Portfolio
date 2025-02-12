package com.example.cinemapark.presentation.recyclerviewadapter.searchfilmparam

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
import com.example.cinemapark.dataclass.searchparam.Item

class SearchFilmParamAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<Item, SearchFilmParamAdapter.ItemViewHolder>(DiffUtilCallbackSearchParam()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardPremierBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("itemParamAda", "$item")
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.nameRu}"
                Log.d("itemMovieT", "${item.nameRu}")
                moveGenre.text = "${item.genres[0].genre}"
                Glide
                    .with(post.context)
                    .load(it.posterUrlPreview)
                    .into(post)
                root.setOnClickListener {
                    onClick(item.kinopoiskId)
                }
            }

        }
        Log.d("repoItemSearchParam", "$item")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card_premier, parent, false)
        Log.d("adapterLayoutParam", "$adapterLayout")
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackSearchParam : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem
}
