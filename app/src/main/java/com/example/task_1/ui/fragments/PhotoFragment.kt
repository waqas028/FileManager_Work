package com.example.task_1.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task_1.databinding.FragmentPhotoBinding
import com.example.task_1.extension.cropImage
import com.example.task_1.ui.activity.CameraPreviewActivity
import com.example.task_1.utils.Common

class PhotoFragment internal constructor() : Fragment() {
    private var _fragmentPhotoBinding: FragmentPhotoBinding? = null
    private val fragmentPhotoBinding get() = _fragmentPhotoBinding!!
    private var imageUri: Uri? = null
   // override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = CropImageView(requireContext())
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
        Log.i(TAG, "onViewCreated: $resource  //  $imageList")
        /*Glide.with(view)
            .load(imageUri)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_photo)
            .into(view as ImageView)*/
        fragmentPhotoBinding.cropImageView.setImageUriAsync(imageUri)
        fragmentPhotoBinding.button.setOnClickListener {
            val bitmap = fragmentPhotoBinding.cropImageView.croppedImage
            fragmentPhotoBinding.cropImageView.setImageBitmap(bitmap)
            if (bitmap != null) {
                cropImage(bitmap, Common.getFilePathFromImageUri(requireContext(), imageUri!!)!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as CameraPreviewActivity).imageUriList.clear()
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