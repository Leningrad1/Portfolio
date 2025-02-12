package com.example.cinemapark.presentation.recyclerviewadapter.actorpage

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
import com.example.cinemapark.dataclass.actorpage2.FilmPage

class FilmPagingRoleListAdaptersActorPage(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<FilmPage, FilmPagingRoleListAdaptersActorPage.ItemViewHolder>(DiffUtilCallbackRoleActorePage()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.nameRu}"
                moveGenre.text = "${item.genres?.get(0)?.genre}"
                textRang.text = "${item.ratingImdb}"
                Glide
                    .with(post.context)
                    .load(it.posterUrl)
                    .into(post)
                root.setOnClickListener {
                    onClick(item.kinopoiskId!!)
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

class DiffUtilCallbackRoleActorePage : DiffUtil.ItemCallback<FilmPage>() {

    override fun areItemsTheSame(oldItem: FilmPage, newItem: FilmPage): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: FilmPage, newItem: FilmPage): Boolean =
        oldItem == newItem
}
