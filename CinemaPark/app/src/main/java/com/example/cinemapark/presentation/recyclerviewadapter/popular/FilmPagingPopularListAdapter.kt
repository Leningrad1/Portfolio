package com.example.cinemapark.presentation.recyclerviewadapter.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostCardBinding
import com.example.cinemapark.dataclass.combined.CombinedPop

class FilmPagingPopularListAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<CombinedPop, FilmPagingPopularListAdapter.ItemViewHolder>(
        DiffUtilCallbackPopular()
    ) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = item.itemPop.nameRu
                moveGenre.text = item.itemPop.genres[0].genre
                textRang.text = "${item.itemPop.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.itemPop.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    onClick(item.itemPop.kinopoiskId)
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

class DiffUtilCallbackPopular : DiffUtil.ItemCallback<CombinedPop>() {

    override fun areItemsTheSame(oldItem: CombinedPop, newItem: CombinedPop): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: CombinedPop, newItem: CombinedPop): Boolean =
        oldItem == newItem
}