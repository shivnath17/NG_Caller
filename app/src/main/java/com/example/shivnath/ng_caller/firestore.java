package com.example.shivnath.ng_caller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class firestore extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView employe_name, potential_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);
    }

    public void save_data(View view) {
        employe_name = findViewById(R.id.employe_name);
        potential_name = findViewById(R.id.potential_name);
        String em = employe_name.getText().toString();
        String pn = potential_name.getText().toString();
        if (em.matches("")) {
            Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
        } else {
            if (pn.matches("")) {
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
            } else {
                final Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put(em, pn);
                db.collection("Employes").document(em).set(dataToSave);
                Intent inten = new Intent(firestore.this, firestore.class);
                startActivity(inten);
                Toast.makeText(this, "Details successfully submitted", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
