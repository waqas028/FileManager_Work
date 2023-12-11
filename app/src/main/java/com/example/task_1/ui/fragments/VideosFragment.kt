package com.example.task_1.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_1.adapter.VideosAdapter
import com.example.task_1.databinding.FragmentVideosBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.ui.activity.MainActivity
import com.example.task_1.ui.activity.VideoPreviewActivity
import com.example.task_1.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.Q)
class VideosFragment : Fragment() , CopyImageProgressListener {
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!
    var videosAdapter: VideosAdapter = VideosAdapter(this)
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        binding.videosRecyclerView.apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                (activity as MainActivity).mainViewModel.videoList.collect{
                    Log.i(TAG, "collect videos List: $it")
                    withContext(Dispatchers.Main){ videosAdapter.differ.submitList(it) }
                }
            }
        }

        videosAdapter.stOnItemClickListener {
            val intent = Intent(requireContext(), VideoPreviewActivity::class.java)
            intent.putExtra(Constant.SELECT_VIDEO_PATH, it.uri)
            intent.putExtra(Constant.SELECT_VIDEO_NAME, it.name)
            startActivity(intent)
        }

        videosAdapter.stOnCopyClickListener {
            showDialogue()
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
        Log.i(TAG, "onProgressUpdate: $progress  //  ${Constant.currentFragment}")
        progressDialog.progress = progress
        if(progress == Constant.totalImagesToCopy && Constant.currentFragment == 1) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                (activity as MainActivity).mainViewModel.getVideosList()
                (activity as MainActivity).mainViewModel.getSaveVideoImagesList()
                withContext(Dispatchers.Main){
                    delay(500)
                    progressDialog.dismiss()
                }
            }
        }
        if(progress == Constant.totalImagesToCopy && Constant.currentFragment == 2) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                (activity as MainActivity).mainViewModel.getSaveVideoImagesList()
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
                    (requireActivity() as MainActivity).mainViewModel.getVideosList()
                }
                dismiss()
            }
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}