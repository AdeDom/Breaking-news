package com.adedom.breakingnews.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adedom.breakingnews.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
    }

    private fun setUpView() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.generalFragment,
                R.id.businessFragment,
                R.id.entertainmentFragment,
                R.id.healthFragment,
                R.id.scienceFragment,
                R.id.sportsFragment,
                R.id.technologyFragment,
            ),
            drawer_layout
        )

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(fragment.findNavController(), appBarConfiguration)
        navigationView.setupWithNavController(fragment.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return fragment.findNavController().navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

}
