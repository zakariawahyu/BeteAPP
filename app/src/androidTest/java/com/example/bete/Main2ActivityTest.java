package com.example.bete;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.bete.profile.Signup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class Main2ActivityTest {

    @Rule
    public ActivityTestRule<Main2Activity> mActivityTestRule =
            new ActivityTestRule<>(Main2Activity.class);


    @Test
    public void lihatBerita(){
        onView(withId(R.id.rvListBerita)).perform(RecyclerViewActions.actionOnItemAtPosition(28, longClick()));
    }

}