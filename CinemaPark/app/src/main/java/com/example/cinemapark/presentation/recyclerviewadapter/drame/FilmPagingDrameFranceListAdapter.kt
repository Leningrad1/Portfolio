package com.example.cinemapark.presentation.recyclerviewadapter.drame

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
import com.example.cinemapark.dataclass.combined.CombinedDrameFilm


class FilmPagingDrameFranceListAdapter (private var onClick: (Int) -> Unit) :
    PagingDataAdapter<CombinedDrameFilm, FilmPagingDrameFranceListAdapter.ItemViewHolder>(
        DiffUtilCallbackDrameFrance()
    ) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.itemDrame.nameRu}"
                moveGenre.text = "${item.itemDrame.genres[0].genre}"
                textRang.text = "${item.itemDrame.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.itemDrame.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    onClick(item.itemDrame.kinopoiskId)
                }
            }
        }
        Log.d("repoItemDrame", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackDrameFrance : DiffUtil.ItemCallback<CombinedDrameFilm>() {

    override fun areItemsTheSame(oldItem: CombinedDrameFilm, newItem: CombinedDrameFilm): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CombinedDrameFilm, newItem: CombinedDrameFilm): Boolean =
        oldItem == newItem
}