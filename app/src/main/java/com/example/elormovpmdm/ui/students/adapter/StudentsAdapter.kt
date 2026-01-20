package com.example.elormovpmdm.ui.students.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.R
import com.example.elormovpmdm.domain.User

class StudentsAdapter(
    private var userList: List<User> = emptyList(),
    private val onItemSelected: (User) -> Unit
): RecyclerView.Adapter<StudentsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentsViewHolder {
        return StudentsViewHolder(
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
        holder: StudentsViewHolder,
        position: Int
    ) {
        val itemsViewModel = userList[position]
        holder.render(userList[position], onItemSelected)
    }
    
    override fun getItemCount(): Int {
        return userList.size
    }
}