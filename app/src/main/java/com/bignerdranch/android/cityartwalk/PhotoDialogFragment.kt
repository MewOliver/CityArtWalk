package com.bignerdranch.android.cityartwalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bignerdranch.android.cityartwalk.databinding.FragmentPhotoDialogBinding
import getScaledBitmap
import java.io.File

class PhotoDialogFragment : DialogFragment() {

    private var _binding: FragmentPhotoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the photo file name from arguments
        val photoFileName = arguments?.getString(ARG_PHOTO_FILE_NAME)

        // Load and display the photo
        photoFileName?.let {
            val photoFile = File(requireContext().applicationContext.filesDir, it)
            if (photoFile.exists()) {
                val scaledBitmap = getScaledBitmap(photoFile.path, 800, 800) // Load scaled bitmap
                binding.photoImageView.setImageBitmap(scaledBitmap)
            }
        }

        binding.closeButton.setOnClickListener {
            dismiss() // Dismiss the dialog when the button is pressed
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PHOTO_FILE_NAME = "photoFileName"

        fun newInstance(photoFileName: String): PhotoDialogFragment {
            val fragment = PhotoDialogFragment()
            val args = Bundle().apply {
                putString(ARG_PHOTO_FILE_NAME, photoFileName)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
