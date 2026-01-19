package com.example.elormovpmdm.ui.timetable.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.databinding.UserCardBinding
import com.example.elormovpmdm.domain.User

class TimetableViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UserCardBinding.bind(view)
    
    fun render(user: User, onItemSelected: (User) -> Unit) {
        binding.tvName.setText(user.name)
        binding.tvEmail.setText(user.email)
    }
}