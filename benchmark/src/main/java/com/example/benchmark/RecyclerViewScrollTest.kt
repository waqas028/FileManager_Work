package com.example.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task_1.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewScrollTest {

    @RequiresApi(Build.VERSION_CODES.O)
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewScroll() {
        // Delay to wait for RecyclerView to load data, if needed
        Thread.sleep(2000)

        // Assuming your RecyclerView has an ID of recyclerView, replace with your ID
        onView(withId(com.example.task_1.R.id.imagesRecyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10) // Scroll to position 10
            )
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0) // Scroll back to position 0
            )
    }
}