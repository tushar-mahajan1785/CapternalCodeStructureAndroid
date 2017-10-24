package com.capternal.test;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.capternal.test.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jupitor on 23/10/17.
 */
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);
    private String mInputText = "Prasanna";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void startFirstTestCase() {
        //Input Some text in EditText
        Espresso.onView(withId(R.id.editTextInput)).perform(typeText(mInputText));
        // Close soft keyboard
        Espresso.closeSoftKeyboard();
        // Click Change Text Button to change text of below textView.
        Espresso.onView(withId(R.id.buttonChangeText)).perform(click());
        // Checking text in textView
        Espresso.onView(withId(R.id.textView_sample)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {

    }

}