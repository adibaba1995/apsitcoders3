package com.apsitcoders.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button scan_btn;
    private TextView qr_code_txv;

    FirebaseDatabase database;
    DatabaseReference reference;

    private String contents;

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()) {
                Ticket ticket = dataSnapshot.getValue(Ticket.class);
                if(!ticket.isVerified()) {
                    ticket.setVerified(true);
                    reference.child(contents).setValue(ticket);
//                    reference.removeEventListener();
                } else {
                    Toast.makeText(MainActivity.this, "Ticket is verified", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("tickets");

        scan_btn = (Button) findViewById(R.id.scan_btn);
        qr_code_txv= (TextView) findViewById(R.id.qr_code_txv);


        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator= new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        final IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null)
        {
            if (result.getContents()==null)
            {
                Toast.makeText(this, "you cancelled scanning", Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                qr_code_txv.setText(result.getContents());

                contents = result.getContents();
                reference.child(result.getContents()).addValueEventListener(listener);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
