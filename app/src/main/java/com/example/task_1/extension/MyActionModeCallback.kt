package com.example.task_1.extension

import android.content.Context
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
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

class MyActionModeCallback(
    private val context: Context,
    private val menuSelection: Int,
    private val selectedItems: MutableList<Media>,
    private val progressListener: CopyImageProgressListener?,
    private var onCopyClickListener: () -> Unit,
    private var onClearSelectionClickListener: () -> Unit,
    private var onSelectAllItemsClickListener: () -> Unit,
    private var onDestroyActionModeClickListener: () -> Unit,
): ActionMode.Callback {
    private var copiedImagesCount = 0
    companion object{
        const val TAG = "MyActionModeInfo"
    }
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(menuSelection, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_action_save -> {
                if(selectedItems.size > 0){
                    Constant.isItCancel = false
                    onCopyClickListener()
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.i(TAG, "onActionItemClicked: Copy-> ${selectedItems.size}")
                        selectedItems.forEach {
                            copiedImagesCount++
                            val currentSourceImagePath = Common.getFilePathFromVideoUri(context,it.uri)
                            Common.copyImagesToFolder(
                                currentSourceImagePath.orEmpty(),
                                progressListener,
                                mode,
                                copiedImagesCount,
                                selectedItems
                            ){
                                onClearSelectionClickListener()
                                progressListener?.onDeleteOrCopyItemListener()
                            }
                            if(Constant.isItCancel) {
                                Constant.isItCancel = false
                                onClearSelectionClickListener()
                                progressListener?.onDeleteOrCopyItemListener()
                                withContext(Dispatchers.Main){
                                    mode?.finish()
                                }
                                return@launch
                            }
                        }
                    }
                }
            }
            R.id.menu_action_select_all -> {
                onSelectAllItemsClickListener()
            }
            R.id.menu_action_delete -> {
                if(selectedItems.size>0){
                    Constant.isItCancel = false
                    onCopyClickListener()
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.i(TAG, "onActionItemClicked: Delete-> ${selectedItems.size}  //  ${Constant.isItCancel}  //  DeleteFile-> ${selectedItems[0].uri}")
                        for(img in selectedItems.indices){
                            try {
                                copiedImagesCount++
                                Common.deleteMultipleFile(
                                    context,
                                    selectedItems[img].uri,
                                    File(selectedItems[img].uri.path.orEmpty()),
                                    mode,
                                    progressListener,
                                    copiedImagesCount,
                                    selectedItems
                                ){
                                    onClearSelectionClickListener()
                                    progressListener?.onDeleteOrCopyItemListener()
                                }
                                if(Constant.isItCancel) {
                                    Constant.isItCancel = false
                                    onClearSelectionClickListener()
                                    progressListener?.onDeleteOrCopyItemListener()
                                    withContext(Dispatchers.Main){
                                        mode?.finish()
                                    }
                                    return@launch
                                }
                            }catch (e:Exception){
                                copiedImagesCount++
                                Log.i(TAG, "onActionItemClicked: Delete-> $e")
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onDestroyActionModeClickListener()
    }
}
