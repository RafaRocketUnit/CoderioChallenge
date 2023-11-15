package com.rafarocket.coderiochallenge.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafarocket.coderiochallenge.R
import com.rafarocket.coderiochallenge.baseImageUrl
import com.rafarocket.coderiochallenge.domain.model.Movie

class MoviesAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies, parent, false)

        return MoviesViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]

        holder.apply {
            movieImage.load( baseImageUrl + movie.backdrop_path)
            movieTitle.text = movie.title
        }
    }
}