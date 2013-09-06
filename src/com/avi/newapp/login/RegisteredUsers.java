package com.avi.newapp.login;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RegisteredUsers extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registered_users);
		
	    ListView userlist = (ListView) findViewById(R.id.listview);
	    
	    SharedPreferences settings=getSharedPreferences(Register.my_pref, 0);
	    int counter = settings.getInt(Register.USER_COUNT, 0);
	    
	    ArrayList<String> newArray = new ArrayList<String>();
	    for( int i = 1 ; i <= counter; i++)
	    {
	    	String savedUser = settings.getString(""+i, "");
	    	newArray.add(savedUser);  
	    }
	    userlist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newArray));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registered_users, menu);
		return true;
	}
}
