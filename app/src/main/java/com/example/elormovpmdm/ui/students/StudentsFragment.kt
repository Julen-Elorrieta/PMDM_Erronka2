package com.example.elormovpmdm.ui.students

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.elormovpmdm.databinding.FragmentStudentsBinding
import com.example.elormovpmdm.databinding.StudentBottomsheetlayoutBinding
import com.example.elormovpmdm.domain.model.User
import com.example.elormovpmdm.ui.students.adapter.StudentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentsFragment : Fragment() {
    
    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!
    private val studentsViewModel: StudentsViewModel by viewModels()
    private lateinit var studentsAdapter: StudentsAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }
    
    private fun initComponents() {
        studentsAdapter = StudentsAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvStudents.layoutManager = GridLayoutManager(context, 1)
        binding.rvStudents.adapter = studentsAdapter
    }
    
    private fun onItemSelected(user: User) {
        val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(requireContext())
        val sheetBinding = StudentBottomsheetlayoutBinding.inflate(layoutInflater)
        
        val name: String = user.nombre
        val surname: String = user.apellidos
        
        sheetBinding.userName.text = "$name $surname"
        sheetBinding.tvUserName.text = user.username
        sheetBinding.userID.text = user.id.toString()
        sheetBinding.userAddress.text = user.direccion
        sheetBinding.userPhone.text = user.telefono1
        sheetBinding.userEmail.text = user.email
        
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }
    
    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                studentsViewModel.users.collect {
                    studentsAdapter.updateList(it)
                }
            }
        }
    }
}