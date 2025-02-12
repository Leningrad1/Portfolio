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
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentBestFilmBinding
import com.example.cinemapark.dataclass.actorpage2.FilmPage
import com.example.cinemapark.presentation.ActorPageFragment.Companion.FILM_INDEX
import com.example.cinemapark.presentation.recyclerviewadapter.actorpage.FilmPagingRoleListAdaptersActorPage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BestFilmFragment : Fragment() {

    private var _binding: FragmentBestFilmBinding? = null
    private val binding get() = _binding!!
    private var listActorFilm = mutableListOf<FilmPage>()
    private var listActorFilmBest = mutableListOf<FilmPage>()
    private val actorChip1 = mutableListOf<FilmPage>()
    private val actorDoubleChip2 = mutableListOf<FilmPage>()
    private val actorYourselfChip3 = mutableListOf<FilmPage>()

    private val viewModel: MainViewModel by viewModels()
    private val pageAdapterRole = FilmPagingRoleListAdaptersActorPage(::onItemClick)
    private var idActorPage = 0
    private fun onItemClick(item: Int) {
        val fragment = BestFilmFragment()
        val bundle = Bundle()
        bundle.putInt(FILM_PAGE, item)
        fragment.arguments = bundle
        findNavController().navigate(
            R.id.action_bestFilmFragment_to_filmPageFragment,
            bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBestFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
        val bundle = this.arguments
        Log.d("idActor1", "$bundle")
        lifecycleScope.launch {
            val actorName = bundle!!.getString(ActorPageFragment.BEST_FILM_ALL)
            Log.d("bestFilmList", "$actorName")
            binding.apply {
                textViewNameListPage.text = actorName.toString()
                val idList = bundle.getIntegerArrayList(ActorPageFragment.BEST_FILM_ALL_ID)
                val idActor = bundle.getInt(ActorPageFragment.ACTOR_ID)
                val idNumber = bundle.getInt(FILM_INDEX)
                if (idNumber == 1) {
                    chipGroupActor.visibility = View.GONE
                }
                val infoActor = viewModel.repo.getActorPage(idActor)
                Log.d("actorFilmFFFFFFF", "${infoActor}")
                idActorPage = idActor
                Log.d("bestFilmListIdlist", "${idActorPage}")
                idList?.forEach {
                    if (infoActor.films[0].professionKey == "ACTOR") {
                        Log.d("ACTOR", "ACTOR")
                        actorChip1.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("ACTORRRRRRR", "${actorChip1}")
                    }
                }
                idList?.forEach {
                    if (infoActor.films[0].professionKey == "DIRECTOR") {
                        actorDoubleChip2.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("HIMSELFRRRRR", "${actorDoubleChip2}")
                    }
                }
                idList?.forEach {
                    if (infoActor.films[0].professionKey == "HIMSELF") {
                        actorYourselfChip3.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("HIMSELFRRRR", "${actorYourselfChip3}")
                    }
                }

                if (idNumber == 1) {
                    Log.d("idNumber", "$idNumber")
                    idList?.forEach {
                        listActorFilm.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("listActorFilm", "$listActorFilm")
                        if (listActorFilm[0].ratingImdb!! >= 7.0) {
                            listActorFilmBest.add(viewModel.repo.getFilmPageIdActor(it))
                            Log.d("bestlistActorFilmBest", "${listActorFilmBest}")
                        }
                    }
                    val pagingData: PagingData<FilmPage> =
                        PagingData.from(listActorFilmBest.subList(0, idList?.size ?: 1))
                    pageAdapterRole.submitData(lifecycle, pagingData)
                    listFilmRecycler.adapter = pageAdapterRole
                } else {
                    Log.d("idNumber", "$idNumber")
                    idList?.forEach {
                        listActorFilm.add(viewModel.repo.getFilmPageIdActor(it))
                        Log.d("bestListActorFilmBest", "$listActorFilm")
                    }
                    if (viewModel.chilBoolean != -1) {
                        val selectedChip =
                            chipGroupActor.findViewById<Chip>(viewModel.chilBoolean)
                        selectedChip?.isChecked = true
                    }
                    chip1.isChecked = true
                    if (viewModel.chilBoolean == -1) {
                        val pagingData: PagingData<FilmPage> =
                            PagingData.from(actorChip1.subList(0, idList?.size ?: 1))
                        pageAdapterRole.submitData(lifecycle, pagingData)
                    }
                    when (viewModel.chilBoolean) {
                        2131296419 -> {
                            if (actorChip1.size != 0) {
                                Log.d("viewModel.count", "${viewModel.count}")
                                listFilmRecycler.adapter = pageAdapterRole
                                val pagingDataChip1: PagingData<FilmPage> =
                                    PagingData.from(actorChip1.subList(0, idList!!.size - 1))
                                pageAdapterRole.submitData(lifecycle, pagingDataChip1)
                            }
                        }

                        2131296420 -> {
                            if (actorDoubleChip2.size != 0) {
                                Log.d("viewModel.count2", "${viewModel.count}")
                                listFilmRecycler.adapter = pageAdapterRole
                                val pagingDataChip2: PagingData<FilmPage> =
                                    PagingData.from(actorDoubleChip2.subList(0, idList!!.size - 1))
                                pageAdapterRole.submitData(lifecycle, pagingDataChip2)
                            }
                        }

                        2131296421 -> {
                            if (actorYourselfChip3.size != 0) {
                                Log.d("viewModel.count3", "${viewModel.count}")
                                listFilmRecycler.adapter = pageAdapterRole
                                val pagingDataChip3: PagingData<FilmPage> =
                                    PagingData.from(
                                        actorYourselfChip3.subList(0, idList!!.size - 1)
                                    )
                                pageAdapterRole.submitData(lifecycle, pagingDataChip3)
                            }
                        }
                    }
                    chipGroupActor.setOnCheckedChangeListener { group, checkedIds ->
                        val chip: Chip? = group.findViewById(checkedIds)
                        viewModel.chilBoolean = checkedIds
                        when (chip?.id) {
                            R.id.chip1 -> {
                                if (actorChip1.size != 0) {
                                    listFilmRecycler.adapter = pageAdapterRole
                                    val pagingDataChip1: PagingData<FilmPage> =
                                        PagingData.from(actorChip1.subList(0, idList!!.size - 1))
                                    pageAdapterRole.submitData(lifecycle, pagingDataChip1)
                                    Log.d("chip1", "$checkedIds")
                                }
                            }

                            R.id.chip2 -> {
                                if (actorDoubleChip2.size != 0) {
                                    listFilmRecycler.adapter = pageAdapterRole
                                    val pagingDataChip2: PagingData<FilmPage> =
                                        PagingData.from(
                                            actorDoubleChip2.subList(
                                                0,
                                                idList!!.size - 1
                                            )
                                        )
                                    pageAdapterRole.submitData(lifecycle, pagingDataChip2)
                                    Log.d("chip2", "$checkedIds")
                                }
                            }

                            R.id.chip3 -> {
                                if (actorYourselfChip3.size != 0) {
                                    listFilmRecycler.adapter = pageAdapterRole
                                    val pagingDataChip3: PagingData<FilmPage> =
                                        PagingData.from(
                                            actorYourselfChip3.subList(
                                                0,
                                                idList!!.size - 1
                                            )
                                        )
                                    pageAdapterRole.submitData(lifecycle, pagingDataChip3)
                                    Log.d("chip3", "$checkedIds")
                                }
                            }
                        }
                    }
                    chip1.text = "Актер ${actorChip1.size}"
                    chip2.text = "Актер дубляжа ${actorDoubleChip2.size}"
                    chip3.text = "Актер играет сам себя ${actorYourselfChip3.size}"
                    listFilmRecycler.adapter = pageAdapterRole
                    liftBack.setOnClickListener {
                        bundle.putInt(ACTOR_PAGE, idActorPage)
                        findNavController().navigate(
                            R.id.action_bestFilmFragment_to_actorPageFragment,
                            bundle
                        )
                    }
                }
                liftBack.setOnClickListener {
                    bundle.putInt(ACTOR_PAGE, idActorPage)
                    findNavController().navigate(
                        R.id.action_bestFilmFragment_to_actorPageFragment,
                        bundle
                    )
                }
                home.setOnClickListener {
                    findNavController().navigate(R.id.action_bestFilmFragment_to_homepageFragment)
                }
                search.setOnClickListener {
                    findNavController().navigate(R.id.action_bestFilmFragment_to_searchFragment)
                }
                profile.setOnClickListener {
                    findNavController().navigate(R.id.action_bestFilmFragment_to_profileFragment)
                }

            }

        }
        } catch (e:Exception){
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

    companion object {
        const val ACTOR_PAGE = "id"
        const val FILM_PAGE = "id"
    }
}