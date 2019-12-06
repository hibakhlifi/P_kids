package com.iset.P_kids;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Test extends AppCompatActivity {
    private FirebaseDatabase dataBase;
    private DatabaseReference myRef;
    EditText champ1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__test);

        champ1 = findViewById(R.id.champ);

        dataBase = FirebaseDatabase.getInstance();
        myRef = dataBase.getReference("message");
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.setValue(champ1.getText().toString());
                champ1.setText("");


            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String TAG = "test";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                        Toast.makeText(Activity_Test.this, value, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                        Toast.makeText(Activity_Test.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
