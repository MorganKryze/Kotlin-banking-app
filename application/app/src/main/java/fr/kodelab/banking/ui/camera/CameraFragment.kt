package fr.kodelab.banking.ui.camera

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.hardware.Camera
import android.view.LayoutInflater
import android.os.Bundle
import android.provider.MediaStore
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.kodelab.banking.databinding.FragmentCameraBinding
import android.widget.Toast
import androidx.core.content.ContextCompat

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var camera: Camera

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCapture.setOnClickListener {
            capturePicture()
        }
    }

    private fun requestCameraPermission() {
        val permission = arrayOf(Manifest.permission.CAMERA)
        requestPermissions(permission, CAMERA_REQUEST_CODE)
    }

    private fun capturePicture() {
        Toast.makeText(requireContext(), "Image captured", Toast.LENGTH_SHORT).show()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        requireActivity().startActivityFromChild(requireActivity(), intent, requestCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 1
    }
}
