package com.example.admin.amex;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class Permanent extends Activity {

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permanent);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        userID = intent.getStringExtra("UserID");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Toast.makeText(Permanent.this, "Request Sent", Toast.LENGTH_LONG).show();
        AlertDialog alertDialog = new AlertDialog.Builder(
                Permanent.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Request Info");
        Random r = new Random();
        int CCP = r.nextInt(99999 - 11111 + 1) + 11111;
        int OTP = r.nextInt(99999 - 11111 + 1) + 11111;
        // Setting Dialog Message
        alertDialog.setMessage("CCP Number-" + CCP + "\n CCP name-Amex \n OTP-" + OTP);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_permanent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void logout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
