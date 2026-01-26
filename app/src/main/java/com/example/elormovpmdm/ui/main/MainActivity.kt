package com.example.elormovpmdm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.elormovpmdm.BaseActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.ActivityMainBinding
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initComponents()
        initUI()
    }
    
    private fun initUI() {
        initNavigation()
    }
    
    private fun initNavigation() {
        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        
        if (SessionManager.currentUser?.tipoId == 4) {
            binding.bottomBar.menu.clear()
            binding.bottomBar.inflateMenu(R.menu.student_bottom_menu)
            navGraph.setStartDestination(R.id.timetableFragment)
        } else {
            binding.bottomBar.menu.clear()
            binding.bottomBar.inflateMenu(R.menu.teacher_bottom_menu)
            navGraph.setStartDestination(R.id.timetableFragment)
        }
        
        navController.graph = navGraph
        binding.bottomBar.setupWithNavController(navController)
        
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.timetableFragment -> binding.tvToolbarTitle.text = getString(R.string.timetable)
                R.id.studentsFragment -> binding.tvToolbarTitle.text = getString(R.string.students)
                R.id.meetingsFragment -> binding.tvToolbarTitle.text = getString(R.string.meetings)
            }
        }
    }
    
    private fun initComponents() {
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}