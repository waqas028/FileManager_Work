package com.example.task_1.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_1.adapter.CropDirectoryAdapter
import com.example.task_1.databinding.FragmentCropImagesBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
class CropImagesFragment : Fragment(), CopyImageProgressListener {
    private var _binding: FragmentCropImagesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var cropDirectoryAdapter: CropDirectoryAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCropImagesBinding.inflate(inflater, container, false)
        cropDirectoryAdapter = CropDirectoryAdapter(this)
        binding.cropImagesRecyclerView.apply {
            adapter = cropDirectoryAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.currentFragment.collect{
                Log.i(TAG, "onViewCreated: $it")
                cropDirectoryAdapter.onPageUpdate(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.savedCropImageList.collect{cropFolderList->
                Log.i(TAG, "collectCropImageDirList: ${cropFolderList.size}  //  $cropFolderList")
                /*val imagesList = mutableListOf<Media>()
                if(cropFolderList.isNotEmpty()){
                    val filteredImages = cropFolderList.filter { file ->
                        file.name.endsWith(".jpg") || file.name.endsWith(".jpeg") || file.name.endsWith(".png")
                    }.mapNotNull { file ->
                        withContext(Dispatchers.Main) { binding.backButtonImageview.visibility  = View.VISIBLE }
                        Common.getImageContentUri(requireContext(), file)?.let { uri ->
                            Media(1, uri, file.name, 1)
                        }
                    }
                    imagesList.addAll(filteredImages)
                    val nonImageFiles = cropFolderList.filterNot { file ->
                        file.name.endsWith(".jpg") || file.name.endsWith(".jpeg") || file.name.endsWith(".png")
                    }.map { file ->
                        withContext(Dispatchers.Main) { binding.backButtonImageview.visibility  = View.GONE }
                        Media(1, file.absolutePath.toUri(), file.name, 1)
                    }
                    imagesList.addAll(nonImageFiles)
                    Log.i(TAG, "collectCropImageDirList: ${imagesList.size}  //  $imagesList")
                }*/
                withContext(Dispatchers.Main){
                    cropDirectoryAdapter.differ.submitList(cropFolderList)
                }
            }
        }

        cropDirectoryAdapter.stOnItemClickListener{
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

    override fun onProgressUpdate(progress: Int) {}
    override fun onDeleteOrCopyItemListener() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){binding.backButtonImageview.visibility = View.GONE}
            mainViewModel.getNonEmptyDirectoriesWithFiles("")
        }
    }
}