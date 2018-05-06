package com.example.shivnath.ng_caller;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class home extends AppCompatActivity {
    private static final String TAG = "FireLog" ;
    NotificationCompat.Builder notification;
    private static final int uniqId = 1;
    String potential_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        recive_data();


    }

    // recive data.....

    public void recive_data(){

        db.collection("Employes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null){
                    Log.d(TAG, "Error : "+e.getMessage());
                }

                for (DocumentSnapshot doc : documentSnapshots){
                    Bundle in = getIntent().getExtras();
                    String em  = in.getString("add");
                    TextView em_name = findViewById(R.id.em_name);
                    em_name.setText(em);
                    String user_name = doc.getString(em);
                    if (user_name == null){
                        Toast.makeText(home.this, "Employe does not exist", Toast.LENGTH_SHORT).show();
                    }else {
                        potential_name = user_name;
                        Toast.makeText(home.this, "Scrooll down your notification to call potential", Toast.LENGTH_SHORT).show();
                        notification();
                    }

                }
            }
        });
    }


    // notifiation

    public void notification() {
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setTicker("Heloo");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(potential_name);
        notification.setContentText("Tap! to Call Protential");

        Intent intent = new Intent(Intent.ACTION_CALL);

        if (ContextCompat.checkSelfPermission(home.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(home.this,
                    new String[]{Manifest.permission.CALL_PHONE},uniqId);

        }
        else {
            String dail = "tel:" + potential_name;
            intent.setData(Uri.parse(dail));

        }



        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqId,notification.build());

        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.so);
        notification.setSound(sound);


    }
}
