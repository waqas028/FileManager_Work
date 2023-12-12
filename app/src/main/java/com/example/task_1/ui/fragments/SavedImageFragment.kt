@file:Suppress("DEPRECATION")

package com.example.task_1.ui.fragments

import android.app.ProgressDialog
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
import com.example.task_1.adapter.VideosAdapter
import com.example.task_1.databinding.FragmentSavedImageBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SavedImageFragment : Fragment(), CopyImageProgressListener {
    private var _binding: FragmentSavedImageBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    var videosAdapter: VideosAdapter = VideosAdapter(this)
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedImageBinding.inflate(inflater, container, false)
        binding.savedVideoImageRecyclerView.apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectSavedImageVideosListFromStorage()

        videosAdapter.stOnCopyClickListener {
            showDialogue()
        }
    }

    private fun collectSavedImageVideosListFromStorage() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.savedVideoImageList.collect{
                val imagesList = mutableListOf<Media>()
                if(it.isNotEmpty()){
                    for( files in it.indices){
                        if(it[files].name.endsWith(".mp4")){
                            Common.getVideoContentUri(requireContext(),it[files])
                                ?.let { it1 -> Media(it1,it[files].name,1) }
                                ?.let { it2 -> imagesList.add(it2) }
                        }else{
                            Common.getImageContentUri(requireContext(),it[files])
                                ?.let { it1 -> Media(it1,it[files].name,1) }
                                ?.let { it2 -> imagesList.add(it2) }
                        }
                    }
                    Log.i(TAG, "onViewCreated: ${it.size}  //  $imagesList")
                }
                withContext(Dispatchers.Main){
                    videosAdapter.differ.submitList(imagesList)
                }
            }
        }
    }

    companion object {
        const val TAG = "SavedFragInfo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProgressUpdate(progress: Int) {
        Log.i(TAG, "onProgressUpdate: $progress  // ${Constant.totalImagesToCopy}")
        progressDialog.progress = progress
        if(progress == Constant.totalImagesToCopy) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                collectSavedImageVideosListFromStorage()
                withContext(Dispatchers.Main){
                    delay(500)
                    progressDialog.dismiss()
                }
            }
        }
    }

    private fun showDialogue(){
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.apply {
            max = Constant.totalImagesToCopy // Progress Dialog Max Value
            setMessage("Loading...") // Setting Message
            setTitle("ProgressDialog") // Setting Title
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) // Progress Dialog Style Horizontal
            setButton("Cancel"
            ) { _, _ ->
                Constant.isItCancel = true
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                    collectSavedImageVideosListFromStorage()
                }
                dismiss()
            }
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}