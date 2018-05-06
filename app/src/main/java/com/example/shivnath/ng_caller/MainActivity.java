package com.example.shivnath.ng_caller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void user_nam_submit(View view) {
        TextView user_name = findViewById(R.id.user_name);
        String user  = user_name.getText().toString();
        if (user.matches("")){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;

        }else {
            Intent intent1 = new Intent(MainActivity.this, home.class);
            intent1.putExtra("add", user);
            startActivity(intent1);
        }
    }


    public void firestore(View view) {

        Intent intent = new Intent(MainActivity.this, firestore.class);
        startActivity(intent);
    }
}
