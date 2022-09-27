package com.example.tyrecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    Button back,align,opencv;
    TextView fl,fr,rl,rr;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UID= mAuth.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //back = findViewById(R.id.back_button);
        align = findViewById(R.id.align_activity);
        opencv = findViewById(R.id.opencv_activity);
        fl = findViewById(R.id.fl);
        fr = findViewById(R.id.fr);
        rl = findViewById(R.id.rl);
        rr = findViewById(R.id.rr);

        align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alignment = new Intent(MainActivity.this, alignment_activity.class);
                startActivity(alignment);
            }
        });
        opencv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ocv = new Intent(MainActivity.this, opencv3.class);
                startActivity(ocv);
            }
        });
String datapath="users/"+UID;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(datapath);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("fl").exists()) {
                        String flt = snapshot.child("fl").getValue().toString();
                        fl.setText("Front-Left:\n"+flt+"°");
                        fl.setTypeface(fl.getTypeface(), Typeface.BOLD);

                    } else {
                        //
                    }

                    if (snapshot.child("fr").exists()) {
                        String frt = snapshot.child("fr").getValue().toString();
                        fr.setText("Front-Right:\n"+frt+"°");
                        fr.setTypeface(fr.getTypeface(), Typeface.BOLD);

                    } else {
                        //
                    }

                    if (snapshot.child("rl").exists()) {
                        String rlt = snapshot.child("rl").getValue().toString();
                        rl.setText("Rear-Left:\n"+rlt+"°");
                        rl.setTypeface(rl.getTypeface(), Typeface.BOLD);
                    } else {
                        //
                    }

                    if (snapshot.child("rr").exists()) {
                        String rrt = snapshot.child("rr").getValue().toString();
                        rr.setText("Rear-Right:\n"+rrt+"°");
                        rr.setTypeface(rr.getTypeface(), Typeface.BOLD);
                    } else {
                        //
                    }

                } else {
                    Toast.makeText(MainActivity.this, "DATABASE ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}