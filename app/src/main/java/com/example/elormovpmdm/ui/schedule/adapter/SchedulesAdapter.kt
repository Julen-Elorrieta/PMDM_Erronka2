package com.example.elormovpmdm.ui.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.R
import com.example.elormovpmdm.domain.model.User

class SchedulesAdapter(
    private var userList: List<User> = emptyList(),
    private val onItemSelected: (User) -> Unit
): RecyclerView.Adapter<SchedulesViewHolder>() {
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SchedulesViewHolder {
        return SchedulesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_card, parent, false)
        )
    }

    fun updateList(listUpdated: List<User>) {
        userList = listUpdated
        notifyDataSetChanged()
    }
    
    override fun onBindViewHolder(
        holder: SchedulesViewHolder,
        position: Int
    ) {
        holder.render(userList[position], onItemSelected)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}