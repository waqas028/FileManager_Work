package com.example.task_1.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
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
import com.example.task_1.interfaces.CopyImageProgressListener
import com.example.task_1.model.Media
import com.example.task_1.utils.Common
import com.example.task_1.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ImagesAdapter (private val progressListener: CopyImageProgressListener?): RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    companion object{
        const val TAG = "ImagesAdapterInfo"
    }
    var copiedImagesCount = 0
    private var isSelected = mutableListOf<Boolean>()
    private val selectedItems = mutableListOf<Media>() // Replace T with your data type
    private var actionMode: ActionMode? = null
    private var menuSelection = R.menu.selection_menu
    private var checkPosition = mutableListOf<Media>()
    private var selectAllItems = false
    private lateinit var holderview : ViewHolder
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
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ic_images_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaList = differ.currentList[position]
        holderview = holder
        holder.imageNameTextview.text = mediaList.name
        holder.imageview.layout(0,0,0,0)
        try{
            val requestOptions = RequestOptions
                .fitCenterTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300,300)
            Glide.with(holder.itemView.context)
                .load(File(Common.getFilePathFromImageUri(holder.itemView.context,mediaList.uri)!!))
                .apply(requestOptions)
                .placeholder(R.drawable.ic_photo)
                .into(holder.imageview)
        }catch (e:Exception){
            Log.i(TAG, "onBindViewHolder: Exception: $e")
        }
        if(isSelected(mediaList)) holderview.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
        // Set click listener for item selection
        holder.itemView.setOnLongClickListener {
            if(!mediaList.name.startsWith("CropDirectory")){
                if (actionMode == null) {
                    actionMode = holder.itemView.startActionMode(actionModeCallback)
                }
                toggleSelection(mediaList)
                checkPosition.add(mediaList)
                for(check in checkPosition.indices){
                    Log.i(TAG, "onBindViewHolder: $check   ${checkPosition[check]}  //   ${isSelected(checkPosition[check])}")
                    if(isSelected(checkPosition[check])) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
                }
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
                    checkPosition.add(mediaList)
                    for(check in checkPosition.indices){
                        if(isSelected(checkPosition[check])) holder.selectImageview.visibility = View.VISIBLE else holder.selectImageview.visibility = View.GONE
                    }
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

    //new code for selection copy
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(menuSelection, menu) // Create a menu XML
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        @SuppressLint("CheckResult")
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_action_save -> {
                    if(selectedItems.size>0){
                        Constant.isItCancel = false
                        onCopyClickListener?.let {
                            it(true)
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            Log.i(TAG, "onActionItemClicked: ${selectedItems.size}  //  ${Constant.isItCancel}")
                            for(img in selectedItems.indices){
                                try {
                                    val currentSourceImagePath = Common.getFilePathFromImageUri(holderview.itemView.context, selectedItems[img].uri)!!
                                    Log.i(TAG, "onActionItemClicked: path-> $currentSourceImagePath")
                                    if(currentSourceImagePath.endsWith(".jpg") || currentSourceImagePath.endsWith(".png")) copiedImagesCount++
                                    Common.copyFileToFolder(
                                        currentSourceImagePath,
                                        progressListener,
                                        mode,
                                        copiedImagesCount,
                                        selectedItems
                                    ){
                                        clearSelections()
                                    }
                                    if(Constant.isItCancel) {
                                        Constant.isItCancel = false
                                        clearSelections()
                                        withContext(Dispatchers.Main){
                                            mode?.finish()
                                        }
                                        Log.i(TAG, "onActionItemClicked: set false then check: ${Constant.isItCancel}")
                                        return@launch
                                    }
                                }catch (e:Exception){
                                    copiedImagesCount++
                                    Log.i(TAG, "onActionItemClicked: $e")
                                }
                            }
                        }
                    }
                    true
                }
                R.id.menu_action_select_all -> {
                    Log.i(TAG, "onActionItemClicked: selectAllItems: $selectAllItems")
                    if(selectAllItems) unSelectAllItems() else selectAllItems()
                    true
                }

                R.id.menu_action_delete -> {
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            clearSelections()
            actionMode = null
            notifyDataSetChanged()
        }
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
        actionMode?.title = "${selectedItems.size} selected"
        Constant.totalImagesToCopy = selectedItems.size
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until differ.currentList.size) {
                isSelected.add(true)
            }
        }
        notifyDataSetChanged()
    }

    private fun unSelectAllItems() {
        selectAllItems = false
        selectedItems.clear()
        actionMode?.title = "${selectedItems.size} selected"
        Constant.totalImagesToCopy = selectedItems.size
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until differ.currentList.size) {
                isSelected.add(false)
            }
        }
        notifyDataSetChanged()
    }

    fun onPageUpdate(onPageSelected: Int) {
        Log.i(TAG, "onPageUpdate: $onPageSelected")
        clearSelections()
        actionMode?.finish() // Finish the ActionMode
        when(onPageSelected){
            0 -> {menuSelection = R.menu.selection_menu}
            1 -> {menuSelection = R.menu.selection_menu}
            2 -> {menuSelection = R.menu.ic_delete_menu}
        }
    }
}