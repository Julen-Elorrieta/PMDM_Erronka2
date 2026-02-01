package com.example.elormovpmdm.ui.meetings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.elormovpmdm.databinding.AddMeetingDialogBinding
import com.example.elormovpmdm.databinding.FragmentMeetingsBinding
import com.example.elormovpmdm.domain.model.CreateMeetingRequest
import com.example.elormovpmdm.domain.model.Meeting
import com.example.elormovpmdm.domain.model.User
import com.example.elormovpmdm.ui.meetings.adapter.MeetingsAdapter
import com.example.elormovpmdm.ui.meetings.addAdapter.AddDialogAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MeetingsFragment : Fragment() {
    
    private var _binding: FragmentMeetingsBinding? = null
    private val binding get() = _binding!!
    private val meetingsViewModel: MeetingsViewModel by viewModels()
    private lateinit var meetingsAdapter: MeetingsAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeetingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }
    
    private fun initComponents() {
        meetingsAdapter = MeetingsAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvMeetins.layoutManager = GridLayoutManager(context, 1)
        binding.rvMeetins.adapter = meetingsAdapter
        
        binding.btnAdd.setOnClickListener { 
            initDialog()
        }
    }
    
    private fun onItemSelected(meeting: Meeting) {
        
    }
    
    private fun initUI() {
        lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                meetingsViewModel.meetings.collect { 
                    meetingsAdapter.updateList(it)
                }
            }
        }
    }
    
    private fun initDialog() {
        val dialogBinding = AddMeetingDialogBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Añadir reunion")
            .setView(dialogBinding.root)
            .create()
        
        var selectedUser: User? = null
        
        val addDialogAdapter: AddDialogAdapter = AddDialogAdapter(onMeetingUserSelected = { user ->
            selectedUser = user
        })
        dialogBinding.rvUsers.layoutManager = GridLayoutManager(context, 1)
        dialogBinding.rvUsers.adapter = addDialogAdapter
        addDialogAdapter.updateList(meetingsViewModel.users.value)
        
        val centerNames: List<String> = meetingsViewModel.centers.value.map { 
            it.NOM
        }
        val centersAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, centerNames)
        dialogBinding.autoCompleteTextView.setAdapter(centersAdapter)
        
        var selectedDateMillis: Long = 0
        
        dialogBinding.etDate.setOnClickListener { 
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccione una fecha")
                .build()
            
            datePicker.show(childFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener { timestamp ->
                selectedDateMillis = timestamp
                dialogBinding.etDate.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(
                    Date(timestamp)
                ))
            }
        }
        
        var selectedHour: Int = 0
        var selectedMinute: Int = 0
        
        dialogBinding.etHour.setOnClickListener { 
            val timePicker = MaterialTimePicker.Builder()
                .setTitleText("Seleccione una hora")
                .setHour(0)
                .setMinute(0)
                .build()
            
            timePicker.show(childFragmentManager, "timePicker")
            timePicker.addOnPositiveButtonClickListener {
                selectedHour = timePicker.hour
                selectedMinute = timePicker.minute
                dialogBinding.etHour.setText(String.format("%02d:%02d", timePicker.hour, timePicker.minute))
            }
        }
        
        dialogBinding.btnAdd.setOnClickListener {
            val centro = meetingsViewModel.centers.value.find { center ->
                center.NOM.equals(dialogBinding.autoCompleteTextView.text)
            }
            val id_centro = centro?.CCEN
            val dateMillis = selectedDateMillis
            val hourMillis = selectedHour * 3600000L
            val minuteMillis = selectedMinute * 60000L
            
            val fecha = dateMillis + hourMillis + minuteMillis
            val isoFecha = DateTimeFormatter.ISO_INSTANT
                .format(Instant.ofEpochMilli(fecha))
                    
            if (selectedUser == null || centro == null || dialogBinding.etDate.text == null || dialogBinding.etHour.text == null) {
                return@setOnClickListener
            } else {
                val estado = if (meetingsViewModel.user!!.tipoId == 4) "PENDIENTE" else "ACEPTADA"

                val request = CreateMeetingRequest(
                    estado = estado,
                    aula = dialogBinding.etClassroom.toString(),
                    fecha = isoFecha,
                    id_centro = id_centro,
                    usersByAlumnoId = selectedUser,
                    usersByProfesorId = meetingsViewModel.user!!
                )
                meetingsViewModel.createMeeting(request)
            }
        }
        
        dialog.show()
    }
}