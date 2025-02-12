package com.example.cinemapark.presentation.recyclerviewadapter.collect

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.ItemCollectFilmBinding
import com.example.cinemapark.dataclass.dbcollectfilm.FilmsWithCollection


class FilmCollectProfileListAdapters(
    private var onClick: (String) -> Unit
) :
    PagingDataAdapter<FilmsWithCollection, FilmCollectProfileListAdapters.ItemViewHolder>(
        DiffUtilCallbackCollectProfile()
    ) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCollectFilmBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                cardCollectText.text = "${item.collectFilm.collectFilm}"
                cardButtonCollect.text = "${item.movieList.size}"
                if (item.collectFilm.collectFilm.contains("Любимое")) {
                    Glide.with(cardCollectImage.context)
                        .load(R.drawable.icon_like)
                        .into(cardCollectImage)
                }
                if (item.collectFilm.collectFilm.contains("Хочу посмотреть")) {
                    Glide.with(cardCollectImage.context)
                        .load(R.drawable.icon_see_yes)
                        .into(cardCollectImage)
                }
                if (item.collectFilm.collectFilm.contains("Русское кино")) {
                    Glide.with(cardCollectImage.context)
                        .load(R.drawable.profile)
                        .into(cardCollectImage)
                }

                if (!item.collectFilm.collectFilm.contains("Русское кино") &&
                    !item.collectFilm.collectFilm.contains("Хочу посмотреть") &&
                    !item.collectFilm.collectFilm.contains("Любимое")) {
                    Glide.with(cardCollectImage.context)
                        .load(R.drawable.home)
                        .into(cardCollectImage)
                }

                root.setOnClickListener {
                    onClick(item.collectFilm.collectFilm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collect_film, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackCollectProfile : DiffUtil.ItemCallback<FilmsWithCollection>() {

    override fun areItemsTheSame(
        oldItem: FilmsWithCollection,
        newItem: FilmsWithCollection
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: FilmsWithCollection,
        newItem: FilmsWithCollection
    ): Boolean =
        oldItem == newItem
}