package com.example.bete.profile;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bete.R;
import com.example.bete.berita.BuatBerita;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private String username;
    private String passsword;


    @Rule
    public ActivityTestRule<Login> mActivityTestRule =
            new ActivityTestRule<>(Login.class);

    @Before
    public void isi(){
        username = "zakariawahyu";
        passsword = "1234";
    }

    @Test
    public void logintest() {
        onView(withId(R.id.usernamelogin)).perform(typeText(username));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlogin)).perform(typeText(passsword));
        closeSoftKeyboard();
        onView(withId(R.id.btn_login)).perform(click());
    }
}