package com.example.headhunterapps.presentation.entryfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.headhunterapps.R
import com.example.headhunterapps.databinding.FragmentEntryEmailBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryEmailFragment : Fragment() {
    private lateinit var binding: FragmentEntryEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEntryEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            try {
                var editTextBind = binding.editText
                val textWatcher = object : TextWatcher {
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
                    override fun afterTextChanged(s: Editable?) {
                    }
                }
                editTextBind.addTextChangedListener(textWatcher)
                button(editTextBind)
                tabButton()
            } catch (e: Exception) {
            }
        }
    }

    private fun button(editTextBind: TextInputEditText) {
        lifecycleScope.launch {
            try {
                binding.nextButton.setOnClickListener {
                    // Привязываем слушатель к полю ввода
                    // Проверка корректности введенных данных
                    if (editTextBind.text.toString().isNotBlank()) {
                        Toast.makeText(
                            requireContext(),
                            "Введите почту не корректно!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        checkEmail(editTextBind.text.toString())
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Введите почту корректно!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                binding.entryWithPassword.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Входим по паролю. Заглушка.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                binding.searchButton.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Вы не являетесь работодателем, Вам вход недоступен. Заглушка.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun tabButton() {
        lifecycleScope.launch {
            try {
                binding.home.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_entryEmailFragment_to_homeFragment
                    )
                }
                binding.favourites.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_entryEmailFragment_to_favoritesFragment
                    )
                }
                binding.send.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_entryEmailFragment_to_responseFragment
                    )
                }
                binding.massage.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_entryEmailFragment_to_massageFragment
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun checkEmail(item: String) { // заглушка, все данные(код) отправляются через сервер
        try {
            lifecycleScope.launch {
                val fragment = EntryEmailFragment()
                val bundle = Bundle()
                bundle.putString(EMAIL, item)
                fragment.arguments = bundle
                Toast.makeText(requireContext(), "Пароль отправлен на почту!", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(
                    R.id.action_entryEmailFragment_to_entryPasswordFragment,
                    bundle
                )
            }
        } catch (e: Throwable) {
        }
    }

    companion object {
        const val EMAIL = "email"
    }
}