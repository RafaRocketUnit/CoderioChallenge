package com.rafarocket.coderiochallenge.ui.movies

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
import com.rafarocket.coderiochallenge.domain.model.Movie
import com.rafarocket.coderiochallenge.databinding.FragmentMoviesBinding
import com.rafarocket.coderiochallenge.ui.AppViewModel
import com.rafarocket.coderiochallenge.ui.ViewStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val topRatedRecyclerview: CarouselRecyclerview = binding.topRatedRecycler
        val popularRecyclerview: CarouselRecyclerview = binding.popularRecycler
        val upComingRecyclerview: CarouselRecyclerview = binding.upComingRecycler

        val titleTopRated: TextView = binding.topRated
        val titleUpComing: TextView = binding.upComing
        val titlePopular: TextView = binding.popular

        val errorMessage: TextView = binding.errorMessage

        viewModel.apply {
            fetchUpComingMovies()
            fetchPopularMovies()
            fetchTopRatedMovies()
        }

        lifecycleScope.launch {
            viewModel.useCases.collect {
                when (it) {
                    is ViewStates.PopularMoviesResult -> {
                        errorMessage.visibility = View.GONE
                        titlePopular.visibility = View.VISIBLE
                        val movieAdapter = MoviesAdapter(it.movies.results
                            ?: emptyMoviesResponse("No Popular Movies")
                        )
                        popularRecyclerview.apply {
                            visibility = View.VISIBLE
                            adapter = movieAdapter
                            set3DItem(true)
                            setAlpha(true)
                            setInfinite(false)
                        }
                    }

                    is ViewStates.UpComingResult -> {
                        errorMessage.visibility = View.GONE
                        titleUpComing.visibility = View.VISIBLE
                        val movieAdapter = MoviesAdapter(it.movies.results
                            ?: emptyMoviesResponse("No Upcoming Movies")
                        )
                        upComingRecyclerview.apply {
                            visibility = View.VISIBLE
                            adapter = movieAdapter
                            set3DItem(true)
                            setAlpha(true)
                            setInfinite(false)
                        }
                    }

                    is ViewStates.TopRatedResult -> {
                        errorMessage.visibility = View.GONE
                        titleTopRated.visibility = View.VISIBLE
                        val movieAdapter = MoviesAdapter(it.movies.results
                            ?: emptyMoviesResponse("No Top Rated Movies")
                        )
                        topRatedRecyclerview.apply {
                            visibility = View.VISIBLE
                            adapter = movieAdapter
                            set3DItem(true)
                            setAlpha(true)
                            setInfinite(false)
                        }
                    }

                    is ViewStates.ErrorUi -> {
                        topRatedRecyclerview.visibility = View.GONE
                        popularRecyclerview.visibility = View.GONE
                        upComingRecyclerview.visibility = View.GONE
                        titleTopRated.visibility = View.GONE
                        titlePopular.visibility = View.GONE
                        titleUpComing.visibility = View.GONE
                        errorMessage.apply {
                            visibility = View.VISIBLE
                            text = it.error.status_message
                        }

                    }

                    is ViewStates.Idle -> {
                        topRatedRecyclerview.visibility = View.GONE
                        popularRecyclerview.visibility = View.GONE
                        upComingRecyclerview.visibility = View.GONE
                        titleTopRated.visibility = View.GONE
                        titlePopular.visibility = View.GONE
                        titleUpComing.visibility = View.GONE

                        errorMessage.apply {
                            visibility = View.VISIBLE
                            text = getString(R.string.message_loading)
                        }
                    }

                    else -> {
                        topRatedRecyclerview.visibility = View.GONE
                        popularRecyclerview.visibility = View.GONE
                        upComingRecyclerview.visibility = View.GONE
                        titleTopRated.visibility = View.GONE
                        titlePopular.visibility = View.GONE
                        titleUpComing.visibility = View.GONE
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

    private fun emptyMoviesResponse(message: String): List<Movie> {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(title = message))
        return movies
    }
}