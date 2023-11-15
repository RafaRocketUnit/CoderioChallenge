package com.rafarocket.coderiochallenge.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import com.rafarocket.coderiochallenge.R
import com.rafarocket.coderiochallenge.databinding.FragmentProfileBinding
import com.rafarocket.coderiochallenge.ui.AppViewModel
import com.rafarocket.coderiochallenge.ui.ViewStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerview: CarouselRecyclerview = binding.recyclerview
        val errorMessage: TextView = binding.errorMessage
        viewModel.fetchPopularPerson()

        lifecycleScope.launch {
                viewModel.useCases.collect {
                    when (it) {
                        is ViewStates.PopularPersonResult -> {
                            val profileAdapter = ProfileAdapter(it.popularPerson.results)
                            errorMessage.visibility = View.GONE
                            recyclerview.apply {
                                visibility = View.VISIBLE
                                adapter = profileAdapter
                                set3DItem(true)
                                setAlpha(true)
                                setInfinite(false)
                            }
                        }

                        is ViewStates.ErrorUi -> {
                            recyclerview.visibility = View.GONE
                            errorMessage.apply {
                                visibility = View.VISIBLE
                                text = it.error.status_message
                            }

                        }

                        is ViewStates.Idle -> {
                            recyclerview.visibility = View.GONE
                            errorMessage.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.message_loading)
                            }
                        }

                        else -> {
                            recyclerview.visibility = View.GONE
                            errorMessage.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.message_loading)
                            }
                        }
                    }
                }

        }
        return root
    }

}