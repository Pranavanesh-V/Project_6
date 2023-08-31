package com.example.project_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Family_List extends AppCompatActivity {
    TextInputLayout Name,Phone_no;
    EditText E_name,E_ph_no;
    String S_Name,S_Ph_no;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);

        //Ids
        Name=findViewById(R.id.Fam_Name);
        Phone_no=findViewById(R.id.Fam_ph);
        submit=findViewById(R.id.submit_fam);

        //Edit Texts
        E_name=Name.getEditText();
        E_ph_no=Phone_no.getEditText();

        TextWatcher login = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                S_Name=E_name.getText().toString().trim();
                S_Ph_no=E_ph_no.getText().toString().trim();

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        E_name.addTextChangedListener(login);
        E_ph_no.addTextChangedListener(login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (S_Ph_no.length()>10)
                {
                    Toast.makeText(Family_List.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Add the data to db
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Project_6").child("Family");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                            if (datasnapshot.child(S_Name).exists())
                            {
                                Toast.makeText(Family_List.this, "This Person Already Exists", Toast.LENGTH_LONG).show();

                            }
                            else {
                                // Push data to a new unique key
                                reference.child(S_Name).child("Phone").setValue(S_Ph_no);

                                Toast.makeText(Family_List.this,"Event Added", Toast.LENGTH_SHORT).show();
                            }

                            //clear the input fields
                            E_name.setText("");
                            E_ph_no.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



    }
}