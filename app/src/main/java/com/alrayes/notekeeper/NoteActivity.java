package com.alrayes.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

public class NoteActivity extends AppCompatActivity {

    public static final String NOTE_INFO = "NOTE_INFO";
    public static final String NOTE_POSITION = "NOTE_POSITION"; // use it rather than NOTE_INFO
    public static final int POSITION_NOT_SET = -1;

    private NoteInfo mNote;
    private boolean mIsNewNote;
    private Spinner spinner;
    private EditText textNoteTitle;
    private EditText textNoteText;
    private int notePosition;
    private boolean isCanceling;
    private ArrayAdapter<CourseInfo> adapterCourses;
    /*   private String mOriginalNoteCourseId;
       private String mOriginalNoteTitle;
       private String mOriginalNoteText;*/
    private NoteActivityViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mViewModel = viewModelProvider.get(NoteActivityViewModel.class);

        if (mViewModel.mIsNewlyCreated && savedInstanceState != null) {
            mViewModel.restoreState(savedInstanceState);
        }
        mViewModel.mIsNewlyCreated = false;

        spinner = findViewById(R.id.spinner_courses);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCourses);

        readDisplayStateValues();
        saveOriginalNoteValues();


        textNoteTitle = findViewById(R.id.text_note_title);
        textNoteText = findViewById(R.id.text_note_text);
        if (!mIsNewNote)
            displayNote(spinner, textNoteTitle, textNoteText);
    }

    private void saveOriginalNoteValues() {
        if (mIsNewNote)
            return;
        mViewModel.mOriginalNoteCourseId = mNote.getCourse().getCourseId();
        mViewModel.mOriginalNoteTitle = mNote.getTitle();
        mViewModel.mOriginalNoteText = mNote.getText();


    }

    private void displayNote(Spinner spinner, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinner.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());

    }

    private void readDisplayStateValues() {

        Intent intent = getIntent();
        //  mNote = intent.getParcelableExtra(NOTE_INFO);
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        //  mIsNewNote = mNote == null;

        mIsNewNote = position == POSITION_NOT_SET;

        if (mIsNewNote) {
            creatNewNote();
        } else {
            mNote = DataManager.getInstance().getNotes().get(position);
        }
    }

    private void creatNewNote() {
        DataManager dm = DataManager.getInstance();
        notePosition = dm.createNewNote();
        mNote = dm.getNotes().get(notePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCanceling) {
            //when   back without saving new node
            if (mIsNewNote) {
                DataManager.getInstance().removeNote(notePosition);
            } else {
                storePreviousNoteValue();
            }
        } else {
            saveNote();

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            mViewModel.saveState(outState);
        }
    }

    private void storePreviousNoteValue() {
        CourseInfo course = DataManager.getInstance().getCourse(mViewModel.mOriginalNoteCourseId);
        mNote.setCourse(course);
        mNote.setTitle(mViewModel.mOriginalNoteTitle);
        mNote.setText(mViewModel.mOriginalNoteText);

    }

    private void saveNote() {
        mNote.setCourse((CourseInfo) spinner.getSelectedItem());
        mNote.setTitle(textNoteTitle.getText().toString());
        mNote.setText(textNoteText.getText().toString());
    }

// used to  disable or enable next action on bar menue
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        int lastNoteIndex = DataManager.getInstance().getNotes().size() -1;
        item.setEnabled(notePosition <lastNoteIndex); // diable next if we reach last note

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {

            isCanceling = true;
            finish();

        } else if (id == R.id.action_next) {
            moveNext();
        }

        return super.onOptionsItemSelected(item);
    }

    private void moveNext() {

        saveNote();

        ++notePosition;
        mNote = DataManager.getInstance().getNotes().get(notePosition);
        saveOriginalNoteValues();
        displayNote(spinner, textNoteTitle, textNoteText);
        invalidateOptionsMenu();

    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) spinner.getSelectedItem();

        String subject = textNoteTitle.getText().toString();
        String Text = "Checkout what i learnes in the pluralSight \"" +
                course.getTitle() + "\"\n" + textNoteText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822"); //"message/rfc2822" it's standard internet mime type for sending email
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, Text);
        startActivity(intent);


    }
}
