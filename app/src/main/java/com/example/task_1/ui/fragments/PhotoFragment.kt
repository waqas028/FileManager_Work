package com.example.task_1.ui.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.task_1.R
import com.example.task_1.databinding.FragmentPhotoBinding
import com.example.task_1.extension.cropImage
import com.example.task_1.ui.activity.CameraPreviewActivity
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoFragment internal constructor() : Fragment() {
    private var _fragmentPhotoBinding: FragmentPhotoBinding? = null
    private val fragmentPhotoBinding get() = _fragmentPhotoBinding!!
    private var imageUri: Uri? = null
    private val mainViewModel : MainViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       _fragmentPhotoBinding = FragmentPhotoBinding.inflate(inflater, container, false)
        return fragmentPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments ?: return
        val resource = args.getInt(FILE_POSITION_KEY,0)
        var imageList = (activity as CameraPreviewActivity).imageUriList
        imageList = imageList.reversed().toMutableList()
        imageUri = imageList[resource].imageUri
        Log.i(TAG, "onViewCreated: $resource  //  ${imageList.size}  $imageUri")
        fragmentPhotoBinding.cropImageView.setImageUriAsync(imageUri)

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.buttonClicked.collectLatest{
                Log.i(TAG, "onViewCreated: $it  ${imageList.size}")
                if(it == 1){
                    val bitmap = fragmentPhotoBinding.cropImageView.croppedImage
                    Log.i(TAG, "onViewCreated: ${imageList[resource]}  $bitmap")
                    if (bitmap != null) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            cropImage(bitmap,imageList[resource].currentTimeSession)
                        }else{
                            cropImage(bitmap, imageList[resource].currentTimeSession)
                        }
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) { // `true` indicates the callback consumes the event by default
                override fun handleOnBackPressed() {
                    // Perform your desired action on back press
                    Log.i(TAG, "handleOnBackPressed: back press button")
                    (activity as CameraPreviewActivity).imageUriList.clear()
                    Navigation.findNavController(requireActivity(), R.id.fragment_container).navigateUp()
                }
            }
        )
    }

    companion object {
        const val TAG = "PhotoFragInfo"
        private const val FILE_POSITION_KEY = "file_position"
        fun create(position: Int) = PhotoFragment().apply {
            arguments = Bundle().apply {
                putInt(FILE_POSITION_KEY, position)
            }
        }
    }
}