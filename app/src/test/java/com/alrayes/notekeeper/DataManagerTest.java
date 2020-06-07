package com.alrayes.notekeeper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    static DataManager sDataManger;

    /**
     * @BeforeClass must be static
     */
    @BeforeClass
    public static void classSetup() {
        sDataManger = DataManager.getInstance();
    }


    /*
     *  we make this fuunction because
     *  createNewNote() and  findSimilarNotes()
     *   make issue
     *   so this function
     * make test run in a consistent environment
     * */
    @Before
    public void setup() {
        sDataManger.getNotes().clear();
        sDataManger.initializeExampleNotes();
    }

    @Test
    public void createNewNote() {

        final CourseInfo course = sDataManger.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";

        int noteIndex = sDataManger.createNewNote();
        NoteInfo newNote = sDataManger.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = sDataManger.getNotes().get(noteIndex);
        assertEquals(compareNote.getCourse(), course);
        assertEquals(compareNote.getTitle(), noteTitle);
        assertEquals(compareNote.getText(), noteText);
    }

    /* this function if we not use setup befor will
         give wornge result  so we have to use
       setup befor
    * */
    @Test
    public void findSimilarNotes() {
        final CourseInfo course = sDataManger.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText1 = "This is the body text of my test note";
        final String noteText2 = "This is the body of my second test note";

        int noteIndex1 = sDataManger.createNewNote();
        NoteInfo newNote1 = sDataManger.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = sDataManger.createNewNote();
        NoteInfo newNote2 = sDataManger.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = sDataManger.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = sDataManger.findNote(newNote2);
        assertEquals(noteIndex2, foundIndex2);
    }
    @Test
    public void createNewNoteOneStepCreation() {
        final CourseInfo course = sDataManger.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of my test note";

        int noteIndex = sDataManger.createNewNote(course, noteTitle, noteText);

        NoteInfo compareNote = sDataManger.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }
}