package com.avi.newapp.login;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	static String enteredUsername;
	String enteredPassword;
	TextView welcome;
	EditText username, password;
	Button newuser, login;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		welcome=(TextView)findViewById(R.id.welcome);
		username=(EditText)findViewById(R.id.username);
		password=(EditText)findViewById(R.id.oldpassword);
		newuser=(Button)findViewById(R.id.new_user);
		login=(Button)findViewById(R.id.login);

		newuser.setOnClickListener(this);
		login.setOnClickListener(this);

	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		username.setText("");
		password.setText("");
	}

	public void onClick(View v) {
		if(v==login)
		{
			enteredUsername = username.getText().toString();
			enteredPassword = password.getText().toString();

			if(enteredUsername.length() == 0)
			{
				Toast.makeText(this,"Please Enter Username",Toast.LENGTH_LONG).show();
				return;
			}

			if(enteredPassword.length() == 0)
			{
				Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
				return;
			}

			SharedPreferences settings = getSharedPreferences(Register.my_pref, 0);
			int counter = settings.getInt(Register.USER_COUNT, 0);
			boolean match = false;
			int passKey = 0;
			for ( int i = 1; i<=counter; i++)	//This loop will continue till it checks against all the stored values even after it finds the correct match. 
			{
				String userKey = Integer.toString(i);
				String savedUser = settings.getString(userKey, "");
				if(savedUser.equals(enteredUsername))
				{
					match = true;
					passKey = i;
					SharedPreferences.Editor e = settings.edit();
					e.putString(SpHelper.LOGGED_USER, enteredUsername);
					
					String key = passKey + Register.PATH_KEY;
					String pathstring = settings.getString(key, "");
					e.putString(SpHelper.PATH_KEY, pathstring);
					e.commit();
				}
			}
			if(match == false)
			{
				Toast.makeText(this,"User does't exist",Toast.LENGTH_LONG).show();
				return;
			}
			String key;
			key = passKey + Register.USERPASSWORD_KEY;
			String existingPassword = settings.getString(key, "");		

			if(enteredPassword.compareTo(existingPassword) !=0 )
			{
				Toast.makeText(this,"Wrong Password !!",Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
				Toast.makeText(this, "Redirecting to Welcome page", Toast.LENGTH_SHORT).show();
				Intent myint=new Intent(this,Welcome.class);
				startActivity(myint);
			}
		}


		else if(v==newuser)
		{
			Intent myint=new Intent(this,Register.class);
			startActivity(myint);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
