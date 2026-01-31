package com.example.elormovpmdm.ui.schedule.userSchedule

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elormovpmdm.BaseActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.ActivityScheduleBinding
import com.example.elormovpmdm.domain.model.Schedule
import com.example.elormovpmdm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserScheduleActivity : BaseActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private var schedules: List<Schedule> = emptyList()
    private val userScheduleViewModel: UserScheduleViewModel by viewModels()
    private var currentIndex: Int = 0
        //Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2

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

        val daysOfWeek by lazy {
            listOf(
                getString(R.string.monday),
                getString(R.string.tuesday),
                getString(R.string.wednesday),
                getString(R.string.thursday),
                getString(R.string.friday),
            )
        }
        
        initComponents(daysOfWeek)
        initUI(daysOfWeek)
    }

    private fun initComponents(daysOfWeek: List<String>) {
        binding.btnQuit.setOnClickListener { 
            finish()
        }
        
        binding.btnBack.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                paintSchedule(daysOfWeek, currentIndex)
            } else {
                currentIndex = daysOfWeek.size - 1
                paintSchedule(daysOfWeek, currentIndex)
            }

            binding.ivDay.text = daysOfWeek[currentIndex]
        }
        binding.btnForward.setOnClickListener {
            if (currentIndex < daysOfWeek.size - 1) {
                currentIndex++
                paintSchedule(daysOfWeek, currentIndex)
            } else {
                currentIndex = 0
                paintSchedule(daysOfWeek, currentIndex)
            }

            binding.ivDay.text = daysOfWeek[currentIndex]
        }
    }

    private fun initUI(daysOfWeek: List<String>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userScheduleViewModel.schedules.collect { schedules ->
                    Log.i("GVA", "Datos recibidos: ${schedules.size}")
                    if (schedules.isNotEmpty()) {
                        updateList(schedules)
                        paintSchedule(daysOfWeek, currentIndex)
                    }
                }
            }
        }

        binding.ivDay.text = daysOfWeek[currentIndex]
    }

    private fun updateList(listUpdated: List<Schedule>) {
        schedules = listUpdated
    }

    private fun paintSchedule(daysOfWeek: List<String>, index: Int) {
        val textViews = listOf(
            binding.tvFirstHour,
            binding.tvSecondHour,
            binding.tvThirdHour,
            binding.tvFourthHour,
            binding.tvFifthHour,
            binding.tvSixthHour
        )

        textViews.forEach {
            it.text = ""
            it.setBackgroundColor(getColor(R.color.white))
        }

        schedules.forEach { schedule ->
            if (schedule.dia.equals(daysOfWeek[index].uppercase())) {
                val targetTextView = when (schedule.hora) {
                    1 -> binding.tvFirstHour
                    2 -> binding.tvSecondHour
                    3 -> binding.tvThirdHour
                    4 -> binding.tvFourthHour
                    5 -> binding.tvFifthHour
                    6 -> binding.tvSixthHour
                    else -> null
                }

                targetTextView?.apply {
                    text = schedule.modulos.nombre
                    setBackgroundColor(getModuloColor(schedule.modulos.id))
                }

            }
        }
    }

    private fun getModuloColor(modulo_id: Int): Int {
        val colorRes = when(modulo_id) {
            1 -> R.color.mod1
            2 -> R.color.mod2
            3 -> R.color.mod3
            4 -> R.color.mod4
            5 -> R.color.mod5
            6 -> R.color.mod6
            7 -> R.color.mod7
            8 -> R.color.mod8
            9 -> R.color.mod9
            10 -> R.color.mod10
            11 -> R.color.mod11
            12 -> R.color.mod12
            13 -> R.color.mod13
            14 -> R.color.mod14
            15 -> R.color.mod15
            16 -> R.color.mod16
            17 -> R.color.mod17
            18 -> R.color.mod18
            19 -> R.color.mod19
            else -> {R.color.white}
        }
        return getColor(colorRes)
    }
}