package com.example.adminiicp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        TextView name = findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
           title = extras.getString("title");
        }
        name.setText(title);
    }
}