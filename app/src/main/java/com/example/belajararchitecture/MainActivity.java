package com.example.belajararchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.belajararchitecture.adapter.NoteAdapter;
import com.example.belajararchitecture.model.Note;
import com.example.belajararchitecture.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rcv_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
//                update recyclerview
                noteAdapter.submitList(notes);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note succes delete ^_^", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListeners(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(EditNoteActivity.EXTRA_DESC, note.getDescription());
                intent.putExtra(EditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {


                String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
                String desc = data.getStringExtra(EditNoteActivity.EXTRA_DESC);
                int priority = data.getIntExtra(EditNoteActivity.EXTRA_PRIORITY, 1);

                Note note = new Note(title, desc, priority);
                noteViewModel.insert(note);
            }
            Toast.makeText(this, "Note Save ^_^", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            if(data != null){
                int id = data.getIntExtra(EditNoteActivity.EXTRA_ID,-1);
                if(id == -1 ){
                    Toast.makeText(this, "Note Can't be update", Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
                String desc = data.getStringExtra(EditNoteActivity.EXTRA_DESC);
                int priority = data.getIntExtra(EditNoteActivity.EXTRA_PRIORITY, 1);
                Note note =new Note(title,desc,priority);
                note.setId(id);
                noteViewModel.update(note);
                Toast.makeText(this, "Note Succes Update ^_^", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Note not save :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_note) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(this, "All note succes delete ^_^ ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
