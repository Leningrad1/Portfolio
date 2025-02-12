package com.example.cinemapark.presentation.recyclerviewadapter.seasons

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostSeasonsBinding
import com.example.cinemapark.dataclass.seasons.Episode
import com.example.cinemapark.dataclass.seasons.Item
import com.example.cinemapark.dataclass.seasons.Seasons

class SerialsAdapters(private var onClick: (item: Int?) -> Unit) :
    PagingDataAdapter<Episode, SerialsAdapters.ItemViewHolder>(DiffUtilCallbackCollectSeasons()) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostSeasonsBinding.bind(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                nameSeria.text = "${item.episodeNumber} серия."
                dateSeria.text = item.releaseDate
                root.setOnClickListener {
                    onClick(item.episodeNumber)
                }
            }
        }
        Log.d("repoItemCollect", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_seasons, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackCollectSeasons : DiffUtil.ItemCallback<Episode>() {

    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem == newItem
}