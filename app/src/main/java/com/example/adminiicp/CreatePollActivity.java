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
import android.widget.TextView;
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

public class CreatePollActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText poll_title;
    EditText option1;
    EditText option2;
    EditText option3;
    Button date;
    Button time;
    Button submit;
    int month, year, dayOfMonth, hourOfDay, minute;
    boolean selected = false;
    boolean selected_time = false;
    String poll_id;

    DatabaseReference pollDbRef, eventsDbRef, database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        poll_title = findViewById(R.id.title);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        date = findViewById(R.id.date_picker);
        time = findViewById(R.id.time_picker);
        submit = findViewById(R.id.submit);

        pollDbRef = FirebaseDatabase.getInstance().getReference().child("poll_event");
        eventsDbRef = FirebaseDatabase.getInstance().getReference().child("events");
        poll_id = pollDbRef.push().getKey();
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
                insertPollData();
            }
        });
    }
        public void insertPollData(){
        String pollTitle = poll_title.getText().toString();
        String optionOne = option1.getText().toString();
        String optionTwo = option2.getText().toString();
        String optionThree = option3.getText().toString();
        if(TextUtils.isEmpty(pollTitle) || TextUtils.isEmpty(optionOne) || TextUtils.isEmpty(optionTwo)|| TextUtils.isEmpty(optionThree) || selected==false || selected_time==false){
            Toast.makeText(CreatePollActivity.this, "Please enter all fields",
                    Toast.LENGTH_SHORT).show();
        }else{
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        dataSnapshot.child(poll_id).getRef().setValue(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        PollEvent polls = new PollEvent(pollTitle, optionOne, optionTwo, optionThree, month, year, dayOfMonth, hourOfDay, minute);
        pollDbRef.child(poll_id).setValue(polls);
        Event pollEvent = new Event(poll_id, pollTitle, ServerValue.TIMESTAMP,"poll", month, year, dayOfMonth, hourOfDay, minute);
        eventsDbRef.push().setValue(pollEvent);
            Toast.makeText(CreatePollActivity.this, "Created successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreatePollActivity.this, AdminHomeActivity.class));
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