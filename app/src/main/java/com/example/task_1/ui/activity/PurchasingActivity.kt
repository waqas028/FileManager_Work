package com.example.task_1.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task_1.adapter.PremiumImagesAdapter
import com.example.task_1.databinding.ActivityPurchasingBinding
import com.example.task_1.model.Media
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PurchasingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchasingBinding
    @Inject
    lateinit var premiumImagesAdapter: PremiumImagesAdapter
    private var isSystemUIVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        //windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding = ActivityPurchasingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.displayAreaView.setOnClickListener {
            //isSystemUIVisible = !isSystemUIVisible // Toggle the visibility state
            toggleNavigationBar()
        }

        val list = mutableListOf<Media>()
        list.add(Media(1, Uri.parse(""),"Remove Ads",1))
        list.add(Media(2, Uri.parse(""),"Equalizer Effects",1))
        list.add(Media(3, Uri.parse(""),"Themes",1))
        list.add(Media(4, Uri.parse(""),"24/7",1))
        list.add(Media(5, Uri.parse(""),"NO Ads",1))
        premiumImagesAdapter.differ.submitList(list)
        binding.recyclerview.apply {
            adapter = premiumImagesAdapter
            layoutManager = LinearLayoutManager(this@PurchasingActivity, RecyclerView.HORIZONTAL,false)
        }
    }

    private fun toggleNavigationBar() {
        val decorView = window.decorView
        val uiOptions = decorView.systemUiVisibility
        val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == uiOptions

        decorView.systemUiVisibility = if (isImmersiveModeEnabled) {
            // Show navigation bar
            uiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        } else {
            // Hide navigation bar
            uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    private fun toggleSystemUIVisibility() {
        if (isSystemUIVisible) {
            // Hide the system bars
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } else {
            // Show the system bars
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }
}