package com.example.headhunterapps.presentation.recyclerviewadapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.headhunterapp.model.Offer
import com.example.headhunterapps.R

class JobTitleRecyclerViewAdapter(private val offers: List<Offer>) : RecyclerView.Adapter<JobTitleRecyclerViewAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_entry_format, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.bind(offer)
    }

    override fun getItemCount(): Int = offers.size

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.workNear)
        private val imageView: ImageView = itemView.findViewById(R.id.imageCircle)
        private val button: TextView = itemView.findViewById(R.id.workButton)
        private lateinit var currentOffer: Offer

        init {
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentOffer.link))
                itemView.context.startActivity(intent)
            }
        }

        fun bind(offer: Offer) {
            currentOffer = offer
            titleTextView.text = offer.title
            imageView.setImageResource(offer.image)
            if (offer.button != null) {
                button.text = offer.button.text
                button.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentOffer.link))
                    itemView.context.startActivity(intent)
                }
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
            }
        }
    }
}