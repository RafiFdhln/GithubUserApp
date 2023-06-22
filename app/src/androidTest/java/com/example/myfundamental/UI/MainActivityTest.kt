package com.example.myfundamental.UI

import androidx.test.core.app.ActivityScenario
import com.example.myfundamental.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val dummyQueue = "Ahmad"

    @Before
    fun setup() {
        ActivityScenario.launch(Splash::class.java)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testComponentShowCorrectly() {
        Thread.sleep(2000)
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
    }
    @Test
    fun testSearch(){
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummyQueue), pressImeActionButton())
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
    }
}