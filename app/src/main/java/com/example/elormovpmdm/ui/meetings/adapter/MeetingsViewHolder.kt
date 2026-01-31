package com.example.elormovpmdm.ui.meetings.adapter

import android.text.Selection
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.databinding.MeetingCardBinding
import com.example.elormovpmdm.domain.model.Meeting
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter


class MeetingsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = MeetingCardBinding.bind(view)
    
    fun render(meeting: Meeting, onItemSelected: (Meeting) -> Unit) {
        val studentName = meeting.usersByAlumnoId.nombre
        val classroom = meeting.aula
        
        val fullDate = meeting.fecha
        val instant = Instant.parse(fullDate)
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val onlyDate = localDateTime.date
        val onlyTime = localDateTime.time
        
        binding.tvMeetingTitle.text = studentName
        binding.tvClassroom.text = classroom
        binding.tvDate.text = onlyDate.toString()
        binding.tvHour.text = onlyTime.toString()
        
        binding.root.setOnClickListener { 
            onItemSelected(meeting)
        }
    }
}