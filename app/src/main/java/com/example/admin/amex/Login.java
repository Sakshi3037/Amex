package com.example.admin.amex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class Login extends Activity {

    DataBaseAdapter loginDataBaseAdapter;

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
}
