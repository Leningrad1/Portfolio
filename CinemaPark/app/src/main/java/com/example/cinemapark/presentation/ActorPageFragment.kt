package com.example.cinemapark.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentActorPageBinding
import com.example.cinemapark.dataclass.actorpage2.FilmPage
import com.example.cinemapark.presentation.FilmPageFragment.Companion.ACTOR_PAGE
import com.example.cinemapark.presentation.FilmPageFragment.Companion.ID_FILM
import com.example.cinemapark.presentation.recyclerviewadapter.actorpage.FilmPagingRoleListAdaptersActorPage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActorPageFragment : Fragment() {

    private var _binding: FragmentActorPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var listActorFilm = mutableListOf<FilmPage>()
    private var listActorFilmBest = mutableListOf<FilmPage>()
    private val pageAdapterRole = FilmPagingRoleListAdaptersActorPage(::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            lifecycleScope.launch {
                Log.d("idActor1", "${bundle}")
                var id = bundle!!.getInt(ACTOR_PAGE)
                val idActorName = bundle.getInt(BestFilmFragment.ACTOR_PAGE)
                val idActorNameFilmActorAll = bundle.getInt(FilmActorAllFragment.ID_ACTOR)
                if (id == 0) {
                    id = idActorName
                }
                if (id == 0) {
                    id = idActorNameFilmActorAll
                }
                val idFilmPage = bundle.getInt(ID_FILM)
                Log.d("idActor2", "${id}")
                Log.d("idActor4", "${idFilmPage}")
                binding.backActorPageToFilmPageFragment.setOnClickListener {
                    bundle.putInt(ACTOR_PAGE_FILM, idFilmPage)
                    findNavController().navigate(
                        R.id.action_actorPageFragment_to_filmPageFragment,
                        bundle
                    )
                    Log.d("idActor3", "${bundle}")
                }
                val infoActor = viewModel.repo.getActorPage(id)
                binding.apply {
                    Glide.with(requireContext())
                        .load(infoActor.posterUrl)
                        .into(fotoActor)
                    nameActor.text = infoActor.nameRu
                    role.text = infoActor.profession
                    allFilmActor.text = "${infoActor.films.size} фильма(ов)"
                    forwardListBestFilmActor.setOnClickListener {
                        val fragment = ActorPageFragment()
                        bundle.putString(BEST_FILM_ALL, infoActor.nameRu)
                        bundle.putIntegerArrayList(BEST_FILM_ALL_ID, viewModel.filmActor)
                        bundle.putInt(ACTOR_ID, id)
                        bundle.putInt(FILM_INDEX, 1)
                        fragment.arguments = bundle
                        findNavController().navigate(
                            R.id.action_actorPageFragment_to_bestFilmFragment,
                            bundle
                        )
                    }
                    forwardFilmographyActor.setOnClickListener {
                        val fragment = ActorPageFragment()
                        bundle.putString(BEST_FILM_ALL, infoActor.nameRu)
                        bundle.putIntegerArrayList(BEST_FILM_ALL_ID, viewModel.filmActor)
                        bundle.putInt(ACTOR_ID, id)
                        fragment.arguments = bundle
                        findNavController().navigate(
                            R.id.action_actorPageFragment_to_bestFilmFragment,
                            bundle
                        )
                    }
                    home.setOnClickListener {
                        findNavController().navigate(R.id.action_actorPageFragment_to_homepageFragment)
                    }
                    search.setOnClickListener {
                        findNavController().navigate(R.id.action_actorPageFragment_to_searchFragment)
                    }
                    profile.setOnClickListener {
                        findNavController().navigate(R.id.action_actorPageFragment_to_profileFragment)
                    }
                }
                infoActor.films.forEach {
                    viewModel.filmActor.add(it.filmId)
                    Log.d("idFilmActorList", "${viewModel.filmActor}")
                }
                viewModel.filmActor.forEach {
                    listActorFilm.add(viewModel.repo.getFilmPageIdActor(it!!))
                    if (listActorFilm[0].ratingImdb!! >= 7.0) {
                        listActorFilmBest.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("listActorFilmBest", "$listActorFilmBest")
                    }
                }
                Log.d("idFilmActorList2", "$listActorFilm")
                if (listActorFilm.size > 19) {
                    listActorFilm.forEach {
                        val pagingData: PagingData<FilmPage> =
                            PagingData.from(listActorFilm.subList(0, 20))
                        pageAdapterRole.submitData(lifecycle, pagingData)
                    }
                } else {
                    val pagingData: PagingData<FilmPage> =
                        PagingData.from(listActorFilm.subList(0, listActorFilm.size))
                    pageAdapterRole.submitData(lifecycle, pagingData)
                    binding.apply {
                        allBestFilmActor.visibility = View.GONE
                        forwardListBestFilmActor.visibility = View.GONE
                    }
                }
                binding.recyclerRole.adapter = pageAdapterRole
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }
    private fun sheetBottomError() {
        viewLifecycleOwner.lifecycleScope.launch {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.item_botom_sheet_error, null)
            bottomSheetDialog.setContentView(view)
            delay(2000)
            bottomSheetDialog.show()
            val close: ImageView = view.findViewById(R.id.closeNameCollect)
            close.setOnClickListener {
                Log.d("close", "close")
                bottomSheetDialog.dismiss()
            }
        }
    }

    private fun onItemClick(item: Int) {
        val fragment = ActorPageFragment()
        val bundle = Bundle()
        bundle.putInt(FILM_ID_BEST, item)
        fragment.arguments = bundle
        findNavController().navigate(
            R.id.action_actorPageFragment_to_filmPageFragment,
            bundle
        )
    }

    companion object {
        const val ACTOR_PAGE_FILM = "id"
        const val BEST_FILM_ALL = "best film"
        const val BEST_FILM_ALL_ID = "best film list"
        const val ACTOR_ID = "id"
        const val FILM_ID_BEST = "id best"
        const val FILM_INDEX = "number best"

    }
}