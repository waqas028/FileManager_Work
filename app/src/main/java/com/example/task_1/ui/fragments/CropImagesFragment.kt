package com.example.task_1.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_1.adapter.ImagesAdapter
import com.example.task_1.databinding.FragmentCropImagesBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
class CropImagesFragment : Fragment(), CopyImageProgressListener {
    private var _binding: FragmentCropImagesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    var imagesAdapter : ImagesAdapter = ImagesAdapter(this){
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){binding.backButtonImageview.visibility = View.GONE}
            mainViewModel.getNonEmptyDirectoriesWithFiles("")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCropImagesBinding.inflate(inflater, container, false)
        binding.cropImagesRecyclerView.apply {
            adapter = imagesAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.savedCropImageList.collect{
                Log.i(TAG, "collectCropImageDirList: ${it.size}  //  $it")
                val imagesList = mutableListOf<Media>()
                if(it.isNotEmpty()){
                    for( files in it.indices){
                        if(it[files].name.endsWith(".jpg") || it[files].name.endsWith(".png")){
                            Common.getImageContentUri(requireContext(),it[files])
                                ?.let { it1 -> Media(it1,it[files].name,1) }
                                ?.let { it2 -> imagesList.add(it2) }
                            withContext(Dispatchers.Main){binding.backButtonImageview.visibility = View.VISIBLE}
                        }else{
                            withContext(Dispatchers.Main){binding.backButtonImageview.visibility = View.GONE}
                            imagesList.add(Media(it[files].absolutePath.toUri(),it[files].name,1))
                        }
                    }
                    Log.i(TAG, "collectCropImageDirList: ${imagesList.size}  //  $imagesList")
                }
                withContext(Dispatchers.Main){
                    imagesAdapter.differ.submitList(imagesList)
                }
            }
        }

        imagesAdapter.stOnItemClickListener{
            binding.backButtonImageview.visibility = View.VISIBLE
            if(!it.name.endsWith(".jpg")  &&  !it.name.endsWith(".png")){
                mainViewModel.getCropImagesList(it.name)
            }
        }

        binding.backButtonImageview.setOnClickListener{
            binding.backButtonImageview.visibility = View.GONE
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.getNonEmptyDirectoriesWithFiles("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        const val TAG = "CropImagesFragInfo"
    }

    override fun onProgressUpdate(progress: Int) {

    }
}