package com.example.rtes_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText from,to,busType,busNum,stopName,stopTime;
    LinearLayout addStopLayout;
    Button submit,addStopBtn,submitStopBtn;
    DatabaseReference rootRef;
    String busNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from=findViewById(R.id.fromEditText);
        to=findViewById(R.id.toeditText);
        busType=findViewById(R.id.busTypeEditText);
        busNum=findViewById(R.id.busNumEditText);
        addStopLayout=findViewById(R.id.addStopLayout);
        submit=findViewById(R.id.submitBtn);
        addStopBtn=findViewById(R.id.addBtn);
        submitStopBtn=findViewById(R.id.submitStopBtn);
        stopName=findViewById(R.id.stopNameEditText);
        stopTime=findViewById(R.id.timeEditText);
        rootRef= FirebaseDatabase.getInstance().getReference().child("schedule");

        submit.setOnClickListener(view -> {
            busNumber=busNum.getText().toString();

            rootRef.push().setValue(busNumber);
            rootRef.child(busNumber).child("from").setValue(from.getText().toString());
            rootRef.child(busNumber).child("to").setValue(to.getText().toString());
            rootRef.child(busNumber).child("bustype").setValue(busType.getText().toString());
            addStopLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Add Stop Information Below",Toast.LENGTH_SHORT).show();
        });

        addStopBtn.setOnClickListener(view -> {
            String key=rootRef.child(busNumber).child("stops").push().getKey();
            rootRef.child(busNumber).child("stops").child(key).child("stopname").setValue(stopName.getText().toString());
            rootRef.child(busNumber).child("stops").child(key).child("time").setValue(stopTime.getText().toString()).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(),"Stop Added",Toast.LENGTH_SHORT).show();
            });
        });

        submitStopBtn.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Stop Information added successfully",Toast.LENGTH_SHORT).show();
            addStopLayout.setVisibility(View.INVISIBLE);
        });


    }
}