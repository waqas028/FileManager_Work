package com.example.task_1.ui.fragments

import android.graphics.Rect
import android.net.Uri
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
import com.canhub.cropper.CropImageView
import com.example.task_1.R
import com.example.task_1.databinding.FragmentPhotoBinding
import com.example.task_1.model.TempImage
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PhotoFragment internal constructor() : Fragment() {
    private var _fragmentPhotoBinding: FragmentPhotoBinding? = null
    private val fragmentPhotoBinding get() = _fragmentPhotoBinding!!
    private var imageUri: Uri? = null
    private val mainViewModel : MainViewModel by activityViewModels()
    private var resource = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _fragmentPhotoBinding = FragmentPhotoBinding.inflate(inflater, container, false)
        return fragmentPhotoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments ?: return
        resource = args.getInt(FILE_POSITION_KEY, 0)
        val imageList = Constant.cropImageList
        imageUri = imageList[resource+1]?.imageUri
        Log.i(TAG, "onViewCreated: $resource  //  ${imageList.size}  $imageUri   ${Constant.cropImageList}  //  Rect: ${imageList[resource]?.rect}")

        fragmentPhotoBinding.cropImageView.setImageUriAsync(imageUri)
        fragmentPhotoBinding.cropImageView.cropRect = imageList[resource+1]?.rect
        fragmentPhotoBinding.cropImageView.guidelines = CropImageView.Guidelines.ON

        fragmentPhotoBinding.cropImageView.viewTreeObserver.addOnGlobalLayoutListener {
            val visibleRect = Rect()
            fragmentPhotoBinding.cropImageView.getLocalVisibleRect(visibleRect)
            Log.i(TAG, "onViewCreated: Rect-> $visibleRect  ${visibleRect.width()}  ${visibleRect.height()}")
            /*val cropBitmap = fragmentPhotoBinding.cropImageView.getCroppedImage()
            Constant.cropImageList[resource+1] = TempImage(
                imageList[resource+1]?.id ?: resource,
                imageUri ?: Uri.parse(""),
                imageList[resource+1]?.currentTimeSession.orEmpty(),
                visibleRect,
                cropBitmap
            )*/
        }

        viewLifecycleOwner.lifecycleScope.launch{
            delay(500)
            val cropBitmap = fragmentPhotoBinding.cropImageView.getCroppedImage()
            Constant.cropImageList[resource+1] = TempImage(
                imageList[resource+1]?.id ?: resource,
                imageUri ?: Uri.parse(""),
                imageList[resource+1]?.currentTimeSession.orEmpty(),
                imageList[resource+1]?.rect,
                cropBitmap
            )
            Log.i(TAG, "onCropOverlayReleased-> Bitmap Cropped-> ${resource+1} ${Constant.cropImageList}")
        }

        /*fragmentPhotoBinding.cropImageView.setOnCropWindowChangedListener {
            val cropBitmap = fragmentPhotoBinding.cropImageView.getCroppedImage()
            Constant.cropImageList[resource+1] = TempImage(
                imageList[resource+1]?.id ?: resource,
                imageUri ?: Uri.parse(""),
                imageList[resource+1]?.currentTimeSession.orEmpty(),
                imageList[resource+1]?.rect,
                cropBitmap
            )
            Log.i(TAG, "onCropOverlayReleased-> onViewCreated: ${Constant.cropImageList}")
        }*/

        fragmentPhotoBinding.cropImageView.setOnSetCropOverlayReleasedListener { rect ->
            Log.i(TAG, "onCropOverlayReleased: ${resource+1}   -> $rect  //  ${Constant.cropImageList}")
            val cropBitmap = fragmentPhotoBinding.cropImageView.getCroppedImage()
            Constant.cropImageList[resource+1] = TempImage(
                imageList[resource+1]?.id ?: resource,
                imageList[resource+1]?.imageUri!!,
                imageList[resource+1]?.currentTimeSession.orEmpty(),
                rect,
                cropBitmap
            )
            Log.i(TAG, "onCropOverlayReleased: ${requireContext().hashCode()}  ${Constant.cropImageList}")
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) { // `true` indicates the callback consumes the event by default
                override fun handleOnBackPressed() {
                    // Perform your desired action on back press
                    Log.i(TAG, "handleOnBackPressed: back press button")
                    mainViewModel.cameraTempImageList.value = mutableListOf()
                    Constant.cropImageList.clear()
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigateUp()
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