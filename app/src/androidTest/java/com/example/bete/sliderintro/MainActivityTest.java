package com.example.bete.sliderintro;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bete.R;
import com.example.bete.profile.Login;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sliderHome(){
        onView(withId(R.id.nextBtn)).perform(click());
        onView(withId(R.id.nextBtn)).perform(click());
        onView(withId(R.id.nextBtn)).perform(click());
    }

}