package fr.kodelab.banking.ui.landing

import android.os.Bundle
import fr.kodelab.banking.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import fr.kodelab.banking.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!
    private val landingViewModel: LandingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the text from the ViewModel
        binding.textLanding.text = landingViewModel.text.value

        // Set the image from the ViewModel
        binding.imageLanding.setImageResource(landingViewModel.getImageResource())

        binding.buttonGoToAuth.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_authFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
