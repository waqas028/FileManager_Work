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
import java.io.File

class CropDirectoryAdapter (private val progressListener: CopyImageProgressListener?): RecyclerView.Adapter<CropDirectoryAdapter.ViewHolder>() {
    companion object{
        const val TAG = "CropDirectoryAdapterInfo"
    }
    private val selectedItems = mutableListOf<Media>()
    private var actionMode: ActionMode? = null
    private var menuSelection = R.menu.selection_menu
    private var selectAllItems = false
    private val differCallback = object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ic_images_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaList = differ.currentList[position]
        holder.imageNameTextview.text = mediaList.name
        Log.i(TAG, "onBindViewHolder: URI: ${mediaList.uri}")
        try{
            val requestOptions = RequestOptions
                .fitCenterTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300,300)
            Glide.with(holder.itemView.context)
                .load(mediaList.uri)
                .apply(requestOptions)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24)
                .into(holder.imageview)
        }catch (e:Exception){
            Log.i(TAG, "onBindViewHolder: Exception: $e   //  ${File(Common.getFilePathFromImageUri(holder.itemView.context,mediaList.uri).orEmpty())}")
        }
        if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
        // Set click listener for item selection
        holder.itemView.setOnLongClickListener {
            if(!mediaList.name.startsWith("CropDirectory")){
                if (actionMode == null) {
                    actionMode = holder.itemView.startActionMode(MyActionModeCallback(
                        holder.itemView.context,
                        menuSelection,
                        selectedItems,
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
                            Log.i(TAG, "onDestroyActionMode: ")
                        },
                    ))
                    Constant.actionMode = actionMode
                }
                toggleSelection(mediaList)
                if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
            }
            true
        }

        // Your regular item click listener
        holder.itemView.setOnClickListener {
            if(mediaList.name.startsWith("CropDirectory")){
                onItemClickListener?.let {
                    it(mediaList)
                }
            }else{
                if (actionMode != null) {
                    // In ActionMode, toggle selection on click
                    toggleSelection(mediaList)
                    if(isSelected(mediaList)) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
                } else {
                    // Handle item click in a non-selection context
                    Constant.selectImagePosition = position
                    onItemClickListener?.let {
                        it(mediaList)
                    }
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageNameTextview: TextView = itemView.findViewById(R.id.imageNameTextview)
        val imageview: ImageView = itemView.findViewById(R.id.imageview)
        val selectImageview: ImageView = itemView.findViewById(R.id.selectImageview)
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

    private fun isSelected(item: Media): Boolean = selectedItems.contains(item)

    private fun clearSelections() {
        selectAllItems = false
        selectedItems.clear()
    }

    private fun selectAllItems() {
        selectAllItems = true
        selectedItems.clear()
        selectedItems.addAll(differ.currentList)
        actionMode?.title = "${selectedItems.size} selected"
        Constant.totalImagesToCopy = selectedItems.size
        notifyDataSetChanged()
    }

    private fun unSelectAllItems() {
        selectAllItems = false
        selectedItems.clear()
        actionMode?.title = "${selectedItems.size} selected"
        Constant.totalImagesToCopy = selectedItems.size
        notifyDataSetChanged()
    }

    fun onPageUpdate(onPageSelected: Int) {
        Log.i(TAG, "onPageUpdate: $onPageSelected")
        clearSelections()
        Constant.actionMode?.finish() // Finish the ActionMode
        when(onPageSelected){
            0 -> { menuSelection = R.menu.selection_menu}
            1 -> { menuSelection = R.menu.selection_menu}
            2 -> { menuSelection = R.menu.ic_delete_menu}
            3 -> { menuSelection = R.menu.ic_delete_menu}
        }
    }

    fun updateDataList(){
        Log.i(TAG, "updateDataList: ")
        clearSelections()
        notifyDataSetChanged()
    }
}