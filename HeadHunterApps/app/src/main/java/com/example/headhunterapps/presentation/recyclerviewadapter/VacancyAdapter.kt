package com.example.headhunterapps.presentation.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.headhunterapp.model.Vacancy
import com.example.headhunterapps.R
import com.example.headhunterapps.presentation.ViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.KFunction0

class VacancyAdapter @Inject constructor(
    private val context: Context,
    private var vacancies: List<Vacancy>,
    private val onClickListener: KFunction0<Unit>,
    private val viewModel: ViewModel
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peopleLookTitle: TextView = itemView.findViewById(R.id.peopleLookTitle1)
        val likeFavourites: ImageView = itemView.findViewById(R.id.likeFavourites)
        val nameJob: TextView = itemView.findViewById(R.id.nameJob)
        val cityJob: TextView = itemView.findViewById(R.id.cityJob)
        val moneyJob: TextView = itemView.findViewById(R.id.moneyJob)
        val nameWork: TextView = itemView.findViewById(R.id.nameWork)
        val imageWork: ImageView = itemView.findViewById(R.id.imageWork)
        val experienceWork: TextView = itemView.findViewById(R.id.experienceWork)
        val publicWork: TextView = itemView.findViewById(R.id.publicWork)
        val nextButton: AppCompatButton = itemView.findViewById(R.id.nextButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.post_search, parent, false)
        return VacancyViewHolder(view)
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        if (vacancy.lookingNumber != null && vacancy.lookingNumber > 0) {
            holder.peopleLookTitle.text = getLookingString(vacancy.lookingNumber)
        } else {
            holder.peopleLookTitle.visibility = View.INVISIBLE
        }
        if (vacancy.isFavorite) {
            viewModel.onAddLikeVacancy(vacancy.id, true)
            holder.likeFavourites.setImageResource(R.drawable.tab_fav_blue)
        } else {
            holder.likeFavourites.setImageResource(R.drawable.tab_heard)
        }
        holder.likeFavourites.setOnClickListener {
            toggleFavorite(holder.likeFavourites, vacancy)
            notifyDataSetChanged()
        }
        if (vacancy.salary.short != null) {
            holder.moneyJob.text = vacancy.salary.short
        } else {
            holder.moneyJob.visibility = View.GONE
        }
        holder.nameJob.text = vacancy.title
        holder.cityJob.text = vacancy.address.town
        holder.nameWork.text = vacancy.company
        holder.imageWork.setImageResource(R.drawable.icon_work)
        holder.experienceWork.text = vacancy.experience.previewText
        holder.publicWork.text = getPublishedDateString(vacancy.publishedDate)
        holder.nextButton.setOnClickListener {
            /*onClickListener.onApplyClicked(vacancy)*/
        }
    }

    private fun getLookingString(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "Сейчас просматривает ${count} человек"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "Сейчас просматривают ${count} человека"
            else -> "Сейчас просматривают ${count} людей"
        }
    }

    private fun getPublishedDateString(inputDate: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val parsedDate = inputFormatter.parse(inputDate)
            val outputFormatter = SimpleDateFormat("dd MMMM", Locale.forLanguageTag("ru"))
            return "Опубликовано " + outputFormatter.format(parsedDate)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun toggleFavorite(imageView: ImageView, vacancy: Vacancy) {
        vacancy.isFavorite = !vacancy.isFavorite
        if (vacancy.isFavorite) {
            viewModel.onAddLikeVacancy(vacancy.id, true)
            imageView.setImageResource(R.drawable.tab_fav_blue)
        } else {
            viewModel.onRemoveLikeVacancy(vacancy.id, false)
            imageView.setImageResource(R.drawable.tab_heard)
        }
    }

}