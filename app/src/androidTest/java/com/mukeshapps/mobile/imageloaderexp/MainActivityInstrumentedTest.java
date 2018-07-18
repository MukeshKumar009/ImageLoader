package com.mukeshapps.mobile.imageloaderexp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.mukeshapps.mobile.imageloaderexp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 * Test the behaviour and status of RecyclerView in MainActivity.java
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Check if Recycler view has more than 0 item, this method will exit with 0 error
     * Tbis test should pass
     */
    @Test
    public void checkRecyclerViewItem(){
        assertTrue(getRVcount() > 0);
    }

    /***
     * Check if Recycler view is not scrolling i.e idle then return false. This test case should fail.
     */
    @Test
    public void checkRecyclerViewScrollingState(){
        assertFalse(getRVState()== SCROLL_STATE_IDLE );
    }

    //Return the rows count of RecyclerView
    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.recyclerView);
        return recyclerView.getAdapter().getItemCount();
    }

    //Return the Scroling state of RecyclerView
    private int getRVState(){
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.recyclerView);
        int state = recyclerView.getScrollState();
        return state;
    }
}
