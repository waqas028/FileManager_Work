package com.example.task_1.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.task_1.adapter.ViewPagerAdapter
import com.example.task_1.databinding.ActivityMainBinding
import com.example.task_1.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mainViewModel : MainViewModel by viewModels()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionForAllVersion() //check permission

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected: $position")
                mainViewModel.currentFragment.value = position
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.cameraPreviewButton.setOnClickListener{
            val intent = Intent(this, CameraPreviewActivity::class.java)
            cameraActivityResultLauncher.launch(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissionForAllVersion() {
        Log.i(TAG, "checkPermissionForAllVersion: ${Build.VERSION.SDK_INT}")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            checkRequestPermissionFor14()
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q  && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
            checkRequestPermissionAboveTen()
        }else{
            checkRequestPermissionBelowTen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkRequestPermissionAboveTen(){
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                getAllListOfData()
                Log.i(TAG, "checkRequestPermissionAboveTen: collect videos list")
            }
            else -> {
                Log.i(TAG, "checkRequestPermissionAboveTen: requestPermission")
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_WRITE_REQUEST_CODE)
            }
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
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.getVideosList()
            mainViewModel.getImagesList()
            mainViewModel.getSaveVideoImagesList()
            mainViewModel.getNonEmptyDirectoriesWithFiles("")
        }
    }

    private val cameraActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val receivedValue = data?.getStringExtra("key")
            Log.i(TAG, "onActivityResult: $receivedValue")
            mainViewModel.getNonEmptyDirectoriesWithFiles("")
        }
    }

    companion object{
        const val TAG = "MainActivityInfo"
        const val READ_WRITE_REQUEST_CODE = 123
    }
}