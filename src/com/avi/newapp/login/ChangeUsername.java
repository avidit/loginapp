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



public class ChangeUsername extends Activity implements OnClickListener{

	TextView title;
	EditText new_un, pwd;
	Button change_un, dont_changeun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_username);

		title=(TextView)findViewById(R.id.title_change_user);
		new_un=(EditText)findViewById(R.id.newusername);
		pwd=(EditText)findViewById(R.id.oldpassword);
		change_un=(Button)findViewById(R.id.changepassword);
		dont_changeun=(Button)findViewById(R.id.dont_changeusername);

		change_un.setOnClickListener(this);
		dont_changeun.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_username, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==change_un)
		{	
			String enteredPassword=pwd.getText().toString();
			String newUsername=new_un.getText().toString();

			if((newUsername.length()==0))
			{
				Toast.makeText(this, "Please enter a new username", Toast.LENGTH_LONG).show();
				return;
			}
			if((enteredPassword.length()==0))
			{
				Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
				SharedPreferences settings = getSharedPreferences(Register.my_pref, 0);
				int counter = settings.getInt(Register.USER_COUNT, 0);
				int passKey = 0;
				for ( int i = 1; i<=counter; i++)
				{
					String userKey = Integer.toString(i);
					String savedUser = settings.getString(userKey, "");
					if(savedUser.equals(MainActivity.enteredUsername))
					{
						passKey = i;
					}
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

					SharedPreferences.Editor e=settings.edit();
					e.putString("" + passKey, newUsername);
					e.commit();

					Toast.makeText(this, "Username changed, Please login again !!", Toast.LENGTH_SHORT).show();
					Intent myint=new Intent(this,MainActivity.class);
					startActivity(myint);
				}
			}
		}

		else if(v==dont_changeun)
		{
			Toast.makeText(this, "Username not changed", Toast.LENGTH_SHORT).show();
			Intent myint=new Intent(this,Welcome.class);
			startActivity(myint);
		}

	}
}
