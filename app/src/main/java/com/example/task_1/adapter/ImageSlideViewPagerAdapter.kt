package com.example.task_1.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.task_1.R
import com.example.task_1.model.Media
import java.util.Objects

class ImageSlideViewPagerAdapter (val context: Context, private val imageList: List<Media>) : PagerAdapter()  {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.ic_image_slider_layout, container, false)
        val imageView: ImageView = itemView.findViewById<View>(R.id.imageview) as ImageView
        Log.i("ImageSlideAdapterInfo", "instantiateItem: $position  //  ${imageList[position].name}")
        Glide.with(context)
            .load(imageList[position].uri)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_image_24)
            .into(imageView)
        /*if (!Constant.currentImageBitmap.containsKey(position)) {
            Glide.with(context)
                .asBitmap()
                .load(File(Common.getFilePathFromImageUri(context,imageList[position].uri).orEmpty()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageView.setImageBitmap(resource)
                        Constant.currentImageBitmap[position] = resource
                        Log.i("ImageSliderAdapterInfo", "onResourceReady: ${Constant.currentImageBitmap[position]}")
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        } else {
            val bitmap = Constant.currentImageBitmap[position]
            imageView.setImageBitmap(bitmap)
            Log.i("ImageSliderAdapterInfo", "else part: ${Constant.currentImageBitmap[position]}")
        }*/
        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}