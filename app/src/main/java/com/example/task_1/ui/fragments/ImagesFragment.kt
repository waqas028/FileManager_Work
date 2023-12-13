package com.example.task_1.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task_1.adapter.ImagesAdapter
import com.example.task_1.databinding.FragmentImagesBinding
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.ui.activity.ImagePreviewActivity
import com.example.task_1.ui.activity.MainActivity
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.O)
class ImagesFragment : Fragment(), CopyImageProgressListener {
    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    var imagesAdapter : ImagesAdapter = ImagesAdapter(this){}
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        binding.imagesRecyclerView.apply {
            adapter = imagesAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.imageList.collect{
                Log.i(TAG, "collectImagesList: ${it.size}  //  $it")
                withContext(Dispatchers.Main){
                    imagesAdapter.differ.submitList(it)
                }
            }
        }

        imagesAdapter.stOnItemClickListener {
            val intent = Intent(requireContext(), ImagePreviewActivity::class.java)
            startActivity(intent)
        }

        imagesAdapter.stOnCopyClickListener {
            showDialogue()
        }

    }

    companion object {
        const val TAG = "ImagesFragInfo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: ")
    }

    override fun onProgressUpdate(progress: Int) {
        Log.i(TAG, "onProgressUpdate: $progress  // ${Constant.totalImagesToCopy}")
        progressDialog.progress = progress
        if(Constant.currentFragment == 0){
            if(progress == Constant.totalImagesToCopy) {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                    //mainViewModel.getImagesList()
                    withContext(Dispatchers.Main){
                        delay(500)
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun showDialogue() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.apply {
            max = Constant.totalImagesToCopy // Progress Dialog Max Value
            setMessage("Loading...") // Setting Message
            setTitle("ProgressDialog") // Setting Title
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) // Progress Dialog Style Horizontal
            setButton(
                "Cancel"
            ) { _, _ ->
                Constant.isItCancel = true
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    //mainViewModel.getImagesList()
                }
                dismiss()
            }
            show() // Display Progress Dialog
            setCancelable(false)
        }
    }
}