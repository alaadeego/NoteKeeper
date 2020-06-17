package com.alrayes.notekeeper;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class NextThroughNotesTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule(MainActivity.class);

    @Test
    public void NextThroughNotes() {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()); //open drawer_layout
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_note));// click on nav_note item
        onView(withId(R.id.list_items)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); // press on first item in RecyclerView

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        for (int index = 0; index < notes.size(); index++) { // loop all over item through clicking next btn

            NoteInfo note = notes.get(index);
            onView(withId(R.id.spinner_courses)).check(
                    matches(withSpinnerText(note.getCourse().getTitle())));

            onView(withId(R.id.text_note_title)).check(matches(withText(note.getTitle())));
            onView(withId(R.id.text_note_text)).check(matches(withText(note.getText())));

            if (index < notes.size() - 1) {  //all  item  before last item next btn should be enabled
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click()); // allOf used to add more critera (is Enabled)
            }


        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled()))); // here mean we reach last item and it should be disabled next btn
        pressBack();

    }
}