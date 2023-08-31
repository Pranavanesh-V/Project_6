package com.example.project_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Daily_process extends AppCompatActivity {

    Button SOS,Family;
    String S_Number,S_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_process);

        SOS=findViewById(R.id.M_SOS);
        Family=findViewById(R.id.M_F_Con);

        SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Project_6").child("SOS");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                            S_Number = datasnapshot.child("person01").child("Phone No").getValue(String.class);
                            S_Name = datasnapshot.child("person01").child("user").getValue(String.class);

                        Intent intent=new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:"+S_Number));
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Daily_process.this, M_Family_list.class);
                startActivity(intent);
            }
        });
    }
}