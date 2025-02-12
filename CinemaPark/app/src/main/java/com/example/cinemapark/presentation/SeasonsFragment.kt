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
import com.example.cinemapark.databinding.FragmentSeasonsBinding
import com.example.cinemapark.dataclass.seasons.Episode
import com.example.cinemapark.dataclass.seasons.Item
import com.example.cinemapark.presentation.recyclerviewadapter.seasons.SerialsAdapters
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SeasonsFragment : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding!!
    private val seasons = mutableListOf<Int>()
    private val viewModel: MainViewModel by viewModels()
    private val seasonsAdapter = SerialsAdapters(::onClick)

    private fun onClick(i: Int?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            lifecycleScope.launch {
                val id = bundle!!.getInt(FilmPageFragment.ID_SEASONS)
                val nameFilm = bundle.getString(FilmPageFragment.NAME_FILM)
                seasons.add(viewModel.repo.getSeasonsId(id).total)
                binding.nameSerials.text = nameFilm
                binding.recyclerEpisod.adapter = seasonsAdapter
                val infoSeasons = viewModel.repo.getSeasonsId(id).items
                createAndAddChips(
                    viewModel.repo.getSeasonsId(id).total,
                    infoSeasons
                )
                binding.backView.setOnClickListener {
                    bundle.putInt(BACK_ID, id)
                    findNavController().navigate(
                        R.id.action_seasonsFragment_to_filmPageFragment,
                        bundle
                    )
                }
                binding.home.setOnClickListener {
                    findNavController().navigate(R.id.action_seasonsFragment_to_homepageFragment)
                }
                binding.search.setOnClickListener {
                    findNavController().navigate(R.id.action_seasonsFragment_to_searchFragment)
                }
                binding.profile.setOnClickListener {
                    findNavController().navigate(R.id.action_seasonsFragment_to_profileFragment)
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }

    private fun createAndAddChips(seasons: Int, infoSeasons: List<Item>) {

        val chipGroup = binding.chipGroup
        for (seasonIndex in 0..<seasons) {
            val season = infoSeasons[seasonIndex]
            val seasonChip = Chip(requireContext())
            seasonChip.text = "${seasonIndex + 1}"
            seasonChip.isClickable = true
            seasonChip.isCheckable = true
            seasonChip.setOnClickListener {
                val pagingData: PagingData<Episode> =
                    PagingData.from(season.episodes)
                seasonsAdapter.submitData(lifecycle, pagingData)
            }
            chipGroup.addView(seasonChip)
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
        const val BACK_ID = "back"
    }
}