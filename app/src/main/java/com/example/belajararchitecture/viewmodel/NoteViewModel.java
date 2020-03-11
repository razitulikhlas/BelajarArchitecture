package com.example.belajararchitecture.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.belajararchitecture.model.Note;
import com.example.belajararchitecture.reprository.NoteReprository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteReprository reprository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        reprository = new NoteReprository(application);
        allNotes = reprository.getAllNotes();
    }

    public void insert(Note note){
        reprository.insert(note);
    }

    public void update(Note note){
        reprository.update(note);
    }

    public void delete(Note note){
        reprository.delete(note);
    }

    public void deleteAllNotes(){
        reprository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

}
