package com.example.adminiicp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateElectionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText election_title;
    EditText president_candidate1;
    EditText president_candidate2;
    EditText vice_president_candidate1;
    EditText vice_president_candidate2;
    Button date;
    Button time;
    Button submit;
    int month, year, dayOfMonth, hourOfDay, minute;
    boolean selected = false;
    boolean selected_time = false;
    String election_id;

    DatabaseReference electionDbRef, eventsDbRef, database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_election);

        election_title = findViewById(R.id.title);
        president_candidate1 = findViewById(R.id.candidate);
        president_candidate2 = findViewById(R.id.candidate1);
        vice_president_candidate1 = findViewById(R.id.candidate3);
        vice_president_candidate2 = findViewById(R.id.candidate4);

        date = findViewById(R.id.date_picker);
        time = findViewById(R.id.time_picker);
        submit = findViewById(R.id.submit);

        electionDbRef = FirebaseDatabase.getInstance().getReference().child("election_event");
        eventsDbRef = FirebaseDatabase.getInstance().getReference().child("events");
        election_id = electionDbRef.push().getKey();
        database = FirebaseDatabase.getInstance().getReference("user_events");

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
                insertElectionData();
            }
        });
    }

    private void insertElectionData() {
        String electionTitle = election_title.getText().toString();
        String p_candidate1 = president_candidate1.getText().toString();
        String p_candidate2 = president_candidate2.getText().toString();
        String vp_candidate1 = vice_president_candidate1.getText().toString();
        String vp_candidate2 = vice_president_candidate2.getText().toString();

        if(TextUtils.isEmpty(electionTitle) || TextUtils.isEmpty(p_candidate1) || TextUtils.isEmpty(p_candidate2) || TextUtils.isEmpty(vp_candidate1) || TextUtils.isEmpty(vp_candidate2) || selected==false || selected_time==false){
            Toast.makeText(CreateElectionActivity.this, "Please enter all fields",
                    Toast.LENGTH_SHORT).show();
        }else{
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        dataSnapshot.child(election_id).getRef().setValue(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            ElectionEvent petitions = new ElectionEvent(electionTitle, p_candidate1, p_candidate2, vp_candidate1, vp_candidate2, month+1, year, dayOfMonth, hourOfDay, minute);
            electionDbRef.child(election_id).setValue(petitions);
            Event petitionEvent = new Event(election_id, electionTitle, ServerValue.TIMESTAMP,"election", month+1, year, dayOfMonth, hourOfDay, minute);
            eventsDbRef.push().setValue(petitionEvent);
            Toast.makeText(CreateElectionActivity.this, "Created successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreateElectionActivity.this, AdminHomeActivity.class));
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