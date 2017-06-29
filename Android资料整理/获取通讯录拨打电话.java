package com.example.lv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	private ListView lv;
	private BaseAdapter adapter;
	private List<Persons> contacts;
	private AlertDialog ad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		contacts = new ArrayList<Persons>();
		
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while(cursor.moveToNext()) { 
			Persons person = new Persons();
			String contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			person.setName(contactName);
			
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId, null, null);
			String phoneNum = "";
			while(phone.moveToNext()) { 
				phoneNum += phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + ",";
			}
			person.setPhoneNum(phoneNum.substring(0, phoneNum.length()-1));
			contacts.add(person);
			phone.close();
		}
		cursor.close();
		
		lv = (ListView) findViewById(R.id.listView);
		adapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = null;
				Persons person = contacts.get(position);
				if(convertView==null) {
					Button btn = new Button(MainActivity.this);
					btn.setText(person.getName());
					v = btn;
				}else {
					v = convertView;
					((Button) v).setText(person.getName());
				}
				v.setTag(position);
				v.setOnClickListener(MainActivity.this);
				return v;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			} 
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return contacts.size();
			}
		};
		
		lv.setAdapter(adapter);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showDialog(contacts.get((int) v.getTag()));
	}
	
	private void showDialog(Persons person) {
		LinearLayout ll = new LinearLayout(MainActivity.this);
		ll.setOrientation(LinearLayout.VERTICAL);
		String[] numbers = person.getPhoneNum().split(",");
		for(String number:numbers) {
			TextView tv = new TextView(MainActivity.this);
			tv.setText(number);
			tv.setPadding(0, 10, 0, 10);
			tv.setTextSize(20);
			tv.getPaint().setFakeBoldText(true);
			tv.setGravity(Gravity.CENTER);
			tv.setOnClickListener(phonenumOnclick);
			ll.addView(tv);
		}
		ad = new AlertDialog.Builder(MainActivity.this).setTitle(null).setView(ll).create();
		ad.setCanceledOnTouchOutside(true);
		ad.show();
	}

	private OnClickListener phonenumOnclick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v instanceof TextView) {
				//Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+telTv.getText().toString())); //到拨打电话界面，不拨打
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+((TextView) v).getText().toString()));//直接拨打
				startActivity(intent);
				if(ad.isShowing()) {
					ad.dismiss();
				}
			}
		}
	};
	
}
