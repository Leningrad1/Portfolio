package com.example.cinemapark.presentation.recyclerviewadapter.premier

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
import com.example.cinemapark.dataclass.combined.CombinedPremier

class FilmPagingPremierListAdapter (private var onClick: (Int) -> Unit) :
PagingDataAdapter<CombinedPremier, FilmPagingPremierListAdapter.ItemViewHolder>(DiffUtilCallbackPremier()) {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostCardPremierBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                moveTitle.text = "${item.itemPremier.nameRu}"
                moveGenre.text = "${item.itemPremier.genres[0].genre}"
                Glide
                    .with(post.context)
                    .load(it.itemPremier.posterUrlPreview)
                    .into(post)
                look.visibility = if (item.seeFilm) View.VISIBLE else View.GONE
                root.setOnClickListener {
                    onClick(item.itemPremier.kinopoiskId)
                }
            }
        }
        Log.d("repoItemPrem", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_card_premier, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackPremier : DiffUtil.ItemCallback<CombinedPremier>() {


    override fun areItemsTheSame(oldItem: CombinedPremier, newItem: CombinedPremier): Boolean  =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: CombinedPremier, newItem: CombinedPremier): Boolean =
        oldItem == newItem

}