package com.alrayes.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter noteRecyclerAdapter;

    // private ArrayAdapter<NoteInfo> adapterNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDisplayContent();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));

            }
        });
    }

    private void initializeDisplayContent() {
  /*      final ListView listNotes = findViewById(R.id.list_note);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        adapterNote = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notes);
        listNotes.setAdapter(adapterNote);

        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this , NoteActivity.class);
                 NoteInfo note = (NoteInfo)  listNotes.getItemAtPosition(position);
                 // intent.putExtra(NoteActivity.NOTE_INFO , note); to learn how to use Parcelable
                intent.putExtra(NoteActivity.NOTE_POSITION , position); // use is insted of NOTE_INFO as we have DataManger singletone
               startActivity(intent);
            }
        });*/

        final RecyclerView recyclerNotes = findViewById(R.id.list_note);
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        recyclerNotes.setLayoutManager(notesLayoutManager);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        noteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        recyclerNotes.setAdapter(noteRecyclerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  adapterNote.notifyDataSetChanged();
        noteRecyclerAdapter.notifyDataSetChanged();
    }

}
