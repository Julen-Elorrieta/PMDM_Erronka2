package com.example.elormovpmdm.ui.schedule

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.FragmentSchedulesBinding
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.Schedule
import com.example.elormovpmdm.ui.schedule.userSchedule.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    
    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding: FragmentSchedulesBinding? = null
    private val binding get() = _binding!!
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var schedules: List<Schedule> = emptyList()
    private var currentIndex: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchedulesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val daysOfWeek by lazy {
            listOf(
                ContextCompat.getString(requireContext(), R.string.monday),
                ContextCompat.getString(requireContext(), R.string.tuesday),
                ContextCompat.getString(requireContext(), R.string.wednesday),
                ContextCompat.getString(requireContext(), R.string.thursday),
                ContextCompat.getString(requireContext(), R.string.friday),
            )
        }
        initComponents(daysOfWeek)
        initUI(daysOfWeek)
    }

    private fun initComponents(daysOfWeek: List<String>) {
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
                scheduleViewModel.schedules.collect { schedules ->
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
            it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
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
        return ContextCompat.getColor(requireContext(), colorRes)
    }
}