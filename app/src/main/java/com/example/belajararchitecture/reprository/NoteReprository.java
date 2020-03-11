package com.example.belajararchitecture.reprository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.belajararchitecture.database.NoteDao;
import com.example.belajararchitecture.database.NoteDatabase;
import com.example.belajararchitecture.model.Note;

import java.util.List;

public class NoteReprository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    public NoteReprository(Application application){
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allnotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyntask(noteDao).execute(note);

    }

    public void update(Note note){
        new UpdateNoteAsyntask(noteDao).execute(note);

    }

    public void delete(Note note){
        new DeleteNoteAsyntask(noteDao).execute(note);

    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyntask(noteDao).execute();

    }


    public LiveData<List<Note>> getAllNotes() {
        return allnotes;
    }

    private static class InsertNoteAsyntask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private InsertNoteAsyntask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyntask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private UpdateNoteAsyntask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyntask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private DeleteNoteAsyntask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyntask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private DeleteAllNoteAsyntask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
