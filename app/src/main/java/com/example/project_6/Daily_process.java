package com.example.project_6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Daily_process extends AppCompatActivity {

    Button SOS,Family;
    String S_Number,S_Name;
    private  static final int REQUEST_CALL=1;

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

                        CallButton();

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

    private  void CallButton()
    {
        if (ContextCompat.checkSelfPermission(Daily_process.this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Daily_process.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else
        {
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+S_Number));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallButton();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
