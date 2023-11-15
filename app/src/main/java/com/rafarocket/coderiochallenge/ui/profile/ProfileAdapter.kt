package com.rafarocket.coderiochallenge.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafarocket.coderiochallenge.R
import com.rafarocket.coderiochallenge.baseImageUrl
import com.rafarocket.coderiochallenge.domain.model.Person

class ProfileAdapter(private val persons: List<Person>): RecyclerView.Adapter<ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)

        return ProfileViewHolder(view)
    }

    override fun getItemCount() = persons.size


    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val person = persons[position]

        holder.apply {
            profileImage.load( baseImageUrl + person.profile_path)
            profileName.text = person.name
            knownFor.text = getMovieTitles(person)
        }

    }

    private fun getMovieTitles(person: Person): String {
        var result = ""
        for (movie in person.known_for) {
            result = result + movie.title + "\n"
        }
        return result
    }
}