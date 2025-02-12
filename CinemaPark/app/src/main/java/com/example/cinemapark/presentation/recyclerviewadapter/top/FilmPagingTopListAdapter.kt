package com.example.cinemapark.presentation.recyclerviewadapter.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardBinding
import com.example.cinemapark.dataclass.combined.CombinedTopFilm

class FilmTopPagingListAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<CombinedTopFilm, FilmTopPagingListAdapter.ItemViewHolder>(DiffUtilCallback()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.itemTop.nameRu}"
                moveGenre.text = "${item.itemTop.genres[0].genre}"
                textRang.text = "${item.itemTop.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.itemTop.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    onClick(item.itemTop.kinopoiskId)
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

class DiffUtilCallback : DiffUtil.ItemCallback<CombinedTopFilm>() {
    override fun areItemsTheSame(oldItem: CombinedTopFilm, newItem: CombinedTopFilm): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: CombinedTopFilm, newItem: CombinedTopFilm): Boolean =
        oldItem == newItem

}