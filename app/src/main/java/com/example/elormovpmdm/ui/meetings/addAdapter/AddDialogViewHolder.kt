package com.example.elormovpmdm.ui.meetings.addAdapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.UserCardBinding
import com.example.elormovpmdm.domain.model.User

class AddDialogViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UserCardBinding.bind(view)
    
    fun render(user: User, isSelected: Boolean,  onMeetingUserSelected: (User) -> Unit) {
        binding.tvName.text = "${user.nombre} ${user.apellidos}"
        binding.tvEmail.text = user.email
        
        binding.sivProfile.visibility = View.INVISIBLE
        binding.btnBack.visibility = View.INVISIBLE
        
        binding.root.setCardBackgroundColor(
            ContextCompat.getColor(itemView.context, if(isSelected) R.color.orange else R.color.divider)
        )
        
        binding.root.setOnClickListener { 
            onMeetingUserSelected(user)
        }
    }
}