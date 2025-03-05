package com.example.headhunterapp.presentation.entryfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.headhunterapp.R
import com.example.headhunterapp.databinding.FragmentEntryPasswordBinding
import com.example.headhunterapp.presentation.ViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EntryPasswordFragment : Fragment() {

    private lateinit var binding: FragmentEntryPasswordBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        lifecycleScope.launch {
            try {
                var email = bundle?.getString(EntryEmailFragment.EMAIL)
                Log.d("email", "$email")
                binding.email.text = email.toString()
                setupListeners()
                checkPassword()
                tabButton()
                delay(1000)
                if (viewModel.allLikeVacancy.value.isNotEmpty()) {
                    binding.redTab.visibility = View.VISIBLE
                    binding.textRed.visibility = View.VISIBLE
                    binding.textRed.text = viewModel.allLikeVacancy.value.size.toString()
                    Log.d("vacancyListMut", "${viewModel.allLikeVacancy}")
                }
                Log.d("vacancyListMut2", "${viewModel.allLikeVacancy.value}")
            } catch (e: Throwable) {
                Log.d("catch", "$e")
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }

    private fun setupListeners() {
        try {
            lifecycleScope.launch {
                // Получение ссылок на EditText
                val inputFields = listOf(
                    binding.passwordInput1,
                    binding.passwordInput2,
                    binding.passwordInput3,
                    binding.passwordInput4
                )
                // Обработчик ввода для всех полей
                for (i in inputFields.indices) {
                    val currentEditText = inputFields[i]
                    val nextEditText = if (i < inputFields.size - 1) inputFields[i + 1] else null

                    currentEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(editable: Editable?) {
                            if (editable != null && editable.length == 1) {
                                currentEditText.clearFocus()
                                nextEditText?.requestFocus()
                            }
                        }
                    })

                    // Переход на следующее поле при вводе символа
                    currentEditText.setOnKeyListener { _, keyCode, event ->
                        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                            if (currentEditText.text.isNullOrEmpty()) {
                                val prevIndex = i - 1
                                if (prevIndex >= 0) {
                                    inputFields[prevIndex].requestFocus()
                                    inputFields[prevIndex].setText("")
                                }
                            } else {
                                currentEditText.setText("") // Очищаем текущее поле
                            }
                            true
                        } else {
                            false // Позволяем обрабатывать другие события клавиатуры
                        }
                    }
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }

    private fun tabButton() {
        lifecycleScope.launch {
            binding.home.setOnClickListener {
                findNavController().navigate(
                    R.id.action_entryPasswordFragment_to_homeFragment
                )
            }
            binding.favourites.setOnClickListener {
                findNavController().navigate(
                    R.id.action_entryPasswordFragment_to_favoritesFragment
                )
            }
            binding.send.setOnClickListener {
                findNavController().navigate(
                    R.id.action_entryPasswordFragment_to_responseFragment
                )
            }
            binding.massage.setOnClickListener {
                findNavController().navigate(
                    R.id.action_entryPasswordFragment_to_massageFragment
                )
            }
            binding.profile.setOnClickListener {
                findNavController().navigate(
                    R.id.action_entryPasswordFragment_to_entryEmailFragment
                )
            }
        }
    }

    private fun checkPassword() { //заглушка, здесь сверяем пароль, который отправлял сервер и напечатал пользователь
        lifecycleScope.launch {
            delay(10000)
            Toast.makeText(requireContext(), "Пароль верный!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.action_entryPasswordFragment_to_homeFragment
            )
        }

    }
}