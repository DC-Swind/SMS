package com.example.sms;

import java.util.ArrayList;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.*;

public class MainActivity extends Activity {
	Context mContext = null;  
	/**获取库Phon表字段**/  
    private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
     
    /**联系人显示名称**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
    /**电话号码**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**联系人名称**/  
    private ArrayList<String> mContactsName = new ArrayList<String>();  
    /**联系人头像**/  
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  
    
	EditText[] showListName = new EditText[1000];
	EditText[] showListNumber = new EditText[1000];
	RadioButton[] showListDelete = new RadioButton[1000];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		/*
		final TextView text = new TextView(this);
		text.setText("Loading...");
		
		
		EditText User = new EditText(this);
		User.setOnEditorActionListener(new OnEditorActionListener(){
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
				text.setText("Editing User");
				return false;
			}
		});
		*/
		setContentView(R.layout.activity_main);
		
		getPhoneContact();
		
		drawContactList();
		//EditText show = (EditText)findViewById(R.id.msg);
		//show.setText("1+1=2".toCharArray(),0,5);
		
	}

	protected void drawContactList() {
		EditText show = (EditText)findViewById(R.id.contactList);
		int totalContact = mContactsName.size();
		String s = new String("总共导入 "+totalContact+" 个联系人");
		show.setText(s.toCharArray(),0,s.length());
		

		
		for(int i=0; i<min(totalContact,1000); i++){
			showListName[i] = new EditText(mContext);
			showListNumber[i] = new EditText(mContext);
			showListDelete[i] = new RadioButton(mContext);
			String name = mContactsName.get(i);
			showListName[i].setText(name.toCharArray(),0,name.length());
			String number = mContactsNumber.get(i);
			showListNumber[i].setText(number.toCharArray(),0,number.length());

			TableRow tr = new TableRow(mContext);
			TableLayout table = (TableLayout)findViewById(R.id.showlist);
			table.addView(tr);
			tr.addView(showListName[i]);
			tr.addView(showListNumber[i]);
			tr.addView(showListDelete[i]);
		}
	}

	private int min(int a, int b) {
		if (a < b) return a; else return b;
	}

	protected void getPhoneContact(){
		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
		 
	    if (phoneCursor != null) {  
	        while (phoneCursor.moveToNext()) {  
	        	//得到手机号码  
	        	String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
	        	//当手机号码为空的或者为空字段 跳过当前循环  
	        	if (TextUtils.isEmpty(phoneNumber)) continue;  
	        	//得到联系人名称  
	        	String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
	        mContactsName.add(contactName);  
	        mContactsNumber.add(phoneNumber);  
	        //mContactsPhonto.add(contactPhoto);  
	        }  
	 
	        phoneCursor.close();  
	    }  
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}


