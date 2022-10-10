package com.example.adminiicp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultPetitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = "", description="", count = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_petition);
        TextView name = findViewById(R.id.textView4);
        TextView desc = findViewById(R.id.textView);
        TextView petition_no = findViewById(R.id.textView5);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
           title = extras.getString("title");
           description = extras.getString("description");
           count = extras.getString("count");
        }
        name.setText(title);
        desc.setText(description);
        petition_no.setText(count);
    }
}