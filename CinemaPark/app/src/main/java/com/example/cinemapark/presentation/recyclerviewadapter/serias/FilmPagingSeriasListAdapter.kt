package com.example.cinemapark.presentation.recyclerviewadapter.serias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardBinding
import com.example.cinemapark.dataclass.combined.CombinedSeries


class FilmPagingSeriasListAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<CombinedSeries, FilmPagingSeriasListAdapter.ItemViewHolder>(
        DiffUtilCallbackSerias()
    ) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.itemSeries.nameRu}"
                moveGenre.text = "${item.itemSeries.genres[0].genre}"
                textRang.text = "${item.itemSeries.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.itemSeries.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    onClick(item.itemSeries.kinopoiskId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackSerias : DiffUtil.ItemCallback<CombinedSeries>() {

    override fun areItemsTheSame(oldItem: CombinedSeries, newItem: CombinedSeries): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CombinedSeries, newItem: CombinedSeries): Boolean =
        oldItem == newItem

}