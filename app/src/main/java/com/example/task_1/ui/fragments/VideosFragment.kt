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
import com.example.task_1.databinding.FragmentVideosBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.ui.activity.VideoPreviewActivity
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.Q)
class VideosFragment : Fragment() , CopyImageProgressListener {
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        videosAdapter = VideosAdapter(this)
        binding.videosRecyclerView.apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.videoList.collect{
                Log.i(TAG, "collect videos List: ${it.size}")
                withContext(Dispatchers.Main){
                    videosAdapter.differ.submitList(it)
                    videosAdapter.updateAdapterDataList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.currentFragment.collect{
                Log.i(TAG, "onViewCreated: $it")
                videosAdapter.onPageUpdate(it)
            }
        }

        videosAdapter.stOnItemClickListener {
            val intent = Intent(requireContext(), VideoPreviewActivity::class.java)
            intent.putExtra(Constant.SELECT_VIDEO_PATH, it.uri)
            intent.putExtra(Constant.SELECT_VIDEO_NAME, it.name)
            imagePreviewActivityResultLauncher.launch(intent)
        }

        videosAdapter.stOnCopyClickListener {
            showDialogue()
        }
    }

    private val imagePreviewActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val receivedValue = data?.getStringExtra("key")
            Log.i(TAG, "onActivityResult: $receivedValue")
            if(receivedValue.equals("deleteVideoList")){
                videosAdapter.updateDataList()
            }
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                mainViewModel.getSaveVideoImagesList()
            }
        }
    }

    companion object {
        const val TAG = "VideosFragInfo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProgressUpdate(progress: Int) {
        Log.i(TAG, "onProgressUpdate: $progress")
        progressDialog.progress = progress
        if(progress == Constant.totalImagesToCopy) {
            progressDialog.dismiss()
        }
    }

    override fun onDeleteOrCopyItemListener() { viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { mainViewModel.getSaveVideoImagesList() } }

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
                dismiss()
            }
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}