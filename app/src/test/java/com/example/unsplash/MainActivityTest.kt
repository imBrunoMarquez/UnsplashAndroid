package com.example.unsplash


import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.test.rule.ActivityTestRule
import com.example.unsplash.presentation.popular.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class MainActivityTest {
    @Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    @Test
    fun checkMainActivityDisplayed() {
        val activity: MainActivity = activityRule.getActivity()
        Assert.assertNotNull(activity.findViewById(R.id.main_activity_layout))
    }

    @Test
    fun checkButtonText() {
        val activity: MainActivity = activityRule.getActivity()
        val button = activity.findViewById<Button>(R.id.button)
        Assert.assertEquals("Button Text is Incorrect", "Click Me", button.text.toString())
    }


}
