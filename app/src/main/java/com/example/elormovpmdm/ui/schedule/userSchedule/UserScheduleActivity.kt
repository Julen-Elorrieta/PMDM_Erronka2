package com.example.elormovpmdm.ui.schedule.userSchedule

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.ActivityScheduleBinding
import com.example.elormovpmdm.domain.model.Schedule
import com.example.elormovpmdm.domain.model.UserResponse
import kotlinx.coroutines.launch

class UserScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private val userScheduleViewModel: UserScheduleViewModel by viewModels()
    private var schedules: List<Schedule> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.schedule_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userScheduleViewModel.schedules.collect {
                    updateList(it)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun updateList(listUpdated: List<Schedule>) {
        schedules = listUpdated
    }

}