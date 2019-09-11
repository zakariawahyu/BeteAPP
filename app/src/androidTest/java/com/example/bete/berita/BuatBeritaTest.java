package com.example.bete.berita;




import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.bete.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class BuatBeritaTest {
    private String judulberita;
    private String isiberita;


    @Rule
    public ActivityTestRule<BuatBerita> mActivityTestRule =
            new ActivityTestRule<>(BuatBerita.class);

    @Before
    public void isi(){
        judulberita = "Telkom University Meraih Juara";
        isiberita = "Meraih beberapa gelar juara";

    }


    @Test
    public void pilihfotoberita(){
        onView(withId(R.id.btn_selectimageberita)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_selectimageberita)).perform(click());
    }

    @Test
    public void tambahbertia(){
        onView(withId(R.id.et_judulberita)).perform(typeText(judulberita));
        closeSoftKeyboard();
        onView(withId(R.id.et_isiberita)).perform(typeText(isiberita));
        closeSoftKeyboard();

        String[] myArray =
                mActivityTestRule.getActivity().getResources()
                        .getStringArray(R.array.kategori);

        int size = myArray.length;
        for (int i=0; i<size; i++) {
            onData(is(myArray[i])).perform(click());
        }
    }
}