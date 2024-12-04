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
import fr.kodelab.banking.db.UserDAO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        userDAO = UserDAO(this)

        // Check if the user is authenticated and navigate accordingly
        val user = userDAO.getAllUsers().firstOrNull()
        if (user != null) {
            val lastLoginDate = user.lastLogin?.let { parseDate(it) }
            val currentDate = Date()
            val diffInMinutes = lastLoginDate?.let { (currentDate.time - it.time) / TimeUnit.MINUTES.toMillis(1) }

            if (diffInMinutes != null && diffInMinutes < 15) {
                // Navigate to the home page if the last login is less than 15 minutes
                navController.navigate(R.id.navigation_home)
            } else {
                // Navigate to the landing page if the last login is more than 15 minutes
                navController.navigate(R.id.landingFragment)
            }
        } else {
            // Navigate to the landing page if no user is found
            navController.navigate(R.id.landingFragment)
        }

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
    }

    private fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.parse(dateString)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
