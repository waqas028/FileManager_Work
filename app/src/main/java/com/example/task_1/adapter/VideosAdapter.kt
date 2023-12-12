package com.example.task_1.adapter

import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.task_1.R
import com.example.task_1.extension.MyActionModeCallback
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class VideosAdapter (private val progressListener: CopyImageProgressListener?) : RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    companion object{
        const val TAG = "VideosAdapterInfo"
    }
    private var copiedImagesCount = 0
    private var isSelected = mutableListOf<Boolean>()
    private val selectedItems = mutableListOf<Media>()
    private var actionMode: ActionMode? = null
    private var menuSelection = R.menu.selection_menu
    private var selectAllItems = false
    private val differCallback = object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    init {
        for (i in 0 until differ.currentList.size) {
            isSelected.add(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ic_videos_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaList = differ.currentList[position]
        holder.imageNameTextview.text = mediaList.name
        if(mediaList.name.endsWith(".mp4")) {
            holder.videoPlayImageview.visibility = View.VISIBLE
            try{
                val requestOptions = RequestOptions
                    .fitCenterTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(300,300)
                Glide.with(holder.itemView.context)
                    .load(File(Common.getFilePathFromVideoUri(holder.itemView.context,mediaList.uri).orEmpty()))
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_photo)
                    .into(holder.imageview)
            }catch (e:Exception){
                Log.i(TAG, "onBindViewHolder: $e")
            }
        }else{
            holder.videoPlayImageview.visibility = View.GONE
            try{
                val requestOptions = RequestOptions
                    .fitCenterTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(300,300)
                Glide.with(holder.itemView.context)
                    .load(File(Common.getFilePathFromImageUri(holder.itemView.context,mediaList.uri).orEmpty()))
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_photo)
                    .into(holder.imageview)
            }catch (e:Exception){
                Log.i(TAG, "onBindViewHolder: $e")
            }
        }
        if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE

        // Set click listener for item selection
        holder.itemView.setOnLongClickListener {
            if (actionMode == null) {
                when(Constant.currentFragment){
                    0 -> {menuSelection = R.menu.selection_menu}
                    1 -> {menuSelection = R.menu.selection_menu}
                    2 -> { menuSelection = R.menu.ic_delete_menu}
                    3 -> { menuSelection = R.menu.ic_delete_menu}
                }
                actionMode = holder.itemView.startActionMode(MyActionModeCallback(
                    holder.itemView.context,
                    menuSelection,
                    selectedItems,
                    copiedImagesCount,
                    progressListener,
                    onCopyClickListener = {
                        onCopyClickListener?.let {
                        it(true)
                    }},
                    onClearSelectionClickListener = { clearSelections() },
                    onSelectAllItemsClickListener = { if(selectAllItems) unSelectAllItems() else selectAllItems() },
                    onDestroyActionModeClickListener = {
                        clearSelections()
                        actionMode = null
                        notifyDataSetChanged()
                        Log.i(TAG, "onDestroyActionMode: ")}
                ))
            }
            toggleSelection(mediaList)
            if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
            true
        }

        // Your regular item click listener
        holder.itemView.setOnClickListener {
            if (actionMode != null) {
                toggleSelection(mediaList)
                if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
            } else {
                Constant.selectVideoPosition = position
                onItemClickListener?.let {
                    it(mediaList)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageNameTextview: TextView = itemView.findViewById(R.id.imageNameTextview)
        val imageview: ImageView = itemView.findViewById(R.id.videoThumbnailImageview)
        val selectImageview: ImageView = itemView.findViewById(R.id.selectImageview)
        val videoPlayImageview: ImageView = itemView.findViewById(R.id.videoPlayImageview)
    }

    private var onItemClickListener: ((Media) -> Unit)? = null
    fun stOnItemClickListener(listener: (Media) -> Unit){
        onItemClickListener = listener
    }

    private var onCopyClickListener: ((Boolean) -> Unit)? = null
    fun stOnCopyClickListener(listener: (Boolean) -> Unit){
        onCopyClickListener = listener
    }

    // Toggle selection of an item
    private fun toggleSelection(item: Media) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        Log.i(TAG, "toggleSelection: ${selectedItems.size}")
        actionMode?.title = "${selectedItems.size} selected"
        Constant.totalImagesToCopy = selectedItems.size
    }

    private fun isSelected(item: Media): Boolean {
        return selectedItems.contains(item)
    }
    fun clearSelections() {
        copiedImagesCount = 0
        selectAllItems = false
        selectedItems.clear()
        for (i in 0 until differ.currentList.size) {
            isSelected.add(false)
        }
    }

    private fun selectAllItems() {
        selectAllItems = true
        selectedItems.clear()
        selectedItems.addAll(differ.currentList)
        Constant.totalImagesToCopy = selectedItems.size
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until differ.currentList.size) {
                //Log.i(TAG, "selectAllItems: $i  //  ${isSelected(selectedItems[i])} // ${selectedItems.size}  //  ${differ.currentList.size}")
                isSelected.add(true)
            }
        }
        actionMode?.title = "${selectedItems.size} selected"
        notifyDataSetChanged()
    }

    private fun unSelectAllItems() {
        selectAllItems = false
        selectedItems.clear()
        Constant.totalImagesToCopy = selectedItems.size
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until differ.currentList.size) {
                isSelected.add(false)
            }
        }
        actionMode?.title = "${selectedItems.size} selected"
        notifyDataSetChanged()
    }

    fun onPageUpdate(onPageSelected: Int) {
        Log.i(TAG, "onPageUpdate: $onPageSelected $actionMode")
        clearSelections()
        actionMode?.finish() // Finish the ActionMode
    }
}