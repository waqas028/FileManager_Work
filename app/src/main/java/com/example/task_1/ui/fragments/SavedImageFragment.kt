@file:Suppress("DEPRECATION")

package com.example.task_1.ui.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_1.adapter.VideosAdapter
import com.example.task_1.databinding.FragmentSavedImageBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.ui.activity.ImagePreviewActivity
import com.example.task_1.ui.activity.VideoPreviewActivity
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
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSavedImageBinding.inflate(inflater, container, false)
        videosAdapter = VideosAdapter(this)
        binding.savedVideoImageRecyclerView.apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.savedVideoImageList.collect{savedFileList->
                Log.i(TAG, "collectSavedImageVideosListFromStorage: CollectList: ${savedFileList.size}")
                withContext(Dispatchers.Main){
                    videosAdapter.differ.submitList(savedFileList)
                    videosAdapter.updateAdapterDataList(savedFileList)
                    videosAdapter.updateAdapterDataList(savedFileList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.currentFragment.collect{
                Log.i(TAG, "onViewCreated: $it")
                videosAdapter.onPageUpdate(it)
            }
        }

        videosAdapter.stOnCopyClickListener {
            showDialogue()
        }

        videosAdapter.stOnItemClickListener {
            if(it.name.endsWith(".mp4")){
                val intent = Intent(requireContext(), VideoPreviewActivity::class.java)
                intent.putExtra(Constant.SELECT_VIDEO_PATH, it.uri)
                intent.putExtra(Constant.SELECT_VIDEO_NAME, it.name)
                intent.putExtra(Constant.PREVIOUS_FRAG_NAME, Constant.SAVED_FRAG_NAME)
                imagePreviewActivityResultLauncher.launch(intent)
            }else{
                val intent = Intent(requireContext(), ImagePreviewActivity::class.java)
                intent.putExtra("SavedImageFragName",it.name)
                intent.putExtra("SavedImageFragId",it.id)
                intent.putExtra("SavedImageFragUri",it.uri.toString())
                intent.putExtra("SavedImageFragSize",it.size)
                imagePreviewActivityResultLauncher.launch(intent)
            }
        }
    }

    private val imagePreviewActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val receivedValue = data?.getStringExtra("key")
            Log.i(TAG, "onActivityResult: $receivedValue")
            if(receivedValue.equals("DeleteImagesList")  || receivedValue.equals("deleteVideoList")){
                videosAdapter.updateDataList()
            }else{
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    mainViewModel.getSaveVideoImagesList()
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onProgressUpdate(progress: Int) {
        Log.i(TAG, "onProgressUpdate: $progress  // ${Constant.totalImagesToCopy}")
        progressDialog.progress = progress
        if(progress == Constant.totalImagesToCopy) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.getImagesList()
                mainViewModel.getVideosList()
                mainViewModel.getSaveVideoImagesList()
                withContext(Dispatchers.Main){
                    delay(500)
                    progressDialog.dismiss()
                }
            }
        }
    }

    override fun onDeleteOrCopyItemListener() {}

    @RequiresApi(Build.VERSION_CODES.Q)
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
                    mainViewModel.getSaveVideoImagesList()
                    mainViewModel.getImagesList()
                    mainViewModel.getVideosList()
                }
                dismiss()
            }
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}