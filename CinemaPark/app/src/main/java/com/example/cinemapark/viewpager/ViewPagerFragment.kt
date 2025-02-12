package com.example.cinemapark.viewpager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentViewPagerBinding
import com.example.cinemapark.presentation.OnBoardindFragment
import com.example.cinemapark.presentation.OnBoarding2Fragment
import com.example.cinemapark.presentation.OnBoarding3Fragment
import kotlinx.coroutines.launch

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = listOf<Fragment>(
            OnBoardindFragment(),
            OnBoarding2Fragment(),
            OnBoarding3Fragment()
        )
        val adapterVP =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapterVP
        // В вашем фрагменте, где происходит первая загрузка приложения
        findNavController() //инициализирую navControl, иначе value null, можно и на прямую, но выдает ошибки
        // Проверяем, был ли уже открыт Fragment2
        val prefs = requireActivity().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
        if (prefs.getBoolean(FRAGMENT_VISITED, false)) {
            navController.navigate(R.id.homepageFragment)
        }
    }

    private fun findNavController(): NavController {
        if (!this::navController.isInitialized) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        }
        return navController
    }

    override fun onResume() {
        super.onResume()
        // Сохраняем состояние, о запуске приложения
        val editor = requireActivity().getSharedPreferences(APP_PREF, Context.MODE_PRIVATE).edit()
        editor.putBoolean(FRAGMENT_VISITED, true)
        editor.apply()
    }

    companion object {
        private const val FRAGMENT_VISITED = "fragment2_visited"
        private const val APP_PREF = "app_preferences"
    }
}