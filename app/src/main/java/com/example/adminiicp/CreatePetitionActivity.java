package com.example.adminiicp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class CreatePetitionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText petition_title;
    EditText description;
    Button date;
    Button time;
    Button submit;
    int month, year, dayOfMonth, hourOfDay, minute;
    boolean selected = false;
    boolean selected_time = false;

    DatabaseReference petitionDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_petition);

        petition_title = findViewById(R.id.title);
        description = findViewById(R.id.option1);
        date = findViewById(R.id.date_picker);
        time = findViewById(R.id.time_picker);
        submit = findViewById(R.id.submit);

        petitionDbRef = FirebaseDatabase.getInstance().getReference().child("petition_event");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPetitionData();
            }
        });
    }

    public void insertPetitionData(){
        String petitionTitle = petition_title.getText().toString();
        String petition_description = description.getText().toString();
        if(TextUtils.isEmpty(petitionTitle) || TextUtils.isEmpty(petition_description) || selected==false || selected_time==false){
            Toast.makeText(CreatePetitionActivity.this, "Please enter all fields",
                    Toast.LENGTH_SHORT).show();
        }else{
            PetitionEvent petitions = new PetitionEvent(petitionTitle, petition_description, month, year, dayOfMonth, hourOfDay, minute);
            petitionDbRef.push().setValue(petitions);
            Toast.makeText(CreatePetitionActivity.this, "Created successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreatePetitionActivity.this, AdminHomeActivity.class));
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        this.selected = true;
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Button date_button = (Button)findViewById(R.id.date_picker);
        date_button.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.selected_time = true;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        Button time_button = (Button)findViewById(R.id.time_picker);
        if (minute < 10){
            time_button.setText( hourOfDay + ":0" + minute);
        }
        else {
            time_button.setText(hourOfDay + ":" + minute);
        }
    }
}