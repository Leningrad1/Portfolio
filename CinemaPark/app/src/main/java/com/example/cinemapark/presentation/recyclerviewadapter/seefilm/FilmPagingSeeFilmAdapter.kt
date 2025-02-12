package com.example.cinemapark.presentation.recyclerviewadapter.seefilm

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
import com.example.cinemapark.dataclass.filmpage.FilmPage

class FilmPagingSeeFilmAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<FilmPage, FilmPagingSeeFilmAdapter.ItemViewHolder>(DiffUtilCallbackSeeFilmPage()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.nameRu}"
                moveGenre.text = "${item.genres?.get(0)?.genre}"
                textRang.text = "${item.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.posterUrlPreview)
                    .into(post)
                root.setOnClickListener {
                    item.kinopoiskId?.let { it1 -> onClick(it1) }
                }
            }

        }
        Log.d("repoItemAction", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackSeeFilmPage : DiffUtil.ItemCallback<FilmPage>() {

    override fun areItemsTheSame(oldItem: FilmPage, newItem: FilmPage): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: FilmPage, newItem: FilmPage): Boolean =
        oldItem == newItem
}
