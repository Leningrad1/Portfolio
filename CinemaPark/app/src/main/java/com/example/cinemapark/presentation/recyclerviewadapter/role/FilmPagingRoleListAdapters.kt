package com.example.cinemapark.presentation.recyclerviewadapter.role

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.PostRoleCardBinding
import com.example.cinemapark.dataclass.role.RoleItem

class FilmPagingRoleListAdapters(private var onClick: (Int) -> Unit) :
    PagingDataAdapter<RoleItem, FilmPagingRoleListAdapters.ItemViewHolder>(DiffUtilCallbackRole()) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PostRoleCardBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                nameActor.text = "${item.nameRu}"
                Log.d("repoItemName", "${item.nameRu}")
                if (item.description == null) {
                    nameRole.text = "${item.professionText}"
                } else {
                    nameRole.text = "${item.description}"
                }
                Glide
                    .with(imageRole.context)
                    .load(it.posterUrl)
                    .into(imageRole)
                root.setOnClickListener {
                    onClick(item.staffId)
                }
            }
        }
        Log.d("repoItemRole", "${item}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_role_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }
}

class DiffUtilCallbackRole : DiffUtil.ItemCallback<RoleItem>() {

    override fun areItemsTheSame(oldItem: RoleItem, newItem: RoleItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: RoleItem, newItem: RoleItem): Boolean =
        oldItem == newItem
}