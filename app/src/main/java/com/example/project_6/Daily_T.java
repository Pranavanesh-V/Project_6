package com.example.project_6;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Daily_T extends AppCompatActivity {

    TextInputEditText E_time;
    TextInputLayout T_time,event;
    String selectedTime,S_event;
    Button submit,close;
    EditText E_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_t);

        T_time=findViewById(R.id.Time_s);
        event=findViewById(R.id.event);
        E_time =findViewById(R.id.E_time);
        submit=findViewById(R.id.submit);
        close=findViewById(R.id.close);

        E_time.setOnClickListener(view -> showTimePickerDialog());
        //edit text in InputTextLayout
        E_event=event.getEditText();
        assert E_event != null;
        TextWatcher login = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                S_event=E_event.getText().toString().trim();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        E_event.addTextChangedListener(login);

        //submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //test
                //Toast.makeText(Daily_T.this, S_event+"\n"+selectedTime, Toast.LENGTH_SHORT).show();

                //Add the data to db
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Project_6").child("Events");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        if (datasnapshot.child(selectedTime).exists())
                        {
                            Toast.makeText(Daily_T.this, "An event already exists at that\n" +
                                    "time...Try using a minute next!!", Toast.LENGTH_LONG).show();

                        }
                        else {
                            // Push data to a new unique key
                            reference.child(selectedTime).child("Event").setValue(S_event);

                            Toast.makeText(Daily_T.this, "Event Added", Toast.LENGTH_SHORT).show();
                        }

                        //clear the input fields
                        E_time.setText("");
                        E_event.setText("");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Daily_T.this, "All Events Added\nSuccessfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Daily_T.this,
                (view, hourOfDay, minute1) -> {
                     selectedTime= hourOfDay + ":" + minute1;
                    E_time.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }
}