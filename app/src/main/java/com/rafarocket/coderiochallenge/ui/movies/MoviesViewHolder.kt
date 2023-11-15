package com.rafarocket.coderiochallenge.ui.movies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rafarocket.coderiochallenge.R

class MoviesViewHolder(itemView: View): ViewHolder(itemView) {
    val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
    val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
}