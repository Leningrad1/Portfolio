package com.example.cinemapark.presentation.recyclerviewadapter.action

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
import com.example.cinemapark.dataclass.combined.CombinedUsaMilitary

class FilmUsaActionListAdapter(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<CombinedUsaMilitary, FilmUsaActionListAdapter.ItemViewHolder>(DiffUtilCallbackUsaAction()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.itemUsaMilitary.nameRu}"
                moveGenre.text = "${item.itemUsaMilitary.genres[1].genre}"
                textRang.text = "${item.itemUsaMilitary.ratingKinopoisk}"
                Glide
                    .with(post.context)
                    .load(it.itemUsaMilitary.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {

                    onClick(item.itemUsaMilitary.kinopoiskId)

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

class DiffUtilCallbackUsaAction : DiffUtil.ItemCallback<CombinedUsaMilitary>() {

    override fun areItemsTheSame(oldItem: CombinedUsaMilitary, newItem: CombinedUsaMilitary): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CombinedUsaMilitary, newItem: CombinedUsaMilitary): Boolean =
        oldItem == newItem
}