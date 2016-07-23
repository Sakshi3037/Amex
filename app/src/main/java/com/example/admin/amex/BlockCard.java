package com.example.admin.amex;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;

public class BlockCard extends Activity {
    String userID;
    DataBaseAdapter loginDataBaseAdapter;
    String username;
    String email;
    GmailSender sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_card);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        userID = intent.getStringExtra("UserID");
        loginDataBaseAdapter=new DataBaseAdapter(this);
        sender = new GmailSender("sakshi95goyal@gmail.com", "music231427");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_block_card, menu);
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

    public void temp_block(View view) {

        try {
            loginDataBaseAdapter=loginDataBaseAdapter.open();
            new MyAsyncClass().execute();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(BlockCard.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this,Temporary.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }

    public void perm_block(View view) {
        try {
            loginDataBaseAdapter=loginDataBaseAdapter.open();
            new MyAsyncClass().execute();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(BlockCard.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this,Permanent.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }
    public void logout(View  view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BlockCard.this);
            pDialog.setMessage("Sending Request...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                username = loginDataBaseAdapter.getUserName(userID);
                email = loginDataBaseAdapter.getEmail(userID);
                String body = "Hello " + username + "\n Your Request to block card associated with userID " + userID + "" +
                        " has been received.\n\n Thanks \n\n AMEX";
                sender.sendMail("Request to Block Card", body, "sakshi95goyal@gmail.com", email);

            }
            catch (Exception ex) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
        }

    }
}
