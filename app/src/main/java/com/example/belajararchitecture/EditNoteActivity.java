package com.example.belajararchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID    = "com.example.belajararchitecture.ID";
    public static final String EXTRA_TITLE    = "com.example.belajararchitecture.TITLE";
    public static final String EXTRA_DESC     = "com.example.belajararchitecture.DESC";
    public static final String EXTRA_PRIORITY = "com.example.belajararchitecture.PRIORITY";

    private EditText Edtitle;
    private EditText Eddesc;
    private NumberPicker numberPickerPriority;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Edtitle = findViewById(R.id.ed_title);
        Eddesc = findViewById(R.id.ed_desc);
        numberPickerPriority = findViewById(R.id.number_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            Edtitle.setText(intent.getStringExtra(EXTRA_TITLE));
            Eddesc.setText(intent.getStringExtra(EXTRA_DESC));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else {
            setTitle("Add Note");
        }
    }

    private void SaveNote(){
        String title = Edtitle.getText().toString();
        String desc  = Eddesc.getText().toString();
        int priority = numberPickerPriority.getValue();

        if(title.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title  and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESC,desc);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);

        if(id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                SaveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
