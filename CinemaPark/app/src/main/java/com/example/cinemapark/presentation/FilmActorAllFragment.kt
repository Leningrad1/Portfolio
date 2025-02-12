package com.example.cinemapark.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.cinemapark.R
import com.example.cinemapark.presentation.FilmPageFragment.Companion.ID_FILMS_ROLE
import com.example.cinemapark.databinding.FragmentFilmActorAllBinding
import com.example.cinemapark.dataclass.role.RoleItem
import com.example.cinemapark.presentation.recyclerviewadapter.role.FilmPagingRoleListAdapters
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FilmActorAllFragment : Fragment() {
    private var _binding: FragmentFilmActorAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val pageAdapterRole = FilmPagingRoleListAdapters(::onItemClick)
    private var listId = 0
    private var filmPage = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmActorAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            binding.recyclerActor.adapter = pageAdapterRole
            lifecycleScope.launch {
                var id = bundle!!.getInt(ID_FILMS_ROLE)
                if (id == 0) {
                    listId = bundle.getInt(ActorPageFragment.ACTOR_PAGE_FILM)
                    id = listId
                }
                filmPage = id
                Log.d("reporesglideIdActor", "${id}")
                viewModel.reloadTest(id)
                Log.d("reponameActor", "${viewModel.state.value}")
                delay(3000)
                val roleInfo = viewModel.repo.getRoleInfo(id)
                val pagingData: PagingData<RoleItem> = PagingData.from(roleInfo)
                pageAdapterRole.submitData(pagingData)
                binding.liftBack.setOnClickListener {
                    bundle.putInt(FILM_LIST_BACK, listId)
                    findNavController().navigate(
                        R.id.action_filmActorAllFragment_to_filmPageFragment,
                        bundle
                    )
                }
                Log.d("reporesviewActor", "${viewModel.reloadTest(id)}")
                Log.d("reporesview2Actor", "${viewModel.state.value?.kinopoiskId}")
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
        lifecycleScope.launch {
            val fragment = FilmActorAllFragment()
            val bundle = Bundle()
            bundle.putInt(ID_ACTOR, item)
            bundle.putInt(FILM_LIST, filmPage)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_filmActorAllFragment_to_actorPageFragment,
                bundle
            )
            Log.d("bundle", "$bundle")
        }
    }

    companion object {
        const val ID_ACTOR = "id"
        const val FILM_LIST = "list"
        const val FILM_LIST_BACK = "list1"
    }
}