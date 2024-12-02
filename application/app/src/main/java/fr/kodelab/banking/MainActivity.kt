package fr.kodelab.banking

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.kodelab.banking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Observe the current destination and update the visibility of the BottomNavigationView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.landingFragment, R.id.authFragment -> {
                    navView.visibility = View.GONE
                    supportActionBar?.hide() // Hide the ActionBar for landing and auth pages
                }
                else -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.show() // Show the ActionBar for other pages
                }
            }
        }

        // Check if the user is authenticated and navigate accordingly
        if (isUserAuthenticated()) {
            navController.navigate(R.id.navigation_home)
        } else {
            navController.navigate(R.id.landingFragment)
        }
    }

    private fun isUserAuthenticated(): Boolean {
        // Implement your authentication check logic here
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
