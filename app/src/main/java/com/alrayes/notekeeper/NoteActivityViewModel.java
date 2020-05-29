package com.alrayes.notekeeper;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {

    public static  final String ORIGINAL_NOTE_COURSE_ID = "com.alrayes.notekeeper.ORIGINAL_NOTE_COURSE_ID";
    public static  final String ORIGINAL_NOTE_COURSE_TITLE= "com.alrayes.notekeeper.ORIGINAL_NOTE_COURSE_TITLE";
    public static  final String ORIGINAL_NOTE_COURSE_TEXT= "com.alrayes.notekeeper.ORIGINAL_NOTE_COURSE_TEXT";


    public String mOriginalNoteCourseId;
    public String mOriginalNoteTitle;
    public String mOriginalNoteText;

    public boolean mIsNewlyCreated = true;
    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID , mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_COURSE_TITLE , mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_COURSE_TEXT , mOriginalNoteText);


    }

    public void restoreState(Bundle inState) {

     mOriginalNoteCourseId = inState.getString(ORIGINAL_NOTE_COURSE_ID);
    mOriginalNoteTitle = inState.getString(ORIGINAL_NOTE_COURSE_TITLE);
   mOriginalNoteText = inState.getString(ORIGINAL_NOTE_COURSE_TEXT);
    }
}
