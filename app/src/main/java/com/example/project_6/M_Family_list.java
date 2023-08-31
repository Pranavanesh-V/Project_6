package com.example.project_6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class M_Family_list extends AppCompatActivity {


    private final List<String> dataList = new ArrayList<>();
    String S_Number;

    private HashMap<String,String> data=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfamily_list);

        ListView listView = findViewById(R.id.listView);

        // Retrieve data from Firebase
        //mRef=FirebaseDatabase.getInstance().getReference().child("Project_6").child("Family");
        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference().child("Project_6").child("Family");

        // Retrieve data from Firebase
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String item = snapshot.getValue(String.class);
                    String item=snapshot.getKey();
                    dataList.add(item);
                }

                // Display data in ListView using ArrayAdapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(M_Family_list.this,
                        android.R.layout.simple_list_item_1, dataList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        // Set ListView item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = dataList.get(position);

                // Handle item click, e.g., open a new activity or show details

                // Construct the DatabaseReference using parent value and child key
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Project_6").child("Family");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         S_Number= dataSnapshot.child(selectedItem).child("Phone").getValue().toString();
                        System.out.println("User's name: " + S_Number);
                        Intent intent=new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:"+S_Number));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors
                    }
                });


            }
        });
    }
}