package com.example.project_6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    Button sos,monitor,daily_T,family_C;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch done;
    boolean isSwitchToggled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sos=findViewById(R.id.SOS);
        done=findViewById(R.id.done);
        monitor=findViewById(R.id.Monitor);
        daily_T=findViewById(R.id.Daily_T);
        family_C=findViewById(R.id.F_Con);

        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSwitchToggled = isChecked;
                if (isChecked) {
                    // Switch is toggled on, start the next activity
                    Intent intent = new Intent(Home.this, Daily_process.class);
                    startActivity(intent);
                }
                done.toggle();
            }
        });


        
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent serviceIntent = new Intent(Home.this, NotificationForegroundService.class);
                ContextCompat.startForegroundService(Home.this, serviceIntent);
                Toast.makeText(Home.this, "Scheduling notifications started.", Toast.LENGTH_SHORT).show();*/

                Intent intent=new Intent(Home.this, SOS.class);
                startActivity(intent);

            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this, Monitor_EXE.class);
                startActivity(intent);
            }
        });

        daily_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Home.this, Daily_T.class);
                startActivity(intent);
            }
        });

        family_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Family_List.class);
                startActivity(intent);
            }
        });
    }


}