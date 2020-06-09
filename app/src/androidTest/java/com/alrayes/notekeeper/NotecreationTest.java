package com.alrayes.notekeeper;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.pressBack;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NotecreationTest {

    static DataManager sDataManger;

    @BeforeClass
    public static void classSetUp() {
        sDataManger = DataManager.getInstance();
    }

    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void CreatNewNote() {
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click()) ;
        //  Test UI Test Behaviour
        onView(withId(R.id.fab)).perform(click());        //here open note activity

        // to choose value and set it in spinner
        final CourseInfo course = sDataManger.getCourse("java_lang");
        final String noteTitle = "Test note title";
        final String noteText = "this is the body of our test note";

        //onView 
        onView(withId(R.id.spinner_courses)).perform(click()); // here open spinner to choose

        //OnData used with AdapterView
        // Return DataInterAction refrence
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click()); // here set the choosed value
        //here test if its realy equal to value we set
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString((course.getTitle())))));

        //fill the view then ckeck if its filled right
        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                closeSoftKeyboard());
        onView(withId(R.id.text_note_text)).check(matches(withText(containsString(noteText))));

        pressBack();

        //Test  UI Logic behaviour
        //test when back does new note create correctly
        int noteIndex = sDataManger.getNotes().size() - 1;

        NoteInfo compareNote = sDataManger.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());

        //All UI Logic and behavioure work correctly
        //generate test automatc  Run -> Record Test Espresso
    }


}