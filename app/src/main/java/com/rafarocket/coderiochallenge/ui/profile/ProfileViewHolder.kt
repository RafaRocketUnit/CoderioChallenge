package com.rafarocket.coderiochallenge.ui.profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rafarocket.coderiochallenge.R

class ProfileViewHolder(view: View): ViewHolder(view) {
    val profileImage: ImageView = itemView.findViewById(R.id.profile_image)
    val profileName: TextView = itemView.findViewById(R.id.profile_name)
    val knownFor: TextView = itemView.findViewById(R.id.profile_knownfor)

}