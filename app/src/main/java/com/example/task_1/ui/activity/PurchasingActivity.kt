package com.example.task_1.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchasingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}