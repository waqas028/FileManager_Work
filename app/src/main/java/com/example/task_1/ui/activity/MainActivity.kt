package com.example.task_1.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.task_1.adapter.ViewPagerAdapter
import com.example.task_1.databinding.ActivityMainBinding
import com.example.task_1.ui.fragments.ImagesFragment
import com.example.task_1.ui.fragments.SavedImageFragment
import com.example.task_1.ui.fragments.VideosFragment
import com.example.task_1.utils.Constant
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mainViewModel : MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionForAllVersion() //check permission

        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected: $position")
                Constant.currentFragment = position
                ImagesFragment().imagesAdapter.onPageUpdate(position)
                VideosFragment().videosAdapter.onPageUpdate(position)
                SavedImageFragment().videosAdapter.onPageUpdate(position)
                if(position == 2){
                    CoroutineScope(Dispatchers.IO).launch { mainViewModel.getSaveVideoImagesList() }
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.cameraPreviewButton.setOnClickListener{
            startActivity(Intent(this,CameraPreviewActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissionForAllVersion() {
        Log.i(TAG, "checkPermissionForAllVersion: ${Build.VERSION.SDK_INT}")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU){
            checkRequestPermissionFor14()
        }else{
            checkRequestPermissionBelowTen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkRequestPermissionBelowTen(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getAllListOfData()
                Log.i(TAG, "checkRequestPermissionBelowTen: collect videos list")
            }
            else -> {
                Log.i(TAG, "checkRequestPermissionBelowTen: requestPermission")
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), READ_WRITE_REQUEST_CODE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkRequestPermissionFor14(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_VIDEO
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                getAllListOfData()
                Log.i(TAG, "checkRequestPermissionFor14: collect videos list")
            }
            else -> {
                Log.i(TAG, "checkRequestPermissionFor14: requestPermission")
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_VIDEO), READ_WRITE_REQUEST_CODE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_WRITE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "onRequestPermissionsResult: Granted")
                    getAllListOfData()
                } else {
                    Log.i(TAG, "onRequestPermissionsResult: Not Granted")
                    checkPermissionForAllVersion()
                }
                return
            }
            else -> {
                Log.i(TAG, "onRequestPermissionsResult: Ignore")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getAllListOfData(){
        CoroutineScope(Dispatchers.IO).launch {
            mainViewModel.getVideosList()
            mainViewModel.getImagesList()
            mainViewModel.getSaveVideoImagesList()
            mainViewModel.getCropImagesList("")
        }
    }

    companion object{
        const val TAG = "MainActivityInfo"
        const val READ_WRITE_REQUEST_CODE = 123
    }
}