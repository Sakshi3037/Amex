package com.example.admin.amex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Random;

public class Login extends Activity {

    DataBaseAdapter loginDataBaseAdapter;
    LayoutInflater inflater;
    EditText accountNum;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            loginDataBaseAdapter=new DataBaseAdapter(this);
            loginDataBaseAdapter=loginDataBaseAdapter.open();
            //Hard coded values
            loginDataBaseAdapter.insertEntry("C2242", "Sakshi","sakshi95goyal@gmail.com","1a2b3c4d");
            loginDataBaseAdapter.insertEntry("C2243","Jyoti","jyotikadian94@gmail.com","1a2b3c4d");
        } catch (SQLException e) {
            e.printStackTrace();
        }
         inflater = this.getLayoutInflater();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }
    public void login(View view) {
        final  EditText editTextUserID=(EditText)findViewById(R.id.userid);
        final  EditText editTextPassword=(EditText)findViewById(R.id.password);
        String userID=editTextUserID.getText().toString();
        String password=editTextPassword.getText().toString();
        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userID);
        if(password.equals(storedPassword))
        {
            Intent intent = new Intent(this, BlockCard.class);
            intent.putExtra("UserID",userID);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Login.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
        }
    }
    public void oneClick (View view) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                Login.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("OTP");
        Random r = new Random();
        final int OTP = r.nextInt(99999 - 11111 + 1) + 11111;
        // Setting Dialog Message
        alertDialog.setMessage("Your OTP is " + OTP + " and the same has been sent to your phone");
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
                View dialogView = inflater.inflate(R.layout.block_card, null);
                dialogBuilder.setView(dialogView);
                Button smsSend = (Button) dialogView.findViewById(R.id.button4);
                accountNum = (EditText) dialogView.findViewById(R.id.card);
                textView = (TextView) dialogView.findViewById(R.id.textView2);
                smsSend.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        final String details = accountNum.getText().toString();
                        if (details.isEmpty() || (!(Integer.parseInt(details.substring(4)) == OTP))) {
                            textView.setText("Please enter valid details");

                        } else {
                            textView.setText("");
                          SmsManager sm = SmsManager.getDefault();
                          sm.sendTextMessage("8728887514", null, "Card Holder with details " + details + " has requested to block their card. " +
                                    "Please block their card", null, null);
                            Toast.makeText(Login.this, "Message sent successfully", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
