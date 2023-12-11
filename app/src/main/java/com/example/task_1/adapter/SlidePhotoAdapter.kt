package com.example.task_1.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.task_1.R
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import java.io.File
import javax.inject.Inject

class SlidePhotoAdapter @Inject constructor() : RecyclerView.Adapter<SlidePhotoAdapter.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ic_image_slider_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val mediaList = differ.currentList[position]
        if (!Constant.currentImageBitmap1.containsKey(position)) {
            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(File(Common.getFilePathFromImageUri(holder.itemView.context,mediaList.uri)!!))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        holder.imageview.setImageBitmap(resource)
                        Constant.currentImageBitmap1[position] = resource
                        Log.i("SlidePhotoAdapterInfo", "onResourceReady: ${Constant.currentImageBitmap1[position]}")
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        } else {
            val bitmap = Constant.currentImageBitmap1[position]
            holder.imageview.setImageBitmap(bitmap)
            Log.i("SlidePhotoAdapterInfo", "else part: ${Constant.currentImageBitmap1[position]}")
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageview: ImageView = itemView.findViewById(R.id.imageview)
    }
}