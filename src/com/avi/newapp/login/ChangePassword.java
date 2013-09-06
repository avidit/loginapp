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

public class ChangePassword extends Activity implements OnClickListener{

	TextView title;
	EditText old_pwd, new_pwd, re_pwd;
	Button change_pwd, dont_changepwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		title=(TextView)findViewById(R.id.title_change_pwd);
		old_pwd=(EditText)findViewById(R.id.oldpasswordtochange);
		new_pwd=(EditText)findViewById(R.id.giveanewpassword);
		re_pwd=(EditText)findViewById(R.id.newpasswordagain);
		change_pwd=(Button)findViewById(R.id.dunkin);
		dont_changepwd=(Button)findViewById(R.id.dont_changepassword);
		old_pwd.requestFocus();	
		change_pwd.setOnClickListener(this);
		dont_changepwd.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String enteredPassword=old_pwd.getText().toString();
		String new_pass=new_pwd.getText().toString();
		String re_pass=re_pwd.getText().toString();

		if(v==change_pwd)
		{
			if((enteredPassword.length()==0))
			{
				Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
				return;
			}
			if((new_pass.length()==0))
			{
				Toast.makeText(this, "Please enter a new password", Toast.LENGTH_LONG).show();
				return;
			}
			if((re_pass.length()==0))
			{
				Toast.makeText(this, "Please re-enter new password", Toast.LENGTH_LONG).show();
				return;
			}
			if(new_pass.compareTo(re_pass)!=0)
			{
				Toast.makeText(this, "Password Mismatch !!", Toast.LENGTH_LONG).show();
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

				if(enteredPassword.compareTo(existingPassword) != 0 )
				{
					Toast.makeText(this,"Wrong Password !!",Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					SharedPreferences.Editor e = settings.edit();
					key = "" + passKey + Register.USERPASSWORD_KEY;
					e.putString(key, new_pass);
					e.commit();

					Toast.makeText(this, "Password changed, Please login again !!", Toast.LENGTH_SHORT).show();
					Intent myint = new Intent(this,MainActivity.class);
					startActivity(myint);
				}
			}
		}
		else if(v==dont_changepwd)
		{
			Toast.makeText(this, "Password not changed", Toast.LENGTH_SHORT).show();
			Intent myint=new Intent(this,Welcome.class);
			startActivity(myint);

		}
	}

}
