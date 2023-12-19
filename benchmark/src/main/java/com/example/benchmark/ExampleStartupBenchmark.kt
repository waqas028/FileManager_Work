package com.example.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import com.example.benchmark.utils.findOrFail
import com.example.benchmark.utils.waitAndFind
import com.example.task_1.R
import com.example.task_1.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.example.task_1",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            startActivityAndWait()
        }
    ) {
        //pressHome()
        startActivityAndWait()
        testRecyclerViewScroll()
    }

    /*@OptIn(ExperimentalMetricApi::class)
    @Test
    fun scrollList() {
        benchmarkRule.measureRepeated(
            packageName = "com.example.task_1",
            metrics = listOf(
                FrameTimingMetric(),
                TraceSectionMetric("RV CreateView"),
                TraceSectionMetric("RV OnBindView"),
            ),
            iterations = 5,
            startupMode = StartupMode.COLD)
            {
                device.waitAndFind(By.res("imagesRecyclerView")).also {
                    it.setGestureMargin(device.displayWidth / 10)
                    it.fling(Direction.DOWN)
                }

                device.findOrFail(By.res("imagesRecyclerView")).fling(Direction.UP)

                *//*val recycler = device.findObject(By.res("com.example.task_1", "imagesRecyclerView"))
                // Set gesture margin to avoid triggering gesture navigation
                // with input events from automation.
                recycler.setGestureMargin(device.displayWidth / 5)

                // Scroll down several times
                repeat(3) { recycler.fling(Direction.DOWN) }*//*
            }
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun testRecyclerViewScroll() {
        // Use ActivityScenario to launch the activity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Delay to wait for RecyclerView to load data, if needed
        Thread.sleep(2000)

        // Replace R.id.recyclerView with your actual RecyclerView ID
        onView(withId(R.id.imagesRecyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
            )
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )
    }
}