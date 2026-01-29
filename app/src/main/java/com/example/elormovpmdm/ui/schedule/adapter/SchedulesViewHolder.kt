package com.example.elormovpmdm.ui.schedule.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.databinding.UserCardBinding
import com.example.elormovpmdm.domain.model.User

class SchedulesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UserCardBinding.bind(view)
    
    fun render(user: User, onItemSelected: (User) -> Unit) {
        val name: String = user.nombre ?: "Sin nombre"
        val surname: String = user.apellidos ?: ""

        binding.tvName.text = "$name $surname"
        binding.tvEmail.text = user.email
        
        binding.root.setOnClickListener { 
            onItemSelected(user)
        }
    }
}