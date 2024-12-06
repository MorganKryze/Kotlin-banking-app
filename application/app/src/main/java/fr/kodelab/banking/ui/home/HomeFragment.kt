package fr.kodelab.banking.ui.home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.kodelab.banking.databinding.FragmentHomeBinding
import fr.kodelab.banking.db.UserDAO
import android.graphics.*;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import fr.kodelab.banking.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDAO: UserDAO
    private lateinit var plot: XYPlot

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(requireContext())

        // Get the user and set the welcome message
        val user = userDAO.getAllUsers().firstOrNull()
        if (user != null) {
            val welcomeText = "Welcome back, "
            val usernameText = user.name
            val spannable = SpannableString("$welcomeText$usernameText")
            spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), welcomeText.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.textViewWelcome.text = spannable
        }

        // Add any additional setup for the placeholder square if needed
        plot = view.findViewById(R.id.viewGraphPlaceholder);

        val domainLabels = arrayOf(1, 2, 3, 6, 7, 8, 9, 10, 13, 14)
        val series1Numbers = arrayOf(1, 4, 2, 8, 88, 16, 8, 32, 16, 64)

        val series1: XYSeries = SimpleXYSeries(
            series1Numbers.asList(),
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Series 1"
        )

        val series1Format = LineAndPointFormatter(Color.RED, Color.GREEN, null, null)
        plot.addSeries(series1, series1Format)

        plot.legend.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
