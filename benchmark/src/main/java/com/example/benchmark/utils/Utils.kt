package com.example.benchmark.utils

import android.util.Log
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.SearchCondition
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import java.io.ByteArrayOutputStream

fun UiDevice.waitAndFind(
    selector: BySelector,
    timeout: Long = 5_000,
    message: String? = null
): UiObject2{
    waitOrFail(Until.hasObject(selector),timeout,message)
    return findOrFail(selector,message)
}

fun UiDevice.waitOrFail(
    searchCondition: SearchCondition<Boolean>,
    timeout: Long,
    message: String?
){
    if(!wait(searchCondition,timeout)){
        val hierarchy = getWindowHierarchy()
        Log.d("UtilsBenchMark", hierarchy)
        throw AssertionError((message ?: "Objects not on screen") + "\n$hierarchy")
    }
}

fun UiDevice.findOrFail(selector: BySelector, message: String? = null): UiObject2 {
    val element = findObject(selector)
    if(element == null){
        val hierarchy = getWindowHierarchy()
        Log.d("UtilsBenchMark", "findOrFail: $hierarchy")
        throw AssertionError((message ?: "Objects not on Screen ($selector)") + "\n$hierarchy")
    }
    return element
}

fun UiDevice.getWindowHierarchy(): String {
    val output = ByteArrayOutputStream()
    dumpWindowHierarchy(output)
    return output.toString()
}