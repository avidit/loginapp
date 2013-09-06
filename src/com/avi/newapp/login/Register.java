package com.avi.newapp.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{

	TextView register;
	EditText firstfield, lastfield, username, emailfield, pwd, cpwd;
	Button ok, cancel;
	ImageView photo;
	ImageButton clicker;
	Uri myfileUri = null;
	Bitmap mybitmap = null;

	String first, last,email, pass, cpass, pathstring;
	public static String user;
	public static final String my_pref="pref_file", my_pref1="pref_file";
	public static final String USER_COUNT="COUNT", USERPASSWORD_KEY= "_PASSWORD";
	public static final String FIRSTNAME_KEY="_FIRST", LASTNAME_KEY = "_LASTNAME", EMAIL_KEY = "_EMAIL";
	public static final String PATH_KEY = "_PATH";
	private static final int CAMERA_PIC_REQUEST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		register=(TextView)findViewById(R.id.welcome_new);
		firstfield=(EditText)findViewById(R.id.first_name);
		lastfield=(EditText)findViewById(R.id.last_name);
		username=(EditText)findViewById(R.id.username);
		emailfield=(EditText)findViewById(R.id.email);
		pwd=(EditText)findViewById(R.id.pick_password);
		cpwd=(EditText)findViewById(R.id.re_password);
		ok=(Button)findViewById(R.id.registerme);
		cancel=(Button)findViewById(R.id.cancel);
		photo = (ImageView)findViewById(R.id.photo_thumb);
		clicker = (ImageButton)findViewById(R.id.camera);

		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		clicker.setOnClickListener(cameraListerer);
	}

		@Override
		public void onClick(View v) 
		{
			first = firstfield.getText().toString();
			last = lastfield.getText().toString();
			user = username.getText().toString();
			email = emailfield.getText().toString();
			pass = pwd.getText().toString();
			cpass = cpwd.getText().toString();
			//pathstring = myfileUri.getPath();
			
			if(v == ok)
			{
				if((first.length()==0))
				{
					Toast.makeText(this, "Please enter First name", Toast.LENGTH_SHORT).show();
					return;
				}
				if((last.length()==0))
				{
					Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
					return;
				}
				if((user.length()==0))
				{
					Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
					return;
				}
				if((email.length()==0))
				{
					Toast.makeText(this, "Please enter a valid email ID !!", Toast.LENGTH_SHORT).show();
					return;
				}
				if((pass.length()==0))
				{
					Toast.makeText(this, "Please enter a Password !!", Toast.LENGTH_SHORT).show();
					return;
				}
				if((cpass.length()==0))
				{
					Toast.makeText(this, "Please re-enter Password !!", Toast.LENGTH_SHORT).show();
					return;
				}
				if(pass.compareTo(cpass)!=0)
				{
					Toast.makeText(this, "Password Mismatch !!", Toast.LENGTH_SHORT).show();
					return;
				}

				else
				{
					SharedPreferences settings=getSharedPreferences(my_pref, 0);
					int counter = settings.getInt(USER_COUNT, 0);
					for ( int i = 1; i<=counter; i++)
					{
						String userKey = ""+i;
						String savedUser = settings.getString(userKey, "");
						if(savedUser.compareTo(user)==0)
						{
							Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
							return;
						}
					}
					int userID = counter + 1;

					SharedPreferences.Editor e=settings.edit();
					e.putInt(USER_COUNT, userID);

					String key;

					key = String.valueOf(userID); 
					e.putString(key, user);
					
					key = userID + USERPASSWORD_KEY;
					e.putString(key, pass);
					
					key = userID + PATH_KEY;
					e.putString(key, pathstring);
					
					e.putString(SpHelper.LOGGED_USER, user);
					e.putString(SpHelper.PATH_KEY, pathstring);

					e.commit();

					SharedPreferences settings1 = getSharedPreferences(my_pref1, 0);
					SharedPreferences.Editor e1 = settings1.edit();

					key = userID + FIRSTNAME_KEY;
					e1.putString(key, first);

					key = userID + LASTNAME_KEY;
					e1.putString(key, last);

					key = userID + EMAIL_KEY;
					e1.putString(key, email);

					e1.commit();

					counter = counter + 1;

					Toast.makeText(this, "Redirecting to Welcome page", Toast.LENGTH_SHORT).show();
					Intent myint = new Intent(this,Welcome.class);
					startActivity(myint);
					finish();
				}
			}

			else if (v == cancel)
			{
				Toast.makeText(this, "Redirecting to Main page", Toast.LENGTH_SHORT).show();
				Intent myint=new Intent(this,MainActivity.class);
				startActivity(myint);
				finish();
			}
		}
	
		OnClickListener cameraListerer = new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Intent myint = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				myfileUri = Uri.fromFile(getPhoto());
				myint.putExtra(MediaStore.EXTRA_OUTPUT, myfileUri);
				startActivityForResult(myint, CAMERA_PIC_REQUEST);
			}
		};
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK)
		{ 
			pathstring = myfileUri.getPath();
			File dest = new File(pathstring);
			FileInputStream fis;
			try 
			{
				fis = new FileInputStream(dest);
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
		else if (resultCode == RESULT_CANCELED) 
		{
			Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
		}
	}

	private File getPhoto() 
	{
		File directory = Environment.getExternalStorageDirectory(); 
		directory=new File(directory.getAbsolutePath()+"/newapp/");
		if(directory.exists() == false)
		{
			directory.mkdir();
		}
		Locale current = getResources().getConfiguration().locale;
		SimpleDateFormat myformat = new SimpleDateFormat("yyMMdd_HHmmss", current);
		String date = myformat.format(new Date());		
		try 
		{
			return File.createTempFile("IMG_" + date, ".jpg", directory);
		} 
		catch (IOException e)
		{
			@SuppressWarnings("unused")
			boolean exists = directory.exists();
			e.printStackTrace();
		}				
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}