package com.example.cinemapark.presentation.recyclerviewadapter.collect

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemapark.R
import com.example.cinemapark.databinding.ItemSheetCollectBinding
import com.example.cinemapark.dataclass.dbcollectfilm.FilmsWithCollection


class FilmCollectListAdapters(
    private var onClick: (item: Int?) -> Unit,
    private val currentMovieId: Int
) :
    PagingDataAdapter<FilmsWithCollection, FilmCollectListAdapters.ItemViewHolder>(
        DiffUtilCallbackCollect()
    ) {
    private var onCheckBoxClickListener: OnCheckBoxClickListener? = null
    private val checkedItems = mutableSetOf<Int>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSheetCollectBinding.bind(view)
    }

    fun setOnCheckBoxClickListener(listener: OnCheckBoxClickListener) {
        this.onCheckBoxClickListener = listener
    }
    var boolean = false
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                checkBoxCollect.setOnCheckedChangeListener(null)
                item.movieList.firstOrNull { it.movieId == currentMovieId }?.let {
                    Log.d("isCheckIt", "${it}")
                    boolean = it.movieId == currentMovieId
                }
                var isCheked = item.movieList.any { it.isCheck } && boolean
                Log.d("isCheck","${item.movieList.any { it.isCheck }}, ${item.movieList}, ${boolean}")
                checkBoxCollect.isChecked = isCheked
                checkBoxCollect.text = item.collectFilm.collectFilm
                textSizeRusFilmIS.text = "${item.movieList.size}"
                checkBoxCollect.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        checkedItems.add(position)
                    } else {
                        checkedItems.remove(position)
                    }
                    onCheckBoxClickListener?.onCheckBoxClicked(position, isChecked)
                }
                root.setOnClickListener {
                    onClick(item.movieList[0].movieId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sheet_collect, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

interface OnCheckBoxClickListener {
    fun onCheckBoxClicked(position: Int, isCheckBoxChecked: Boolean)
}

class DiffUtilCallbackCollect : DiffUtil.ItemCallback<FilmsWithCollection>() {

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