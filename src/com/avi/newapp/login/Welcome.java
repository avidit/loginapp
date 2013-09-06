package com.avi.newapp.login;

import java.io.File;
import java.io.FileInputStream;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends Activity implements OnClickListener{

	TextView welcome_user;
	Button regusers;
	ImageView photo;
	Bitmap mybitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		welcome_user = (TextView)findViewById(R.id.welcome_new);
		regusers = (Button)findViewById(R.id.regusers);
		photo = (ImageView)findViewById(R.id.photo_thumb);

		regusers.setOnClickListener(this);

		SharedPreferences settings = getSharedPreferences(Register.my_pref, 0);
		{
			
			String name = settings.getString(SpHelper.LOGGED_USER, "");
			welcome_user.setText("Welcome " + name);

			String filestring = settings.getString(SpHelper.PATH_KEY, "");
			File imgFile = new File(filestring);
			FileInputStream fis = null;
			try 
			{
				fis = new FileInputStream(imgFile);
				if(mybitmap != null)
				{
					mybitmap.recycle();
				}
				mybitmap = BitmapFactory.decodeStream(fis);
				photo.setImageBitmap(mybitmap);
				fis.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) 
	{
		Toast.makeText(this, "Here are the list of Users", Toast.LENGTH_SHORT).show();
		Intent myint=new Intent(this,RegisteredUsers.class);
		startActivity(myint);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{ 
		switch (item.getItemId()) 
		{
		case R.id.username_menu:
			changeUsername();
			return true;

		case R.id.password_menu:
			changePassword();
			return true;

		case R.id.logout_menu:
			logout();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void changeUsername()
	{
		Toast.makeText(this, "Change your Username", Toast.LENGTH_SHORT).show();
		Intent myinta=new Intent(this,ChangeUsername.class);
		startActivity(myinta);
	}

	public void changePassword()
	{
		Toast.makeText(this, "Change your Password", Toast.LENGTH_SHORT).show();
		Intent myintb=new Intent(this,ChangePassword.class);
		startActivity(myintb);		
	}

	public void logout()
	{	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Logout !!");
		builder.setMessage("Are you sure ?");
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent myintb = new Intent(getBaseContext(), MainActivity.class);
				startActivity(myintb);
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		AlertDialog myalert = builder.create();
		myalert.show();
	}
}
