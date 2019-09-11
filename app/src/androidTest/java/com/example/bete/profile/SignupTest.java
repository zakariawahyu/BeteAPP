package com.example.bete.profile;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bete.R;

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
public class SignupTest {
    private String fullname;
    private String username;
    private String passsword;
    private String confirmpass;


    @Rule
    public ActivityTestRule<Signup> mActivityTestRule =
            new ActivityTestRule<>(Signup.class);

    @Before
    public void isi(){
        fullname = "Zakaria Wahyu Nur Utomo";
        username = "zakariawahyu";
        passsword = "1234";
        confirmpass = "1234";
    }

    @Test
    public void signupTest(){
        onView(withId(R.id.namalengkap)).perform(typeText(fullname));
        closeSoftKeyboard();
        onView(withId(R.id.usernamesignup)).perform(typeText(username));
        closeSoftKeyboard();
        onView(withId(R.id.passwordsignup)).perform(typeText(passsword));
        closeSoftKeyboard();
        onView(withId(R.id.confirmpassword)).perform(typeText(confirmpass));
        closeSoftKeyboard();
        onView(withId(R.id.btn_signupnow)).perform(click());
    }

}