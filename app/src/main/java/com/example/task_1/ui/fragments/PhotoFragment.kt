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
import androidx.navigation.Navigation
import com.canhub.cropper.CropImageView
import com.example.task_1.R
import com.example.task_1.databinding.FragmentPhotoBinding
import com.example.task_1.model.TempImage
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel

class PhotoFragment internal constructor() : Fragment() {
    private var _fragmentPhotoBinding: FragmentPhotoBinding? = null
    private val fragmentPhotoBinding get() = _fragmentPhotoBinding!!
    private var imageUri: Uri? = null
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var aspectRatio : Rect
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
        fragmentPhotoBinding.cropImageView.guidelines=CropImageView.Guidelines.ON

        fragmentPhotoBinding.cropImageView.setOnSetCropOverlayReleasedListener { rect ->
            Log.i(TAG, "onCropOverlayReleased: $rect")
            Constant.cropImageList[resource+1] = TempImage(
                imageList[resource+1]?.id ?: resource,
                imageList[resource+1]?.imageUri!!,
                imageList[resource+1]?.currentTimeSession.orEmpty(),
                rect
            )
        }

//        fragmentPhotoBinding.doneButton.setOnClickListener {
//            Log.i(TAG, "onViewCreated: Done Button Click: ${Constant.cropImageList.size}   //   ${Constant.cropImageList}")
//            Constant.cropImageList.forEach{
//                /*val dirName = "Crop_Directory/Crop_${it.value.currentTimeSession}"
//                val filename = "Crop_${System.currentTimeMillis()}.jpg"
//                val rootDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName)
//                if (!rootDirectory.exists()) {
//                    rootDirectory.mkdirs()
//                }
//                val imageFile = File(rootDirectory, filename)*/
////                val intent = Intent("android.intent.action.CROP")
////                intent.setDataAndType(it.value.imageUri, "image/*")
////                val aspectX = Constant.cropImageList[resource]?.rect?.width() // Replace with your desired aspect ratio values
////                val aspectY = Constant.cropImageList[resource]?.rect?.height()
////                intent.putExtra("aspectX", aspectX)
////                intent.putExtra("aspectY", aspectY)
////                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
////                startActivityForResult(intent, CROPPING_REQUEST_CODE)
//
//
////                val bitmap = BitmapFactory.decodeFile(it.value.imageUri.path)
////                val imageWidth = bitmap.width
////                val imageHeight = bitmap.height
////                // Calculate cropping region based on aspect ratio
////                val cropWidth = (imageHeight * 3) / 2
////                val cropHeight = (imageWidth * 3) / 2
////
////                val startX = (imageWidth - cropWidth) / 2
////                val startY = (imageHeight - cropHeight) / 2
////
////                val cropRect = Rect(startX, startY, startX + cropWidth, startY + cropHeight)
////                Log.i(TAG, "onViewCreated: Done Button Click: $cropWidth  /  $cropHeight")
////                val croppedBitmap = Bitmap.createBitmap(cropWidth, cropHeight, bitmap.config)
////                val canvas = Canvas(croppedBitmap)
////
////                val matrix = Matrix()
////                matrix.postTranslate(-startX.toFloat(), -startY.toFloat())
////                canvas.drawBitmap(bitmap, matrix, null)
////                cropImage(croppedBitmap,it.value.currentTimeSession)
////                Log.i(TAG, "onViewCreated: Done Button Click:: $croppedBitmap  //  $it")
////                bitmap.recycle() // free memory
//
//                val originalBitmap = BitmapFactory.decodeFile(it.value.imageUri.path)
//                val cropRect = it.value.rect
//                val croppedBitmap = Bitmap.createBitmap(
//                    originalBitmap,
//                    cropRect?.left!!,
//                    cropRect.top,
//                    cropRect.width(),
//                    cropRect.height()
//                )
//                cropImage(croppedBitmap,it.value.currentTimeSession){
//                    Log.i(TAG, "onViewCreated: image Crop Saved ${it.value.id}")
//                }
//                Log.i(TAG, "onViewCreated: Done Button Click:: $croppedBitmap  //  $it")
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            mainViewModel.buttonClicked.collectLatest {
//                Log.i(TAG, "onViewCreated: collect done button: $it  ${imageList.size}")
//                if (it == 1) {
//                    val bitmap = fragmentPhotoBinding.cropImageView.croppedImage
//                    Log.i(TAG, "onViewCreated: collect done button: ${imageList[resource]}  $bitmap")
//                    if (bitmap != null) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            cropImage(bitmap, imageList[resource]?.currentTimeSession){}
//                        } else {
//                            cropImage(bitmap, imageList[resource]?.currentTimeSession){}
//                        }
//                    }
//                }
//            }
//        }

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