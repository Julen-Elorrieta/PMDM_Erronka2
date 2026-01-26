package com.example.elormovpmdm.ui.timetable.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.databinding.UserCardBinding
import com.example.elormovpmdm.domain.model.UserResponse

class TimetableViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UserCardBinding.bind(view)
    
    fun render(user: UserResponse, onItemSelected: (UserResponse) -> Unit) {
        val name: String = user.nombre ?: "Sin nombre"
        val surname: String = user.apellidos ?: ""

        binding.tvName.text = "$name $surname"
        binding.tvEmail.text = user.email
    }
}